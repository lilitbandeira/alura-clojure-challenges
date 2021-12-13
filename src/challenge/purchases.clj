(ns challenge.purchases
  (:require [schema.core :as s]
            [challenge.utils :as utils]))

(s/def Purchase {:purchase/id            s/Uuid
                 :purchase/date          s/Str
                 :purchase/value         utils/Positive-number
                 :purchase/category      s/Str
                 :purchase/establishment s/Str
                 :purchase/card          s/Uuid})

;(s/def Purchases [Purchase])

(s/defn create-new-purchase :- Purchase
  "Adicionar comprar para um cartão identificado"
  ([value :- utils/Positive-number
    category :- s/Str
    establishment :- s/Str
    card :- s/Uuid]
   (create-new-purchase (utils/uuid) value category establishment card))

  ([uuid :- s/Uuid
    value :- utils/Positive-number
    category :- s/Str
    establishment :- s/Str
    card :- s/Uuid]
   {:purchase/id            uuid
    :purchase/date          (utils/set-time)
    :purchase/value         value
    :purchase/category      category
    :purchase/establishment establishment
    :purchase/card          card}))

; lógicas referentes as compras (desafios anteriores) serão neste arquivo

; posibilidade para função de criar nova compra
;(pprint @(d/transact conn [[:db/add id-do-card :card/purchase {:value 0.1M :category "Food" :establishment "Subway"}]]))