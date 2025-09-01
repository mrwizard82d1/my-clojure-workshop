(ns ch-01.ex-05)

;; Creating Simple functions with `fn` and `defn`
;;

;; Let's create our first function
(fn [])

;; This function is perhaps the simplest of all functions: a complete
;; "no-op". This function takes no arguments and returns `nil`.
;; Evaluating the function definition itself returns a **function**.

;; A function that takes a single parameter, `x` and returns the square
;; of `x`.
(fn [x] (* x x))

;; The previous form creates a function; however, it is not especially
;; useful since it immediately becomes eligible for garbage collection
;; after it is created.

;; We can use our square function by wrapping the function definition
;; in parentheses and supplying a value **after** the function definition.
;; However, once this outer expression is evaluated, the function is again
;; eligible for garbage collection.
((fn [x] (* x x)) 2)

;; We have **defined** a number of functions. How do we bind these
;; "function values" to a name?
(def square (fn [x] (* x x)))
(square 2)
(square *1)
(square *1)

;; Defining functions this way provides a symbol to refer to the function;
;; however, this pattern of defining a function and immediately binding it
;; to a name is so common, that Clojure provides the `defn` macro to
;; perform these actions (and others).
(defn square [x] (* x x))
(square 10)

;; Remember, arguments passed to a function when it is evaluated will be
;; bound to the actual arguments defined for the function (`x` for the
;; `square` function).

;; Functions can take multiple arguments. Let's define a function,
;; `meditate`, that expects two arguments, a string, `s`, and a Boolean,
;; `calm`, and prints an introductory message and a transformation of `s`
;; based on `calm`.
(defn meditate [s calm]
  (println "Clojure Meditate v1.0")
  (if calm
    (clojure.string/capitalize s)
    (str (clojure.string/upper-case s) "!")))
(meditate "oom" true)
(meditate "oom" false)

(meditate "in calmness lies true pleasure" 1.0)
(meditate "in calmness lies true pleasure" nil)

;; If we **do not supply** the second argument, an exception will be thrown
(try
  (meditate "in calmness lies true pleasure")
  (catch Exception e
    (.getMessage e)))

;; We've seen how `doc` can be used to extract the documentation of
;; built-in functions. We can provide documentation to our own functions
;; that can also be used by the `doc` function. It is a good practice to
;; surround function arguments with backticks. This convention helps IDE
;; tools to recognize these "words" as symbols and treat them appropriately.
(defn square
  "Returns the product of the number, `x`, with itself"
  [x]
  (* x x))
(square 11)
(clojure.repl/doc square)
