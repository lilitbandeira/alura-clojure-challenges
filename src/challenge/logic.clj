(ns challenge.logic
  (:use [clojure pprint])
  (:require [java-time :as time]
            [clojure.string :as str]
            [schema.core :as s]
            [challenge.db :as c.db]))

;---|Pure fuctions|------------------------------------------------------------------------

(defn total-by-product
  [product]
  (* (:amount product) (:unit-price product)))

(defn total-by-filter
  [product]
  (->> product
       vals
       (map total-by-product)
       (reduce +)))

(defn calculate-total-by-filter
  [shops]
  (->> shops
       (map :products)
       (map total-by-filter)
       (reduce +)))

(defn expanding-by-filter
  [[filter shops]]
  {:total     (calculate-total-by-filter shops)
   :filter-by filter}
  )

;---|Adds new shops|------------------------------------------------------------------------

;(defn update-shops
;  [shops new-shop]
;  (pprint "update-shops" shops)
;  (update shops conj new-shop))
;
;(defn create-new-shop!
;  [shops new-shop]
;  (pprint "create-new-shops!" shops)
;  (swap! shops update-shops new-shop))

(s/defn add-new-shop :- c.db/Shop
  [new-shop :- c.db/Shop]
  (let [shops (atom (c.db/all-shops))]
       [new-shop {:id 7}]
       (swap! shops conj new-shop)
       (pprint @shops)))

;---|By category|------------------------------------------------------------------------

(defn expanding-by-category
  ([shops category]
   (->> shops
        (group-by :category)
        (map expanding-by-filter)
        (filter #(= (:filter-by %) category))))
  ([shops]
   (->> shops
        (group-by :category)
        (map expanding-by-filter))))

;---|By establishment|------------------------------------------------------------------------

(defn expanding-by-establishment
  ([shops establishment]
   (->> shops
        (group-by :establishment)
        (map expanding-by-filter)
        (filter #(= (:filter-by %) establishment))))
  ([shops]
   (->> shops
        (group-by :establishment)
        (map expanding-by-filter)))
  )

;---|By month|------------------------------------------------------------------------

(defn date-filter
  [shops]
  (str/includes? (get shops :date) (time/format "MM/yyyy" (time/local-date))))

(defn expanding-by-month
  [shops]
  {:total (calculate-total-by-filter (filter date-filter shops))
   :month (time/format "MM/yyyy" (time/local-date))})

;---|By price|------------------------------------------------------------------------

(defn return-map
  [shops]
  {:date          (:date shops)
   :establishment (:establishment shops)
   :category      (:category shops)
   })

(defn total-expanding-by-filter
  [[_ shops]]
  {:total   (calculate-total-by-filter shops)
   :details (map return-map shops)
   })

(defn expanding-by-price
  [shops price]
  (->> shops
       (group-by :id)
       (map total-expanding-by-filter)
       (filter #(>= (:total %) price))))