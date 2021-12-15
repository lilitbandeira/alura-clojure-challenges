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

(let [costumer-1 (costumers/create-new-costumer "Natália Rosa" "987.654.321-00" "natalia@rica.com")
      costumer-2 (costumers/create-new-costumer "Lilit Bandeira" "013.654.321-89" "lilit@rica.com")
      costumer-3 (costumers/create-new-costumer "Inês Brasil" "654.732.321-13" "ines@rica.com")
      costumer-4 (costumers/create-new-costumer "Rupaul Charles" "974.701.751-19" "rupaul@pobre.com")]
  (costumers/add-new-costumer! connection [costumer-1 costumer-2 costumer-3 costumer-4]))

(pprint (db/get-all-costumers (d/db connection)))

(let [costumer1-id (ffirst (db/get-costumers-uuid (d/db connection) "987.654.321-00"))
      costumer2-id (ffirst (db/get-costumers-uuid (d/db connection) "013.654.321-89"))
      costumer3-id (ffirst (db/get-costumers-uuid (d/db connection) "654.732.321-13"))
      card-1 (cards/create-new-card "1111 2222 3333 4444" 546N "10/29" 8000M [:costumer/id costumer1-id])
      card-2 (cards/create-new-card "3333 5555 3333 7777" 855N "10/27" 5500M [:costumer/id costumer2-id])
      card-3 (cards/create-new-card "9999 6666 3333 8888" 612N "10/27" 20000M [:costumer/id costumer3-id])]
  (cards/add-new-cards! connection [card-1 card-2 card-3]))

(pprint (db/get-all-cards (d/db connection)))

(let [card1-id (ffirst (db/get-cards-uuid (d/db connection) "1111 2222 3333 4444"))
      card2-id (ffirst (db/get-cards-uuid (d/db connection) "3333 5555 3333 7777"))
      card3-id (ffirst (db/get-cards-uuid (d/db connection) "9999 6666 3333 8888"))
      purchase-1 (purchases/create-new-purchase 2000M "Fashion" "BAW clothing" [:card/id card1-id])
      purchase-2 (purchases/create-new-purchase 50M "Food" "Subway" [:card/id card1-id])
      purchase-3 (purchases/create-new-purchase 450M "Fashion" "C&A" [:card/id card1-id])
      purchase-4 (purchases/create-new-purchase 1200M "Market" "Supermarket" [:card/id card1-id])
      purchase-5 (purchases/create-new-purchase 100M "Food" "MC" [:card/id card2-id])
      purchase-6 (purchases/create-new-purchase 8000M "Eletronics" "ebay" [:card/id card2-id])
      purchase-7 (purchases/create-new-purchase 600M "Fashion" "Melissa" [:card/id card3-id])
      purchase-8 (purchases/create-new-purchase 2300M "Market" "Wallmart" [:card/id card3-id])]
  (purchases/add-new-purchase! connection [purchase-1 purchase-2 purchase-3 purchase-4 purchase-5 purchase-6 purchase-7 purchase-8]))

(pprint (db/get-all-purchases (d/db connection)))

(pprint (db/get-purchases-by-card (d/db connection) 17592186045419))

;(println (db/drop-database!))