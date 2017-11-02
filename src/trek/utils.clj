(ns trek.utils
  (:import (java.util Date)
           (java.text SimpleDateFormat)))

(defn fmt-date [date]
  (when (instance? Date date)
    (.format (SimpleDateFormat. "yyyyMMddHHmmss") date)))

(defn now []
  (new Date))

(defmacro try! [fn]
  `(try
     [~fn nil]
     (catch Exception e#
       [nil (.getMessage e#)])))
