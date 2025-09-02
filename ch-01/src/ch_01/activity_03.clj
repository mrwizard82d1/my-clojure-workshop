(ns ch-01.activity-03)

;; The meditate function v2.0
;;
(defn meditate
  "Meditate on `s` depending on `calmness-level`"
  [s calmness-level]
  (println "Clojure Meditate v2.0")
  (if (< calmness-level 5)
    (println (clojure.string/upper-case s) ", I TELL YA!")
    (if (<= 5 calmness-level 9)
      (println (clojure.string/capitalize s))
      (when (= 10 calmness-level)
        (println (clojure.string/reverse s))))))
(meditate "what we do now echoes in eternity" 1)
