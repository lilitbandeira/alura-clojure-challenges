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

(pprint (db/get-all-cards (d/db connection)))

(let [card1-id (ffirst (db/get-cards-uuid (d/db connection) "1111 2222 3333 4444"))
      card2-id (ffirst (db/get-cards-uuid (d/db connection) "3333 5555 3333 7777"))
      card3-id (ffirst (db/get-cards-uuid (d/db connection) "9999 6666 3333 8888"))
      card4-id (ffirst (db/get-cards-uuid (d/db connection) "6666 3333 9999 0000"))
      costumer-1 (costumers/create-new-costumer "Natália Rosa" "987.654.321-00" "natalia@rica.com" [:card/id card1-id])
      costumer-2 (costumers/create-new-costumer "Lilit Bandeira" "013.654.321-89" "lilit@rica.com" [:card/id card2-id])
      costumer-3 (costumers/create-new-costumer "Inês Brasil" "654.732.321-13" "ines@rica.com" [:card/id card3-id])
      costumer-4 (costumers/create-new-costumer "Rupaul Charles" "974.701.751-19" "rupaul@pobre.com" [:card/id card4-id])]
  (costumers/add-new-costumer! connection [costumer-1 costumer-2 costumer-3 costumer-4]))

(pprint (db/get-all-costumers (d/db connection)))

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
  (purchases/add-new-purchase! connection [purchase-1 purchase-2 purchase-3 purchase-4 purchase-5 purchase-6 purchase-7 purchase-8]))

(pprint (db/get-all-purchases (d/db connection)))

(pprint (db/get-purchases-by-card (d/db connection) 17592186045418)) ; RESGATAR COMPRAR POR CARTÃO

(let [costumer1 (db/get-purchase-data-by-costumer-uuid (d/db connection) #uuid "8ceaf348-269e-4377-b4d0-4b95b336494c")
      costumer2 (db/get-purchase-data-by-costumer-uuid (d/db connection) #uuid "5f16b964-a8c1-4a46-a09a-e1694978246c")
      costumer3 (db/get-purchase-data-by-costumer-uuid (d/db connection) #uuid "b629ebe1-75f0-49ff-a6c5-6a4d5dc437fd")
      costumer4 (db/get-purchase-data-by-costumer-uuid (d/db connection) #uuid "7bafebff-6201-4b29-81a0-106f21871b78")]
  (println costumer1 costumer2 costumer3 costumer4))

; FALTA
; 1. definir função que compara quem fez a compra mais cara;
; 2. definir função que compara quem fez mais compras;
; 3. criar nova query para retornar cliente que não tem compras;
; 4. refazer funções de logic e testes;

;(println (db/drop-database!))