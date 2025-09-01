(ns ch-01.ex-06)

;; Truthiness and nil
;;

;; Let's verify that `nil` and `false` are, indeed, falsey
(if nil "Truthy" "Falsey")
(if false "Truthy" "Falsey")

;; Clojure, unlike other languages, only has two values that are falsey:
;;
;; - `false`
;; - `nil`

;; Here are the truthy/falsey results of other values commonly evaluated
;; as false in other languages.
(defn print-truthy-falsey
  [x]
  (println (pr-str x ) "is" (if x "Truthy" "Falsey")))

(doseq [x [0 -1 '() [] "false" ""
           "The truth might not be pure but it is simple"]]
 (print-truthy-falsey x))

;; Clojure also provides predicates that test for exactly true and
;; exactly false
(true? 1)
(if (true? 1) "Yes" "No")
(true? "true")
(true? true)
(false? nil)
(false? false)

;; Similarly, one can use the `nil?` predicate to determine if a value is
;; exactly `nil`
(nil? false)
(nil? nil)
(nil? (println "Hello"))

;; In addition, Clojure provides the `and` and `or` functions to
;;
;; - Determine if all operands are truthy
;; - Determine if any operands are truthy, respectively.

;; The `and` function returns the **first** falsey value if the expression
;; is false; otherwise, it returns the **last** value.
(and "Hello")
(and "Hello" "Then" "Goodbye")
(and false "Hello" "Goodbye")

;; We can use `println` to ensure that not all expressions are evaluated
(and (println "Hello")
     (println "Goodbye"))

;; The `or` function works similarly to `and`. In detail, it returns the
;; **first truthy** value of all its arguments; otherwise, it returns
;; the value of the **last** argument
(or "Hello")
(or "Hello" "Then" "Goodbye")
(or false "Then" "Goodbye")

;; Also similarly, we can verify that `or` "short-cicuits"
(or true (println "Hello"))
