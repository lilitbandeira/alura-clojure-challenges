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
           (create-new-shop (c.db/all-shops)
                            "Mic"
                            "Duck"
                            {:refrigerator {:amount     100
                                            :unit-price 2350.9}
                             :prisma       {:amount     2
                                            :unit-price 350}}))))

  (testing "Recusa nova compra se há entidades vazias"
    (is (thrown? clojure.lang.ExceptionInfo (create-new-shop (c.db/all-shops)
                                                             "Mic"
                                                             ""
                                                             {:refrigerator {:amount     100
                                                                             :unit-price 2350.9}
                                                              :prisma       {:amount     2
                                                                             :unit-price 350}})))
    (is (thrown? clojure.lang.ExceptionInfo (create-new-shop (c.db/all-shops)
                                                             ""
                                                             "Duck"
                                                             {:refrigerator {:amount     100
                                                                             :unit-price 2350.9}
                                                              :prisma       {:amount     2
                                                                             :unit-price 350}})))
    (is (thrown? clojure.lang.ExceptionInfo (create-new-shop (c.db/all-shops)
                                                             "Mic"
                                                             "Duck"
                                                             {})))
    (is (thrown? clojure.lang.ExceptionInfo (create-new-shop (c.db/all-shops)
                                                             "Mic"
                                                             "Duck"
                                                             {:refrigerator {}
                                                              :prisma       {:amount     2
                                                                             :unit-price 350}})))
    (is (thrown? clojure.lang.ExceptionInfo (create-new-shop (c.db/all-shops)
                                                             "Mic"
                                                             "Duck"
                                                             {:refrigerator {}
                                                              :prisma       {}}))))

  (testing "Recusa nova compra se há entidades nulas"
    (is (thrown? clojure.lang.ExceptionInfo (create-new-shop (c.db/all-shops)
                                                             "Mic"
                                                             nil
                                                             {:refrigerator {:amount     100
                                                                             :unit-price 2350.9}
                                                              :prisma       {:amount     2
                                                                             :unit-price 350}})))
    (is (thrown? clojure.lang.ExceptionInfo (create-new-shop (c.db/all-shops)
                                                             nil
                                                             "Duck"
                                                             {:refrigerator {:amount     100
                                                                             :unit-price 2350.9}
                                                              :prisma       {:amount     2
                                                                             :unit-price 350}})))
    (is (thrown? clojure.lang.ExceptionInfo (create-new-shop (c.db/all-shops)
                                                             "Mic"
                                                             "Duck"
                                                             nil)))
    (is (thrown? clojure.lang.ExceptionInfo (create-new-shop (c.db/all-shops)
                                                             "Mic"
                                                             "Duck"
                                                             {:refrigerator nil
                                                              :prisma       {:amount     2
                                                                             :unit-price 350}})))))

(deftest add-new-shop-test
  (testing "Verificando se a nova compra foi adicionada com sucesso à shop list"
    (is (= [{:id            1,
             :date          "20/12/2021 12:22:09",
             :products
                            {:sandwich {:amount 2, :unit-price 35.9},
                             :soda     {:amount 2, :unit-price 20}},
             :category      "Food",
             :establishment "Subway"}
            {:id            2,
             :date          "21/12/2021 02:50:54",
             :products      {:shirt {:amount 4, :unit-price 35.9},
                             :heel  {:amount 3, :unit-price 200}},
             :category      "Clothing",
             :establishment "RCHLO"}
            {:id            3,
             :date          "18/12/2021 10:22:49",
             :products      {:ticket {:amount 42, :unit-price 5.9}},
             :category      "Transport",
             :establishment "Bus"}
            {:id            4,
             :date          "03/12/2021 04:11:54",
             :products      {:milk   {:amount 4, :unit-price 5.3},
                             :bread  {:amount 2, :unit-price 12},
                             :cheese {:amount 1, :unit-price 31},
                             :tomato {:amount 5, :unit-price 2}},
             :category      "Market",
             :establishment "Supermarket"}
            {:id            5,
             :date          "10/12/2021 05:08:29",
             :products      {:hamburguer  {:amount 4, :unit-price 40},
                             :french-frie {:amount 1, :unit-price 20}},
             :category      "Food",
             :establishment "MC"}
            {:id            6,
             :date          "08/12/2021 10:29:09",
             :products      {:boots    {:amount 2, :unit-price 235.9},
                             :hand-bag {:amount 2, :unit-price 350}},
             :category      "Clothing",
             :establishment "Baw Clothing"}
            {:id            7,
             :date          (time/format "dd/MM/yyyy hh:mm:ss" (time/local-date-time)),
             :products      {:refrigerator {:amount 100, :unit-price 2350.9}
                             :prisma       {:amount 2, :unit-price 350}},
             :category      "Duck",
             :establishment "Mic"}]
           (add-new-shop (c.db/all-shops)
                         {:id            7,
                          :date          (time/format "dd/MM/yyyy hh:mm:ss" (time/local-date-time)),
                          :products      {:refrigerator {:amount 100, :unit-price 2350.9}
                                          :prisma       {:amount 2, :unit-price 350}}
                          :category      "Duck",
                          :establishment "Mic"})))

    (is (= [{:id            7,
             :date          (time/format "dd/MM/yyyy hh:mm:ss" (time/local-date-time)),
             :products      {:refrigerator {:amount 100, :unit-price 2350.9}
                             :prisma       {:amount 2, :unit-price 350}},
             :category      "Duck",
             :establishment "Mic"}]
           (add-new-shop []
                         {:id            7,
                          :date          (time/format "dd/MM/yyyy hh:mm:ss" (time/local-date-time)),
                          :products      {:refrigerator {:amount 100, :unit-price 2350.9}
                                          :prisma       {:amount 2, :unit-price 350}}
                          :category      "Duck",
                          :establishment "Mic"})))))

(deftest expanding-by-category-test
  (testing "Testando função de agrupar gastos por categoria, passando o nome da categoria"
  (is (= '({:total 1915.4, :filter-by "Clothing"})
         (expanding-by-category (c.db/all-shops) "Clothing"))))

  (testing "Testando função de agrupar gastos por categoria, não passando categoria"
    (is (= '({:total 291.8, :filter-by "Food"} {:total 1915.4, :filter-by "Clothing"} {:total 247.8, :filter-by "Transport"} {:total 86.2, :filter-by "Market"})
           (expanding-by-category (c.db/all-shops)))))

  (testing "Testando função de agrupar gastos por categoria, passando uma categoria que não existe"
    (is (thrown? clojure.lang.ExceptionInfo
           (expanding-by-category (c.db/all-shops) "Travel")))))





