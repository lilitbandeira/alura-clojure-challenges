(ns challenge.cards
  (:require [schema.core :as s]
            [datomic.api :as d]
            [challenge.utils :as utils]))

(def Card-number #"\d{4} \d{4} \d{4} \d{4}")
(def Card-CVV (s/pred #(and (>= % 100) (<= % 999))))
(def Card-Validity #"\d{2}/\d{2}")

(s/def Card {:card/id       s/Uuid
             :card/number   Card-number
             :card/cvv      Card-CVV,
             :card/validity Card-Validity,
             :card/limit    utils/Positive-number,
             :card/costumer s/Uuid})

(s/defn create-new-card :- Card
  "Cria cartões vinculados a um cliente identificado"
  ([number :- Card-number,
    cvv :- Card-CVV,
    validity :- Card-Validity,
    limit :- utils/Positive-number
    costumer :- s/Uuid]
   (create-new-card (utils/uuid) number cvv validity limit costumer))

  ([uuid :- s/Uuid
    number :- Card-number,
    cvv :- Card-CVV,
    validity :- Card-Validity,
    limit :- utils/Positive-number
    costumer :- s/Uuid]
   {:card/id       uuid,
    :card/number   number,
    :card/cvv      cvv,
    :card/validity validity,
    :card/limit    limit,
    :card/costumer costumer}))

; Escolhemos deixar, por hora, sem o card/costumer

(defn add-new-cards!
  "Adiciona novos cartões ao banco de dados"
  [connection cards]
  (d/transact connection cards))

