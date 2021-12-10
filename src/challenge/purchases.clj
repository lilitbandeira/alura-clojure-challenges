(ns challenge.purchases
  (:require [schema.core :as s]
            [challenge.schema-utils :as s.utils]))

(s/def Purchase {:purchase/date          s/Str
                 :card/cvv               s.utils/Positive-number
                 :purchase/category      s/Str
                 :purchase/establishment s/Str})

;(s/def Purchases [Purchase])

(s/defn create-new-purchase :- Purchase
  "Adicionar comprar para um cartão identificado"
  [value :- s.utils/Positive-number
   category :- s/Str
   establishment :- s/Str]
  {:purchase/date          (s.utils/set-time)
   :purchase/value         value
   :purchase/category      category
   :purchase/establishment establishment})

; lógicas referentes as compras (desafios anteriores) serão neste arquivo

; posibilidade para função de criar nova compra
;(pprint @(d/transact conn [[:db/add id-do-card :card/purchase {:value 0.1M :category "Food" :establishment "Subway"}]]))