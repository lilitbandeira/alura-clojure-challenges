(ns challenge.db
  (:require [datomic.api :as d]))

;-----------------------------------------------------------------

(def db-uri "datomic:dev://localhost:4334/challenge")

(defn open-connection! []
  (d/create-database db-uri)
  (d/connect db-uri))

(defn drop-database! []
  (d/delete-database db-uri))

(def schema-db [; ---------- COSTUMER -----------------

                {:db/ident       :costumer/id
                 :db/valueType   :db.type/uuid
                 :db/cardinality :db.cardinality/one
                 :db/unique      :db.unique/identity}

                {:db/ident       :costumer/name
                 :db/valueType   :db.type/string
                 :db/cardinality :db.cardinality/one}

                {:db/ident       :costumer/cpf
                 :db/valueType   :db.type/string
                 :db/cardinality :db.cardinality/one}

                {:db/ident       :costumer/email
                 :db/valueType   :db.type/string
                 :db/cardinality :db.cardinality/one}

                {:db/ident       :costumer/card
                 :db/valueType   :db.type/ref
                 :db/cardinality :db.cardinality/one}

                ; ---------- CARD -----------------

                {:db/ident       :card/id
                 :db/valueType   :db.type/uuid
                 :db/cardinality :db.cardinality/one
                 :db/unique      :db.unique/identity}

                {:db/ident       :card/number
                 :db/valueType   :db.type/string
                 :db/cardinality :db.cardinality/one}

                {:db/ident       :card/cvv
                 :db/valueType   :db.type/bigint
                 :db/cardinality :db.cardinality/one}

                {:db/ident       :card/validity
                 :db/valueType   :db.type/string
                 :db/cardinality :db.cardinality/one}

                {:db/ident       :card/limit
                 :db/valueType   :db.type/bigdec
                 :db/cardinality :db.cardinality/one}

                {:db/ident       :card/purchases
                 :db/valueType   :db.type/ref
                 :db/cardinality :db.cardinality/many}

                ; ---------- PURCHASE -----------------

                {:db/ident       :purchase/id
                 :db/valueType   :db.type/uuid
                 :db/cardinality :db.cardinality/one
                 :db/unique      :db.unique/identity}

                {:db/ident       :purchase/date
                 :db/valueType   :db.type/string
                 :db/cardinality :db.cardinality/one}

                {:db/ident       :purchase/value
                 :db/valueType   :db.type/bigdec
                 :db/cardinality :db.cardinality/one}

                {:db/ident       :purchase/category
                 :db/valueType   :db.type/string
                 :db/cardinality :db.cardinality/one}

                {:db/ident       :purchase/establishment
                 :db/valueType   :db.type/string
                 :db/cardinality :db.cardinality/one}

                {:db/ident       :purchase/card
                 :db/valueType   :db.type/ref
                 :db/cardinality :db.cardinality/one}])

(defn create-schema! [connection]
  (d/transact connection schema-db))

; -------------- QUERIES ---------------

(defn get-all-costumers
  "Busca todos os clientes"
  [snapshot]
  (d/q '[:find (pull ?costumer [*])
         :where [?costumer :costumer/id]]
       snapshot))

(defn get-all-cards
  "Busca todos os cartões"
  [snapshot]
  (d/q '[:find (pull ?card [*])
         :where [?card :card/id]]
       snapshot))

(defn get-all-purchases
  "Busca todas as compras"
  [snapshot]
  (d/q '[:find (pull ?purchase [*])
         :where [?purchase :purchase/id]]
       snapshot))

(defn get-cards-uuid
  "Busca o uuid do cartão a partir do número"
  [snapshot number]
  (d/q '[:find ?card-id
         :in $ ?card-number
         :where [?card :card/number ?card-number]
         [?card :card/id ?card-id]]
       snapshot number))

(defn get-costumers-uuid
  "Busca o uuid do cliente a partir do cpf"
  [snapshot cpf]
  (d/q '[:find ?costumer-id
         :in $ ?costumer-cpf
         :where [?costumer :costumer/cpf ?costumer-cpf]
         [?costumer :costumer/id ?costumer-id]]
       snapshot cpf))

(defn get-purchases-by-card
  "Busca compras de um do cartão a partir do id"
  [snapshot card-id]
  (d/q '[:find (pull ?purchase [*])
         :in $ ?card
         :where [?purchase :purchase/card ?card]]
       snapshot card-id))

(defn get-purchase-data-by-costumer-uuid
  "Busca informações de compras a partir do uuid do cliente"
  [snapshot costumer-uuid]
  (d/q '[:find ?costumer-name (max ?value) (count ?value)
         :keys costumer higher-value-purchase total-purchases
         :in $ ?costumer-id
         :where [?costumer :costumer/id ?costumer-id]
         [?costumer :costumer/name ?costumer-name]
         [?costumer :costumer/card ?costumer-card]
         [?costumer-card :card/purchases ?purchase]
         [?purchase :purchase/value ?value]]
       snapshot costumer-uuid))

(defn get-card-by-costumer-uuid
  "Busca informações do cartão a partir do uuid do cliente"
  [snapshot costumer-uuid]
  (d/q '[:find ?costumer-name (pull ?costumer-card [*])
         :in $ ?costumer-id
         :where [?costumer :costumer/id ?costumer-id]
         [?costumer :costumer/name ?costumer-name]
         [?costumer :costumer/card ?costumer-card]]
       snapshot costumer-uuid))

;[?card :card/purchases ?purchase]

