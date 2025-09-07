(ns ch-03.multimethods)

;; Code from the section, "Multimethods", of _The Clojure Workshop_, chapture 03.
;;

;; Multimethods are a way to implement polymorphism; however, dispatch is based on
;;
;; - Any or all of the function arguments
;; - Occurs at **runtime** (no "vtable")
;;
;; A multimethod is a combination of two parts:
;;
;; - `defmulti`
;; - `defmethod`
;;
;; One uses `defmulti` to **define**
;;
;; - The name of the function involved in the multiple dispatch
;; - The function (of all the arguments) whose result is used to select
;;   the appropriate specific implementation of the multimethod; that is,
;;   the "dispatch function".
;;
;; One used `defmethd` to define the concrete implementations to be
;; invoked for each result returned by the dispatch function.
;;
;; Remember that the dispatch function receives **all** the arguments
;; in the call and returns a "dispatch value".
;;

;; Here's a simple example. It is **way overkill** but it illustrates the
;; pieces and how they relate.

;; We define our dispatch function externally. I'm uncertain if this choice
;; is typical.
(defn my-dispatch
  [arg1 arg2]
  (if (odd? (+ arg1 arg2))
    :odd
    :even))

;; Defining the multi-method itself
(defmulti my-multi my-dispatch)

;; Our multi-method implemantions
(defmethod my-multi :even
  [arg1 arg2]
  (println (str "The sum of "
                arg1
                " and "
                arg2
                " is even.")))

(defmethod my-multi :odd
  [arg1 arg2]
  (println (str "The sum of "
                arg1
                " and "
                arg2
                " is odd.")))

;; Some tests
(my-multi 0 2)
(my-multi 1 2)

;; Here's how we might implement the `strike` method of Parenthmazes.
(defmulti strike (fn [m] (get m :weapon)))

;; Here's a simpler defintion (more "Clojuric" if that is a word).
;; But we must remove the previously defined multi-method.
(ns-unmap 'ch-03.multimethods 'strike)
(defmulti strike :weapon)

(defmethod strike :sword
  [{{:keys [:health]} :target}]
  (- health 100))

(defmethod strike :cast-iron-saucepan
  [{{:keys [:health]} :target}]
  (- health 100 (rand-int 50)))

;; Let's test our multi-method with two different weapons
(strike {:weapon :sword :target {:health 200}})
(strike {:weapon :cast-iron-saucepan :target {:health 200}})

;; Currently, if we call `strike` and the dispatch function **does not**
;; match any of the `defmethod` dispatch values, we'll see an exception.
(try
  (strike {:weapon :spoon :target {:health 200}})
  (catch IllegalArgumentException iae
    (println (.getMessage iae))))

;; But we can address this situaton by using a dispatch value of `:default`.
(defmethod strike :default
  [{{:keys [:health]} :target}]
  ;; Essentiall, a "no-op"
  health)

;; We'll run all our tests
(strike {:weapon :sword :target {:health 200}})
(strike {:weapon :cast-iron-saucepan :target {:health 200}})
(strike {:weapon :spoon :target {:health 200}})

;; Our examples so far used a simple dispatch function; however, it can
;; be (much) more elaborate.
(ns-unmap 'ch-03.multimethods 'strike)

;; A quick test
#_(strike {:weapon :sword :target {:health 200}})

(defmulti strike
  (fn [{{:keys [:health]} :target weapon :weapon}]
    (if (< health 50)
      :finisher
      weapon)))

(defmethod strike :finisher [_] 0)

;; Because we have **unmapped** `strike`, we must re-implement our other
;; methods. Let's implement a method for `:sword` and the default.
(defmethod strike :sword
  [{{:keys [:health]} :target}]
  (- health 100))

(defmethod strike :default
  [{{:keys [:health]} :target}]
  health)

;; Once again, our tests.
(strike {:weapon :sword :target {:health 200}})
(strike {:weapon :spoon :target {:health 200}})

;; Strange. The book states that the three previous tests should have failed
;; because we unmapped `strike`; however, they work in my test setup.

;; Here's a test that invokes the new dispatch function result
(strike {:weapon :spoon :target {:health 30}})

;; Hmm. It's returning a value of 30. Unexpected.
;; ...
;; Figured it out. When I called `unmap`, I told Clojure to unmap the
;; symbol, `'strike`, in the namespace `'user`. I should have unmapped
;; this symbol in the namespace `'ch03-multimethods`. Sigh...

;; Multimethods can do other things such as:
;;
;; - Dispatch on **multiple** values (using a vector as the dispatch value)
;; - Dispatch on types and hierarchies. THe authors state, "This is useful
;;   but maybe a bit much to take on for now."
