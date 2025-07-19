(ns act-ch01-02)

;; Base CO-2 levels
(def base-co2 382)

;; Base year for calculations
(def base-year 2006)

(defn co2-estimate
  "Extrapolates atmostpheric CO-2 levels from NOAA data."
  [year]
  (let [year-diff (fn [year]
                    (- year 2006))]
    (+ base-co2
       (* 2 (year-diff year)))))

;; (defn meditate
;;   "Enter a state of meditation depending on pre-existing calmness level"
;;   [s calmness-level]
;;   (println "Clojure Meditate 2.0")
;;   (cond
;;     (< calmness-level 5) (str (clojure.string/upper-case s) ", I TELL YA!")
;;     (<= 5 calmness-level 9) (str (clojure.string/capitalize s) "!")
;;     (= calmness-level 10) (clojure.string/reverse s)))
