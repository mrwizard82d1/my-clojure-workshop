(ns ch-01.ex-01)

;; Your first dance
;;

;; Evaluate an expression
"Hello REPL!"

;; Typpe in multiple strings
"Hello" "Again"

;; Arithmetic - but not as expected
1 + 2

;; Arithmetic - Cloqjure style
(+ 1 2)

;; Arithmetic - with three arguments!
(+ 1 2 3)

;; Other arithmetic operators
(- 3 2)
(* 3 4 1)
(/ 9 3)

;; Printing stuff at the REPL
(println "Would you like to dance?")

;; Printing **and** arithmetic
(println (+ 1 2))

;; "Chaining" functions (!?)
(* 2 (+ 1 2))

;; Exiting the REPL
;; Either `Ctrl + D` or...
(System/exit 0)
