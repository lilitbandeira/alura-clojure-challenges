(ns challenge.purchases
  (:require [schema.core :as s]
            [datomic.api :as d]
            [challenge.utils :as utils]))

(s/def Purchase {:purchase/id            s/Uuid
                 :purchase/date          s/Str
                 :purchase/value         utils/Positive-number
                 :purchase/category      s/Str
                 :purchase/establishment s/Str
                 :purchase/card          utils/Id})

(s/defn create-new-purchase :- Purchase
  "Cria compras vinculadas a um cartão identificado"
  ([value :- utils/Positive-number
    category :- s/Str
    establishment :- s/Str
    card :- utils/Id]
   (create-new-purchase (utils/uuid) value category establishment card))

  ([uuid :- s/Uuid
    value :- utils/Positive-number
    category :- s/Str
    establishment :- s/Str
    card :- utils/Id]
   {:purchase/id            uuid
    :purchase/date          (utils/set-time)
    :purchase/value         value
    :purchase/category      category
    :purchase/establishment establishment
    :purchase/card          card}))

(defn add-new-purchase!
  "Adiciona novas compras ao banco de dados"
  [connection purchases]
  (d/transact connection purchases))