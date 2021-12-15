;(ns challenge.logic
;  (:use [clojure pprint])
;  (:require [java-time :as time]
;            [clojure.string :as str]
;            [schema.core :as s]
;            [challenge.db :as c.db]))

;---|Pure fuctions|------------------------------------------------------------------------

;(defn total-by-product
;  [product]
;  (* (:amount product) (:unit-price product)))
;
;(defn total-by-filter
;  [product]
;  (->> product
;       vals
;       (map total-by-product)
;       (reduce +)))
;
;(defn calculate-total-by-filter
;  [shops]
;  (->> shops
;       (map :products)
;       (map total-by-filter)
;       (reduce +)))
;
;(defn expanding-by-filter
;  [[filter shops]]
;  {:total     (calculate-total-by-filter shops)
;   :filter-by filter}
;  )

;---|By category|------------------------------------------------------------------------

;(defn category-exists?
;  [shops category]
;  (->> shops
;       (map :category)
;       (some #(= category %))))
;
;(defn expanding-by-category
;  ([shops category]
;   (if (category-exists? shops category)
;     (->> shops
;          (group-by :category)
;          (map expanding-by-filter)
;          (filter #(= (:filter-by %) category)))
;     (throw (ex-info "Category does not exist" {:shops shops, :typo :Category-Does-Not-Exist}))))
;  ([shops]
;   (->> shops
;        (group-by :category)
;        (map expanding-by-filter))))

;---|By establishment|------------------------------------------------------------------------

;(defn expanding-by-establishment
;  ([shops establishment]
;   (->> shops
;        (group-by :establishment)
;        (map expanding-by-filter)
;        (filter #(= (:filter-by %) establishment))))
;  ([shops]
;   (->> shops
;        (group-by :establishment)
;        (map expanding-by-filter)))
;  )

;---|By month|------------------------------------------------------------------------

;(defn date-filter
;  [shops]
;  (str/includes? (get shops :date) (time/format "MM/yyyy" (time/local-date))))
;
;(defn expanding-by-month
;  [shops]
;  {:total (calculate-total-by-filter (filter date-filter shops))
;   :month (time/format "MM/yyyy" (time/local-date))})

;---|By price|------------------------------------------------------------------------

;(defn return-map
;  [shops]
;  {:date          (:date shops)
;   :establishment (:establishment shops)
;   :category      (:category shops)
;   })
;
;(defn total-expanding-by-filter
;  [[_ shops]]
;  {:total   (calculate-total-by-filter shops)
;   :details (map return-map shops)
;   })
;
;(defn expanding-by-price
;  [shops price]
;  (->> shops
;       (group-by :id)
;       (map total-expanding-by-filter)
;       (filter #(>= (:total %) price))))

;---|Adds new shops|------------------------------------------------------------------------

;(s/set-fn-validation! true)
;
;(s/defn add-new-shop :- c.db/ShopList
;  [shoplist :- c.db/ShopList
;   new-shop :- c.db/Shop]
;  (let [shops (atom shoplist)]
;    (swap! shops conj new-shop)))
;
;(s/defn create-new-shop :- c.db/Shop
;  [shoplist :- c.db/ShopList
;   establishment :- s/Str
;   category :- s/Str
;   products :- c.db/Products]
;  (if (and (not= products {})
;           (not= category "")
;           (not= establishment ""))
;    (let [new-shop {:id            (inc (count shoplist))
;                    :date          (c.db/set-time)
;                    :products      products
;                    :category      category
;                    :establishment establishment}
;          new-shop-list (add-new-shop shoplist new-shop)]
;      (get new-shop-list (- (count new-shop-list) 1)))
;    (throw (ex-info "Cannot receive empty parameters"
;                    {:products products, :category category, :establishment establishment, :typo :Impossible-To-Receive-Empty-Parameters}))))
