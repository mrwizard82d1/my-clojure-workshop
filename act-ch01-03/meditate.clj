(ns meditate)

(defn meditate
  "Enter a state of meditation depending on pre-existing calmness level"
  [s calmness-level]
  (println "Clojure Meditate 2.0")
  (cond
    (< calmness-level 5) (str (clojure.string/upper-case s) ", I TELL YA!")
    (<= 5 calmness-level 9) (str (clojure.string/capitalize s) "!")
    (= calmness-level 10) (clojure.string/reverse s)))
