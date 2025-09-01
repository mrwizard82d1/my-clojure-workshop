(ns ch-01.ex-03)

;; Working with `if`, `do`, and `when`
;;

;; Using `if`
(if true "Yes" "No")

;; A more "interesting" use of `if`
(if false (+ 3 4) (rand))

;; But what about `do`
(clojure.repl/doc do)

;; Let's try to use it
;;
;; Although not obvious - and some optimization may alter the operation,
;; the `do` form evaluates **all** its (child) expressions but only
;; returns the value of the **last** expression.
;;
;; A typical used of `do` is to evaluate expressions that have
;; **side-effects** before evaluating the last expression in the form.
(do (* 3 4) (/ 8 4) (+ 1 1))

;; Let's demonstrate the side-effects when evaluating a `do` form.
(do (println "A proof that this is executed.")
    (println "And this, too."))

;; Finally, one can combine `if` and `do` to execute multiple expressions
;; in either the true branch, the false branch, or both.
(if true
  (do (println "Calculating a random number...")
      (rand))
  (+ 1 2))  ;; Never evaluated.

;; Because the previous evaluation of the `if` form **does not** evaluate
;; the false expression, we technically need not supply it.
(if true
  (do (println "Calculating a random number...")
      (rand)))

;; Similarly
(if false
  (println "Not going to happen."))

;; However, the `when` form is designed for conditions that evaluate
;; expressions only when true.
(when true
  (println "First argument to `when`")
  (println "Second argument to `when`")
  "And the last is returned.")
