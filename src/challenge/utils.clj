(ns challenge.utils
  (:require [java-time :as time]
            [schema.core :as s]))

(defn set-time
  ([]
   (time/format "dd/MM/yyyy hh:mm:ss" (time/local-date-time)))
  ([year month day hour minute second]
   (time/format "dd/MM/yyyy hh:mm:ss" (time/local-date-time year month day hour minute second))))

(def Positive-number (s/pred pos?))

(def Id java.lang.Long)

(defn uuid [] (java.util.UUID/randomUUID))

