(ns challenge.db
  (:require [java-time :as time]
            [schema.core :as s]
            [datomic.api :as d]))

;-----------------------------------------------------------------

(def db-uri "datomic:dev://localhost:4334/ecommerce")

(defn open-connection []
  (d/create-database db-uri)
  (d/connect db-uri))

(defn drop-database []
  (d/delete-database db-uri))

(def schema-db [; ---------- PURCHASE -----------------
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
                 :db/valueType   :db.type/string
                 :db/cardinality :db.cardinality/one}

                {:db/ident       :card/validity
                 :db/valueType   :db.type/string
                 :db/cardinality :db.cardinality/one}

                {:db/ident       :card/limit
                 :db/valueType   :db.type/bigdec
                 :db/cardinality :db.cardinality/one}

                {:db/ident       :card/costumer
                 :db/valueType   :db.type/ref
                 :db/cardinality :db.cardinality/one}

                ; ---------- COSTUMER -----------------

                {:db/ident       :costumer/id
                 :db/valueType   :db.type/uuid
                 :db/cardinality :db.cardinality/one
                 :db/unique      :db.unique/identity}

                {:db/ident       :costumer/name
                 :db/valueType   :db.type/tuple
                 :db/tupleAttrs  :db/string
                 :db/cardinality :db.cardinality/one}

                {:db/ident       :costumer/cpf
                 :db/valueType   :db.type/tuple
                 :db/tupleAttrs  :db.type/string
                 :db/cardinality :db.cardinality/one}])

(defn create-schema [connection]
  (d/transact connection schema-db))

(defn all [conn]                              ;query
  (d/q '[:find ?purchase-category
         :where [?purchase-category :purchase/category]] conn))

; lógicas referentes as queries do datomic
; dúvida: onde chamaremos as funções que sobe e derruba o banco? server.clj? core.clj?

; PRECISAMOS RODAR PRA VER SE TÁ TUDO CERTO ATÉ AQUI!!!!







;----- OLD ----------------------------------------------------------



;--------- COMPRAS -----------------------------------------------

(defn set-time
  ([]
   (time/format "dd/MM/yyyy hh:mm:ss" (time/local-date-time)))
  ([year month day hour minute second]
   (time/format "dd/MM/yyyy hh:mm:ss" (time/local-date-time year month day hour minute second))))

;(defn id-generate
;  []
;  (java.util.UUID/randomUUID))

(def PosInt (s/pred pos-int?))

(def shops-1 {:id            1
              :date          (set-time 2021 12 20 12 22 9),
              :products      {:sandwich {:amount     2
                                         :unit-price 35.9}
                              :soda     {:amount     2
                                         :unit-price 20}
                              },
              :category      "Food",
              :establishment "Subway"})

(def shops-2 {:id            2
              :date          (set-time 2021 12 21 14 50 54),
              :products      {:shirt {:amount     4
                                      :unit-price 35.9}
                              :heel  {:amount     3
                                      :unit-price 200}
                              },
              :category      "Clothing",
              :establishment "RCHLO"})

(def shops-3 {:id            3
              :date          (set-time 2021 12 18 10 22 49),
              :products      {:ticket {:amount     42
                                       :unit-price 5.9}
                              },
              :category      "Transport",
              :establishment "Bus"})

(def shops-4 {:id            4
              :date          (set-time 2021 12 3 4 11 54),
              :products      {:milk   {:amount     4
                                       :unit-price 5.3}
                              :bread  {:amount     2
                                       :unit-price 12}
                              :cheese {:amount     1
                                       :unit-price 31}
                              :tomato {:amount     5
                                       :unit-price 2}
                              },
              :category      "Market",
              :establishment "Supermarket"})

(def shops-5 {:id            5
              :date          (set-time 2021 12 10 17 8 29),
              :products      {:hamburguer  {:amount     4
                                            :unit-price 40}
                              :french-frie {:amount     1
                                            :unit-price 20}
                              },
              :category      "Food",
              :establishment "MC"})

(def shops-6 {:id            6
              :date          (set-time 2021 12 8 10 29 9),
              :products      {:boots    {:amount     2
                                         :unit-price 235.9}
                              :hand-bag {:amount     2
                                         :unit-price 350}
                              },
              :category      "Clothing",
              :establishment "Baw Clothing"})

(defn all-shops []
  [shops-1, shops-2, shops-3, shops-4, shops-5, shops-6])

(s/def Product {:amount     s/Num
                :unit-price s/Num})
(s/def Products {s/Keyword Product})
(s/def Shop {:id            PosInt
             :date          s/Str
             :products      Products
             :category      s/Str
             :establishment s/Str})
(s/def ShopList [Shop])

;(s/validate Shop {:id            7
;                  :date          (time/format "dd/MM/yyyy hh:mm:ss" (time/local-date-time))
;                  :products      {:boots    {:amount     2
;                                             :unit-price 235.9}
;                                  :hand-bag {:amount     2
;                                             :unit-price 350}}
;                  :category      "Food"
;                  :establishment "B"})


;--------- CARTÕES -----------------------------------------------

(def card-1 {:id       1
             :number   "1234 8769 6774 9874",
             :cvv      "456",
             :validity (time/format "MM/yyyy" (time/local-date 2025 6)),
             :limit    8000})

(def card-2 {:id       2
             :number   "6774 9874 1234 8769",
             :cvv      "326",
             :validity (time/format "MM/yyyy" (time/local-date 2029 12)),
             :limit    10000})

(def card-3 {:id       3
             :number   "8769 1234 9874 7654",
             :cvv      "136",
             :validity (time/format "MM/yyyy" (time/local-date 2022 8)),
             :limit    3000})

(defn all-cards []
  [card-1, card-2, card-3])

;--------- CLIENTES -----------------------------------------------

(def client-1 {:id    1
               :name  "Maria José Machado",
               :cpf   "056.783.876-98",
               :email "cliente1@gmail.com"})

(def client-2 {:id    2
               :name  "João da Silva",
               :cpf   "098.789.876-98",
               :email "cliente2@gmail.com"})

(def client-3 {:id    3
               :name  "Mara Aleluia",
               :cpf   "009.783.876-97",
               :email "cliente3@gmail.com"})

(defn all-clients []
  [client-1, client-2, client-3])
