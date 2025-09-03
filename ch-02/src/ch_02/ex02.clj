(ns ch-02.ex02)

;; How to access and modify simple maps
;;
(def favorite-fruit
  {:name "Kiwi"
   :color "Green"
   :kcal_per_100g 61
   :distinguish_mark "Hairy"})
favorite-fruit

;; One can read an entry from the map using `get`
(get favorite-fruit :name)
(get favorite-fruit :color)

;; If the value for a key **cannot** be found, `get` returns `nil`
(get favorite-fruit :taste)

;; However, an overload of `get` taking 3 arguments uses the third
;; argument as a default value if the value sought is **not** in the map
(get favorite-fruit :taste "Very good 8/10")

;; Maps and keywords can also act as **functions** when looking up values
;; in a map
(favorite-fruit :color)
(:color favorite-fruit)

;; Using the map or the keyword as a function return `nil` if no such key
;; exists. Just like the 3-item overload of `get`, using either the map or
;; the keyword support a 2-item overload in which the last argument is
;; the default value returned when no such item is found.
(favorite-fruit :taste)
(:taste favorite-fruit)
(favorite-fruit :taste "Very good 8/10")
(:taste favorite-fruit "Very good 8/10")

(:shape favorite-fruit "egg like")

;; Use `assoc` to associate a new key with a new value. For example,
(assoc favorite-fruit :taste "egg-like")

;; Remember that the function, `assoc`, returns a **new map**; the old map
;; is unchanged. For example,
favorite-fruit

;; Let's change the color of a kiwi in `favorite-fruit`
(assoc favorite-fruit :color "Brown")

;; But `favorite-fruit` remains unchanged
favorite-fruit

;; We can nest a map within our map
(assoc favorite-fruit
       :yearly-production-in-tonnes
       {:china 2025000
        :italy 541000
        :new_zealand 412000
        :iran 311000
        :chile 225000})

;; Nested maps or other data types are commonly used to represent
;; structured information

;; Let's decrement the `:kcal_per_100g value by 1`.
;; We start by using `assoc`.

(assoc favorite-fruit :kcal_per_100g (- (:kcal_per_100g favorite-fruit) 1))

;; Although `assoc` works, using `update` is a more elegant way to provide
;; this behavior.
(update favorite-fruit :kcal_per_100g dec)

;; Remember, using `assoc` requires us to "make a calculation", but using
;; `update` supplies a function which is invoked by the run-time.

;; Suppose the function supplied to `update` required **additional
;; arguments**. To supply this additional arguments, simply supply them
;; after the name of the function passed to the `update` function.
;; For example,
(update favorite-fruit :kcal_per_100g - 10)

;; Just like `assoc`, `update` returns the a new map leaving the original
;; value **unchanged**.`

;; Finally, one can use `dissoc` to remove one or multiple elements from
;; a map.
(dissoc favorite-fruit :distinguish_mark)
(dissoc favorite-fruit :kcal_per_100g :color)
