(ns challenge.core
  (:require [challenge.db :as c.db]
            [challenge.logic :as c.logic]))

;(println "FILTER BY CATEGORY")
;
;(println (c.logic/expanding-by-category (c.db/all-shops) "Clothing"))
;
;(println "CURRENT MONTH RESUME")
;
;(println (c.logic/expanding-by-month (c.db/all-shops)))
;
;(println "FILTER BY PRICE")
;
;(println (c.logic/expanding-by-price (c.db/all-shops) 743.6))
;
;(println "FILTER BY ESTABLISHMENT")
;
;(println (c.logic/expanding-by-establishment (c.db/all-shops) "RCHLO"))

(println (c.logic/create-new-shop
           (c.db/all-shops)
           "Fenty Beauty"
           "Cosmetics"
           {:boots    {:amount     2
                       :unit-price 235.9}
            :hand-bag {:amount     2
                       :unit-price 350}
            }))