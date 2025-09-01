(ns coffee-app.core
  (:require [coffee-app.utils :as utils])
  (:import [java.util Scanner])
  (:gen-class))

(def input (Scanner. System/in))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
