(ns challenge.costumers
  (:require [schema.core :as s]
            [datomic.api :as d]
            [challenge.utils :as utils]))

(def Costumer-CPF #"\d{3}\.\d{3}\.\d{3}-\d{2}")
(def Costumer-email #".+\@.+\..+")

(s/def Costumer {:costumer/id    s/Uuid
                 :costumer/name  s/Str
                 :costumer/cpf   Costumer-CPF
                 :costumer/email Costumer-email})

(s/defn create-new-costumer :- Costumer
  "Cria um novo cliente"
  ([name :- s/Str
    cpf :- Costumer-CPF
    email :- Costumer-email]
   (create-new-costumer (utils/uuid) name cpf email))

  ([uuid :- s/Uuid
    name :- s/Str
    cpf :- Costumer-CPF
    email :- Costumer-email]
   {:costumer/id    uuid
    :costumer/name  name
    :costumer/cpf   cpf
    :costumer/email email}))

(defn add-new-costumer!
  "Adiciona novos clientes ao banco de dados"
  [connection costumers]
  (d/transact connection costumers))