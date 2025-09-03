(ns ch-02.ex06
  (:require [clojure.pprint :as pprint]))

;; Working with nested data structures
;;

;; Our gemstone "database"
(def gemstone-db
  {:ruby
   {:name "Ruby"
    :stock 120
    :sales [1990 3644 6376 4918 7882 6747 7495 8578. 5097 1712]
    :properties
    {:dispersion 0.018
     :hardness 9.0
     :refractive-index [1.77 1.78]
     :color "Red"}},
  :diamond
   {:name "Diamond"
    :stock 10
    :sales [8295 329 5960 6118 4189 3436 9833 8870 9700 7182 7061 1579]
    :properties
    {:dispersion 0.044
     :hardness 10
     :refractive-index [2.417 2.419]
     :color "Typically yellow, brown or grey to colorless"}},
  :moissanite
   {:name "Moissanite"
    :stock 45
    :sales [7761 3220]
    :properties
    {:dispersion 0.104
     :hardness 9.5
     :refractive-index [2.65 2.69]
     :color "Colorless, green, yellow"}}})

;; Let's develop a function for durability. This function has two arguments:
;;
;; - `db`
;; - `gem-name`
;;
;; It returns the `:hardness` property of the specified gem.

;; Here's a candidate implementation
(get (get (get gemstone-db :ruby) :properties) :hardness)

;; The implementation works but it is hard to read because of the nested
;; `get` calls

;; What if we use keywords as functions? Will that implemetation be easier
;; to understand?
(:hardness (:properties (:ruby gemstone-db)))

;; THis implementation is simpler and therefore easier to understand but....

;; Here's a new thought: use the `get-in` function
(get-in gemstone-db [:ruby :properties :hardness])

;; This implementation is much more readable than the other candidates.

;; Now that we have experimented, we can create a `durablity` function
(defn durability
  [db gemstone]
  (get-in db [gemstone :properties :hardness]))
(durability gemstone-db :ruby)
(durability gemstone-db :moissanite)

;; Apparently a ruby is not simply "red" but "Near colorless through pink
;; through all shades of red to a deep crimson." We must now create a
;; function to update the color of a gem in the dabatase.

;; Again, we begin with some experiments starting withth `assoc`.
(assoc (:ruby gemstone-db)
       :properties
       {:color
        "Near colorless through pink through all shades of red to a deep crimson"})

;; Generally, this idea works but **we lose all other properties!

;; We can rely on a "trick": use a combination of `update` and `into`.
;; Here's a "refresher" on `into`
(into {:a 1 :b 2} {:c 3})

;; Here's the combination of `into` and `update`
(update
 (:ruby gemstone-db)
 :properties
 into
 {:color "Near colorless through pink through all shades of red to a deep crimson"})

;; This idea works; however, trying to understand the `update`-`into`
;; combination is a bit difficult - and setting the color to a long
;; string **does not** make understanding any easier. Second, the
;; function **does not** return the entire database but only the map
;; for the gemstone of interest. To update the database, we would
;; similar logic to update the database. Further, updating the database
;; might again involve **another** `update`-`into` combination.

;; Clojure offers a simpler way to deal with nested maps similar to
;; `get-in`: `assoc-in` and `update-in`. Just like `get-in`, they
;; accept a vector of keys to identity the (deeply) nested data to
;; change.

;; We use `update-in` to update a deeply nested value by
;; **calling a function**. Since we simply want to replace one value with
;; another, we can use `assoc-in`

;; Here's an experiment
(assoc-in gemstone-db
          [:ruby :properties :color]
          "Near colorless through pink through all shades of red to a deep crimson")

;; This function returns the **entire** database only updating the
;; `:color` property for `:ruby`. Understanding this result is a bit
;; difficult. We can actually use the function, `clojure.pprint/pprint`,
;; to make it easier on the human.
(pprint/pprint *1)

;; Finally, after experimentation (and learning) at the REPL, we can write
;; the `change-color` function.
(defn change-color
  [db gemstone new-color]
  (assoc-in db
            [gemstone :properties :color]
            new-color))

;; Our tests
(change-color gemstone-db :ruby "Near colorless through pink through all shades of red to a deep crimson")

;; We have one more function to write. WHen a sale occurs, the shop owner
;; would like to call a function named `sell` with three arguments:
;;
;; - A database
;; - A gemstore (keyword)
;; - A client ID.
;;
;; The result of the function will be to
;;
;; - Insert the supplied `:sales` vector to add the supplied `client-id`
;; - Decrease the `stock` for the gem by one (1)
;;
;; Like other functions, this function will return a new database with
;; these changes.

;; We can use the `update-in` function in combination with `dec`. Let's
;; experiment  in the REPL.
(update-in gemstone-db  [:diamond :stock] dec)

;; Although I was able to use some CIDER tools to verify the behavior,
;; it is not easy. Does the REPL expose options that one could set to
;; make the output more readable?
;;
;; Yes. We can use the *print-level* option to limit the depth of the
;; data structure printed in the REPL.
(set! *print-level* 2)
(update-in gemstone-db  [:diamond :stock] dec)

;; We can use `update-in` again, with `conj` and a `client-id` to
;; update the `sales` vector. Let's try an example with the diamond
;; gem and `client-id` 999.
(update-in gemstone-db  [:diamond :sales] conj 999)

;; Argh! It might have worked, but seeting `*print-level* to 2 previously`
;; (and permanently) limits our ability to "see" the result.
;;
;; To reset the option, we set the `*print-level*` option to `nil``
(set! *print-level* nil)

;; Now, we can try adding `client-id` 999 again.
(update-in gemstone-db  [:diamond :sales] conj 999)

;; We can now "manually" verify that our `update-in` call using `conj`
;; accomplished the goal.

;; Let's now write our pure function that combines these two operations.
(defn sell
  [db gemstone client-id]
  (let [clients-updated-db (update-in db [gemstone :sales] conj client-id)]
    (update-in clients-updated-db [gemstone :stock] dec)))

;; A couple of tests
(sell gemstone-db :diamond 999)
(sell gemstone-db :moissanite 123)
