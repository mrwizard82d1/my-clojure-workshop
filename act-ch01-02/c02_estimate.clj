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
