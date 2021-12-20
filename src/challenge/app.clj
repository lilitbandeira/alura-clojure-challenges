(ns challenge.app
  (:require
    [datomic.api :as d]
    [challenge.db :as db]
    [challenge.cards :as cards]
    [challenge.costumers :as costumers]
    [challenge.purchases :as purchases])
  (:use clojure.pprint))

(def connection (db/open-connection!))

(db/create-schema! connection)

(let [card-1 (cards/create-new-card "1111 2222 3333 4444" 546N "07/29" 8000M)
      card-2 (cards/create-new-card "3333 5555 3333 7777" 855N "01/27" 5500M)
      card-3 (cards/create-new-card "9999 6666 3333 8888" 612N "02/27" 20000M)
      card-4 (cards/create-new-card "6666 3333 9999 0000" 162N "05/29" 3000M)]
  (cards/add-new-cards! connection [card-1 card-2 card-3 card-4]))

(let [card1-id (ffirst (db/get-cards-uuid (d/db connection) "1111 2222 3333 4444"))
      card2-id (ffirst (db/get-cards-uuid (d/db connection) "3333 5555 3333 7777"))
      card3-id (ffirst (db/get-cards-uuid (d/db connection) "9999 6666 3333 8888"))
      card4-id (ffirst (db/get-cards-uuid (d/db connection) "6666 3333 9999 0000"))
      costumer-1 (costumers/create-new-costumer "Natália Rosa" "987.654.321-00" "natalia@rica.com" [:card/id card1-id])
      costumer-2 (costumers/create-new-costumer "Lilit Bandeira" "013.654.321-89" "lilit@rica.com" [:card/id card2-id])
      costumer-3 (costumers/create-new-costumer "Inês Brasil" "654.732.321-13" "ines@rica.com" [:card/id card3-id])
      costumer-4 (costumers/create-new-costumer "Rupaul Charles" "974.701.751-19" "rupaul@pobre.com" [:card/id card4-id])]
  (costumers/add-new-costumer! connection [costumer-1 costumer-2 costumer-3 costumer-4]))

(let [card1-id (ffirst (db/get-cards-uuid (d/db connection) "1111 2222 3333 4444"))
      card2-id (ffirst (db/get-cards-uuid (d/db connection) "3333 5555 3333 7777"))
      card3-id (ffirst (db/get-cards-uuid (d/db connection) "9999 6666 3333 8888"))
      purchase-1 (purchases/create-new-purchase 2000M "Fashion" "BAW clothing" [:card/id card1-id])
      purchase-2 (purchases/create-new-purchase 50M "Food" "Subway" [:card/id card1-id])
      purchase-3 (purchases/create-new-purchase 450M "Fashion" "C&A" [:card/id card1-id])
      purchase-4 (purchases/create-new-purchase 1200M "Market" "Supermarket" [:card/id card1-id])
      purchase-5 (purchases/create-new-purchase 100M "Food" "MC" [:card/id card2-id])
      purchase-6 (purchases/create-new-purchase 8000M "Eletronics" "ebay" [:card/id card3-id])
      purchase-7 (purchases/create-new-purchase 600M "Fashion" "Melissa" [:card/id card3-id])
      purchase-8 (purchases/create-new-purchase 2300M "Market" "Wallmart" [:card/id card3-id])]
  (purchases/add-new-purchase! connection [purchase-1 purchase-2 purchase-3 purchase-4 purchase-5 purchase-6 purchase-7 purchase-8])
  (cards/add-purchases-to-card! connection [:card/id card1-id] [:purchase/id (:purchase/id purchase-1)])
  (cards/add-purchases-to-card! connection [:card/id card1-id] [:purchase/id (:purchase/id purchase-2)])
  (cards/add-purchases-to-card! connection [:card/id card1-id] [:purchase/id (:purchase/id purchase-3)])
  (cards/add-purchases-to-card! connection [:card/id card1-id] [:purchase/id (:purchase/id purchase-4)])
  (cards/add-purchases-to-card! connection [:card/id card2-id] [:purchase/id (:purchase/id purchase-5)])
  (cards/add-purchases-to-card! connection [:card/id card3-id] [:purchase/id (:purchase/id purchase-6)])
  (cards/add-purchases-to-card! connection [:card/id card3-id] [:purchase/id (:purchase/id purchase-7)])
  (cards/add-purchases-to-card! connection [:card/id card3-id] [:purchase/id (:purchase/id purchase-8)]))

(pprint (db/get-all-costumers (d/db connection)))
(pprint (db/get-all-cards (d/db connection)))
(pprint (db/get-all-purchases (d/db connection)))

(pprint (db/get-purchases-by-card (d/db connection) 17592186045418)) ; RESGATAR COMPRAS POR CARTÃO

(let [costumer1 (ffirst (db/get-costumers-uuid (d/db connection) "987.654.321-00"))
      costumer1-data (db/get-purchase-data-by-costumer-uuid (d/db connection) costumer1)
      costumer1-card (db/get-card-by-costumer-uuid (d/db connection) costumer1)
      costumer2 (ffirst (db/get-costumers-uuid (d/db connection) "013.654.321-89"))
      costumer2-data (db/get-purchase-data-by-costumer-uuid (d/db connection) costumer2)
      costumer2-card (db/get-card-by-costumer-uuid (d/db connection) costumer2)
      costumer3 (ffirst (db/get-costumers-uuid (d/db connection) "654.732.321-13"))
      costumer3-data (db/get-purchase-data-by-costumer-uuid (d/db connection) costumer3)
      costumer3-card (db/get-card-by-costumer-uuid (d/db connection) costumer3)
      costumer4 (ffirst (db/get-costumers-uuid (d/db connection) "974.701.751-19"))
      costumer4-data (db/get-purchase-data-by-costumer-uuid (d/db connection) costumer4)
      costumer4-card (db/get-card-by-costumer-uuid (d/db connection) costumer4)]

  (pprint (costumers/check-higher-value-purchase [costumer1-data costumer2-data costumer3-data costumer4-data]))
  (pprint (costumers/check-total-purchase [costumer1-data costumer2-data costumer3-data costumer4-data]))
  (pprint (costumers/check-customer-without-purchases [costumer1-card costumer2-card costumer3-card costumer4-card])))

;(println (db/drop-database!))