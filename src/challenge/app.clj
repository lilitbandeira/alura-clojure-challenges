(ns challenge.app
  (:require
    [datomic.api :as d]
    [challenge.db :as db]
    [challenge.cards :as cards])
  (:use clojure.pprint))

(def connection (db/open-connection))

(db/create-schema connection)

(def db (d/db connection))

;(let [celular-barato (model/novo-produto "Celular Barato", "/celular-barato", 8888.10M)
;      resultado @(d/transact conn [celular-barato])
;      id-entidade (-> resultado :tempids vals first)]

(let [card-1 (cards/create-new-card "1111 2222 3333 4444" 546 "10/11" 8000)
      result @(d/transact connection [card-1])]
  (pprint result))

;(pprint (db/all db))
;(println (c.db/drop-database))

