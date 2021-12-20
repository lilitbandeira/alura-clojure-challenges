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
      {:costumer  costumer-name
       :purchases verification})))

(defn check-customer-without-purchases
  "Verifica se os clientes possuem compras realizadas"
  [costumers]
  (let [mapa (map check-purchases costumers)]))