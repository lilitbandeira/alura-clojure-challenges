(ns challenge.schema-utils
  (:require [java-time :as time]))

(defn set-time
  ([]
   (time/format "dd/MM/yyyy hh:mm:ss" (time/local-date-time)))
  ([year month day hour minute second]
   (time/format "dd/MM/yyyy hh:mm:ss" (time/local-date-time year month day hour minute second))))

(def Positive-number (s/pred pos?))