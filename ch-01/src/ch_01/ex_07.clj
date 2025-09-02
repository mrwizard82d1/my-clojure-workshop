(ns ch-01.ex-07)

;; Comparing values
;;

;; Compare two numbers
(= 1 1)
(= 1 2)

;; Like other arithmetic operators, `=` supports more than two arguments
(= 1 1 1)
(= 1 1 1 -1)

;; The `=` operator con be used to compare types other than numbers
(= nil nil)
(= false nil)
(= "hello" "hello" (clojure.string/reverse "olleh"))
(= [1 2 3] [1 2 3])

;; Perhaps surprisingly, sequences of different types but with the same
;; items **are equal**
(= '(1 2 3) [1 2 3])

;; THe `=` operator accepts a **single argument** which **always**
;; evaluates to `true`
(= 1)
(= "I will not reason and compare: my business is to create")

;; Similarly, `<` returns `true` if all its arguments are
;; **strictly increasing**.
(< 1 2)
(< 1 10 100 1000)
(< 1 10 10 100)
(< 3 2 3)
(< -1 0 1)

;; The `<=` function is similar but allows equal adjacent items
(<= 1 10 10 100)
(<= 1 1 1)
(<= 1 2 3)

;; The operators `>` and `>=`, as expected, return true when their
;; arguments are in **decreasing** order. Additionally, as expected,
;; `>=` allows adjacent equal values.
(> 3 2 1)
(> 3 2 2)
(>= 3 2 2)

;; Finally, the `not` operator returns `true` when its arguments is
;; **falsey** and `false` when its argument is **truthy**.
(not true)
(not nil)
(not (< 1 2))
(not (= 1 1))

;; The following Clojure code is a "port" of the JavaScript code
;; let x = 50;
;; if (x >= 1 && x <= 100) || x % 100 == 0) {
;;   console.log("Valid");
;; } else {
;;   console.log("Invalid")
;; }
(let [x 50]
  (if (or (<= 1 x 100)
          (= (mod x 100) 0))
   (println "Valid")
   (println "Invalid")))

;; If, instead, we used the JavaScript ternary operator
;; let x = 50;
;; console.olg(x >= 0 && x <= 100 || x % 100 == 0 ? "Valid" : "Invalid")

;; A similar Clojure code block could be:
(let [x 50]
  (println (if (or (<= 1 x 100)
                   (= 0 (mod x 100)))
             "Valid"
             "Invalid")))
