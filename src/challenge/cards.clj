(ns challenge.cards
  (:require [schema.core :as s]
            [challenge.utils :as utils]))

(def Card-number #"\d{4} \d{4} \d{4} \d{4}")
(def Card-CVV (s/pred #(and (>= % 100) (<= % 999))))
(def Card-Validity #"\d{2}/\d{2}")

(s/def Card {:card/id         s/Uuid
             :card/number     Card-number
             :card/cvv        Card-CVV,
             :card/validity   Card-Validity,
             :card/limit      utils/Positive-number,
             (s/optional-key :card/costumer) s/Uuid})

(s/defn create-new-card :- Card
  "função que cria novo cartão"
  ([card-number :- Card-number,
    card-cvv :- Card-CVV,
    card-validity :- Card-Validity,
    card-limit :- utils/Positive-number]
   (create-new-card (utils/uuid) card-number card-cvv card-validity card-limit))

  ([card-id :- s/Uuid
    card-number :- Card-number,
    card-cvv :- Card-CVV,
    card-validity :- Card-Validity,
    card-limit :- utils/Positive-number]
   {:card/id       card-id
    :card/number   card-number,
    :card/cvv      card-cvv,
    :card/validity card-validity,
    :card/limit    card-limit}))

; Escolhemos deixar, por hora, sem o card/costumer

; lógicas referentes aos cartões (ex.: adicionar cartões) serão neste arquivo