(ns ch-01.activity-02)

;; Create a function to "predict" future CO-2 levels based on data from
;; 2006 to 2020.
(defn co2-estimate
  "Estimates the \"future\" CO-2 level based on data from 2006 to 2020."
  [year]
  (let [base-year 2006
        base-co2 382
        final-year 2020
        final-co2 410
        year-diff (- year base-year)
        slope (/ (- (float final-co2) base-co2)
                 (- final-year base-year))]
    (+ (* slope year-diff)
       base-co2)))

(clojure.repl/doc co2-estimate)
(co2-estimate 2006)
(co2-estimate 2020)
(co2-estimate 2050)
