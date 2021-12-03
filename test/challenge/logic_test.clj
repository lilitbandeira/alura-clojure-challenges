(ns challenge.logic-test
  (:require [clojure.test :refer :all]
            [challenge.logic :refer :all]
            [challenge.db :as c.db]
            [schema.core :as s]
            [java-time :as time]))

(s/set-fn-validation! true)

(deftest create-new-shop-test
  (testing "Adicionando nova compra com sucesso"
    (is (= {:id            7,
            :date          (time/format "dd/MM/yyyy hh:mm:ss" (time/local-date-time)),
            :products      {:refrigerator {:amount 100, :unit-price 2350.9}
                            :prisma       {:amount 2, :unit-price 350}}
            :category      "Duck",
            :establishment "Mic"}
        (create-new-shop "Mic"
                         "Duck"
                         {:refrigerator {:amount     100
                                         :unit-price 2350.9}
                          :prisma       {:amount     2
                                         :unit-price 350}}))))

(testing "Recusa nova compra se há entidades vazias"))



