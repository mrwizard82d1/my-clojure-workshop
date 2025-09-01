(ns ch-01.ex-04)

;; Bindings
;;
;; - `def` for global bindings
;; - `let` for local bindings
;;

;; Use `def` to bind a symbol to a value. For example, the following form
;; binds the value, 10, to the symbol, `x` in **this namespace**.
(def x 10)
x

;; Technically, one can change a binding created with `def`; however,
;; is is only "fine" when experimenting in the REPL.
(def x 20)
x

;; This technique is **not** recommended for programs:
;;
;; - It can make code harder to read
;; - It con complicate maintenance
;; - It is so very **unexpected**.

;; After we have bound `x` to a value, we can use it in expressions
(inc x)

;; Again, this usage **does not** change the value of `x`
x

;; Remember that `def` only binds symbols and values
;; **in the current namespace**. A `def` binding for a symbol in this
;; namespace **does not** mean that binding is available in another
;; namespace.

;; Don't use the following expression in your code.
x
(do (def x 42))
x

;; Remember, any use of `def` affects the **global** scope of the source
;; file no matter how **deeply** the `def` is encountered in other
;; expressions.

;; Additionally, remember that a symbol bound to a value using `def` is
;; **automatically namespaced**. This default action helps avoid clashes
;; between existing name is **different** namespaces.

;; The special form, `let`, allows us to create **new bindings** within
;; a **local** scope (the scope of the `let` form).
(let [y 3]
  (println y)
  (* 10 y))

;; If one now tries to access the value of `y`, an exception will be raised
#_(try
  (println y)
  (catch Exception e
    (println (.getMessage e))))

;; But how do `let` bindings affect `def` bindings? As the crew head says
;; in "The Hitchhikers Guide to the Galaxy", "Not at all."
(let [x 3]
  (println x))
x

;; Remember, `let` bindings **shadow** `def` bindings.

;; The `let` form allows one to create **multiple** let bindings
(let [x 10
      y 20]
  (str "x is " x " and y is " y))

;; Let's combine all the concepts we've learned about bindings.
(def message "Let's add them all!")
(let [x (* 10 3)
      y 20
      z 100]
  (println message)
  (+ x y z))
