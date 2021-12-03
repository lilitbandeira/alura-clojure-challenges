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

(s/defn add-new-shop :- c.db/ShopList
  [new-shop :- c.db/Shop]
  (let [shops (atom (c.db/all-shops))]
    (swap! shops conj new-shop)))

(s/defn create-new-shop :- c.db/Shop
  [establishment :- s/Str category :- s/Str products :- c.db/Products]
  (let [new-shop {:id            (inc (count (c.db/all-shops)))
                   :date          (time/format "dd/MM/yyyy hh:mm:ss" (time/local-date-time))
                   :products      products
                   :category      category
                   :establishment establishment}
       new-shop-list (add-new-shop new-shop)]
      (pprint new-shop-list)
       (get new-shop-list (- (count new-shop-list) 1))))

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