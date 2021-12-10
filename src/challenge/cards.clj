(ns challenge.cards
  (:require [schema.core :as s]
            [challenge.schema-utils :as s.utils]))

(def Card-number #"\d{4} \d{4} \d{4} \d{4}")
(def Card-CVV (s/pred #(and (>= % 100) (<= % 999))))
(def Card-Validity #"\d{2}/\d{2}")
(def Costumer-CPF #"\d{3}\.\d{3}\.\d{3}-\d{2}")

(s/def Card {:card/number   Card-number
             :card/cvv      Card-CVV,
             :card/validity Card-Validity,
             :card/limit    s.utils/Positive-number,
             :costumer/name s/Str
             :costumer/cpf  Costumer-CPF})

(s/defn create-new-card :- Card
  "função que cria novo cartão"
  [card-number :- Card-number,
   card-cvv :- Card-CVV,
   card-validity :- Card-Validity,
   card-limit :- s.utils/Positive-number,
   costumer-name :- s/Str,
   costumer-cpf :- Costumer-CPF]
  {:card/number   card-number,
   :card/cvv      card-cvv,
   :card/validity card-validity,
   :card/limit    card-limit,
   :costumer/name costumer-name
   :costumer/cpf  costumer-cpf
   })

; lógicas referentes aos cartões (ex.: adicionar cartões) serão neste arquivo