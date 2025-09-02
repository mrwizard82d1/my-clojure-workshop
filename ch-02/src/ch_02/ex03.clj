(ns ch-02.ex03)

;; We will model a collection of supported currencies using a `HashSet`.
;;
(def supported-currencies
  #{"Dollar"
    "Japanese yen"
    "Euro"
    "Indian rupee"
    "British pound"})

;; As with maps, one can use `get` to retrieve an item from a set.
;; (But one must already know item in the set.) If the item is **not**
;; in the set, `get` returns `nil`.
(get supported-currencies "Dollar")
(get supported-currencies "Swiss franc")
(get supported-currencies "Dolla" :nada)

;; It is likely that one simply wants to test if an item is a member of
;; a set. In this situation, the `contains?` function is semantically better.
(contains? supported-currencies "Dollar")
(contains? supported-currencies "Swiss franc")

;; Remmber the edge case with a Set. Using `get` to determine if `nil`
;; is a member of a set **cannot** be reliably performed since:
;;
;; - `get` returns `nil` if `nil` is **not** in the set
;; - `get` returns `nil` if `nil` **is in the set**
;;
;; In this situation, `contains?` is more suitable.

;; Similar to maps, sets and keywords can be used as **functions** to
;; test for containment. For example,
(supported-currencies "Dollar")
(supported-currencies "Swiss franc")

;; However, in this particular case, since the items in the set are
;; instances of type string, one **cannot** use these items in a
;; membership test. (A string **cannot** be converted to a function.)
(try
  ("Dollar" supported-currencies)
  (catch Exception e
    (.getMessage e)))

;; This limitation is one reason for choosing keywords if possible.

;; To add an item to a set, use `conj` (conjoin).
(conj supported-currencies "Monopoly Money")

;; Similarly, one can add more than one item using `conj`
(conj supported-currencies "Monopoly Money", "Gold dragon" "Gil")

;; Finally, one can **remove** items from a set using `disj` (disjoin).
(disj supported-currencies "Dollar" "British pound")

;; Again, if an item to be removed is not in the set, Clojure reports
;; **no** error.
(disj supported-currencies "Monopoly Money")

;; Additional functions, for example, union and intersection, for working
;; with sets are available in the `clojure.set` namespace.
