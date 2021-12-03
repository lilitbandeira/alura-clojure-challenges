(ns challenge.core
  (:require [challenge.db :as c.db]
            [challenge.logic :as c.logic]))

(println "FILTER BY CATEGORY")

(println (c.logic/expanding-by-category (c.db/all-shops) "Clothing"))

(println "CURRENT MONTH RESUME")

(println (c.logic/expanding-by-month (c.db/all-shops)))

(println "FILTER BY PRICE")

(println (c.logic/expanding-by-price (c.db/all-shops) 743.6))

(println "FILTER BY ESTABLISHMENT")

(println (c.logic/expanding-by-establishment (c.db/all-shops) "RCHLO"))
