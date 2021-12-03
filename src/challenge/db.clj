(ns challenge.db
  (:require [java-time :as time]
            [schema.core :as s]))

;--------- COMPRAS -----------------------------------------------

(defn set-time [year month day hour minute second]
  (time/format "dd/MM/yyyy hh:mm:ss" (time/local-date-time year month day hour minute second)))

(def shops-1 {:id            1
              :date          (set-time 2021 11 20 12 22 9),
              :products      {:sandwich {:amount     2
                                         :unit-price 35.9}
                              :soda     {:amount     2
                                         :unit-price 20}
                              },
              :category      "Food",
              :establishment "Subway"})

(def shops-2 {:id            2
              :date          (set-time 2021 11 21 14 50 54),
              :products      {:shirt {:amount     4
                                      :unit-price 35.9}
                              :heel  {:amount     3
                                      :unit-price 200}
                              },
              :category      "Clothing",
              :establishment "RCHLO"})

(def shops-3 {:id            3
              :date          (set-time 2021 11 18 10 22 49),
              :products      {:ticket {:amount     42
                                       :unit-price 5.9}
                              },
              :category      "Transport",
              :establishment "Bus"})

(def shops-4 {:id            4
              :date          (set-time 2021 10 3 4 11 54),
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
              :date          (set-time 2021 11 10 17 8 29),
              :products      {:hamburguer  {:amount     4
                                            :unit-price 40}
                              :french-frie {:amount     1
                                            :unit-price 20}
                              },
              :category      "Food",
              :establishment "MC"})

(def shops-6 {:id            6
              :date          (set-time 2021 10 8 10 29 9),
              :products      {:boots    {:amount     2
                                         :unit-price 235.9}
                              :hand-bag {:amount     2
                                         :unit-price 350}
                              },
              :category      "Clothing",
              :establishment "Baw Clothing"})

(defn all-shops []
  [shops-1, shops-2, shops-3, shops-4, shops-5, shops-6])

(s/def Product {s/Keyword s/Num})
(s/def Products { s/Keyword Product})
(s/def Shop {:id s/Num
             :date s/Str
             :products Products
             :category s/Str
             :establishment s/Str})
(s/def ShopList [Shop])

;(s/validate Shop {:id            23
;                  :date          (time/format "dd/MM/yyyy hh:mm:ss" (time/local-date-time))
;                  :products      {:boots    {:amount     2
;                                             :unit-price 235.9}
;                                  :hand-bag {:amount     2
;                                             :unit-price 350}
;                                  }
;                  :category      "C"
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
