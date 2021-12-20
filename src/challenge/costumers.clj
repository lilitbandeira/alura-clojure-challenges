(ns challenge.costumers
  (:require [schema.core :as s]
            [datomic.api :as d]
            [challenge.utils :as utils]))

(def Costumer-CPF #"\d{3}\.\d{3}\.\d{3}-\d{2}")
(def Costumer-email #".+\@.+\..+")

(s/def Costumer {:costumer/id    s/Uuid
                 :costumer/name  s/Str
                 :costumer/cpf   Costumer-CPF
                 :costumer/email Costumer-email
                 :costumer/card  utils/ID})

(s/defn create-new-costumer :- Costumer
  "Cria um novo cliente"
  ([name :- s/Str
    cpf :- Costumer-CPF
    email :- Costumer-email
    card :- utils/ID]
   (create-new-costumer (utils/uuid) name cpf email card))

  ([uuid :- s/Uuid
    name :- s/Str
    cpf :- Costumer-CPF
    email :- Costumer-email
    card :- utils/ID]
   {:costumer/id    uuid
    :costumer/name  name
    :costumer/cpf   cpf
    :costumer/email email
    :costumer/card  card}))

(defn add-new-costumer!
  "Adiciona novos clientes ao banco de dados"
  [connection costumers]
  (d/transact connection costumers))

(defn check-purchases
  "Verifica se cliente possui compras realizadas"
  [costumer]
  (let [costumer-name (ffirst costumer)
        verification (:card/purchases (second (first costumer)))]
    (when (not verification)
      {:costumer  costumer-name})))

(defn check-customer-without-purchases
  "Verifica se os clientes possuem compras realizadas"
  [costumers]
  (remove nil? (map check-purchases costumers)))

(defn check-higher-value-purchase-by-costumer
  "Organiza os dados a partir dos dados de compras de maior valor"
  [costumer]
  (let [costumer-name (:costumer (first costumer))
        higher-value-purchase (:higher-value-purchase (first costumer))]
    (when higher-value-purchase
      {:costumer costumer-name
       :higher-value-purchase higher-value-purchase})))

(defn check-higher-value-purchase
  "Verifica cliente com compra de maior valor"
  [costumers]
  (let [costumer-data (remove nil? (map check-higher-value-purchase-by-costumer costumers))]
    (apply max-key :higher-value-purchase costumer-data)))

(defn check-total-purchase-by-costumer
  "Organiza os dados a partir dos dados de maior número de compras"
  [costumer]
  (let [costumer-name (:costumer (first costumer))
        total-purchases (:total-purchases (first costumer))]
    (when total-purchases
     {:costumer        costumer-name
      :total-purchases total-purchases})))

(defn check-total-purchase
  "Verifica cliente que realizou mais compras"
  [costumers]
  (let [costumer-data (remove nil? (map check-total-purchase-by-costumer costumers))]
    (apply max-key :total-purchases costumer-data)))