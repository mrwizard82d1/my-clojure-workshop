(ns ch-02.collection-and-sequence)

;; Code from the section, _Collection and Sequence Abstractions_,
;; from chapter 2 of "The Clojure Workshop"

;; Let's create something to work with
(def language {:name "Clojure"
               :creator "Rich Hickey"
               :platforms ["Java" "JavaScript" ".NET"]})

;; We can count the elements in a collection
(count language)

;; An empty set contains **no** elements
(count #{})

;; We can test collections for "emptiness"
(empty? language)

(empty? [])

(empty? {})

(empty? '())

(empty? #{})

(every? empty? [[] {} '() #{}])
(some empty? [[] {} '() #{}])

;; A map is a collection; it is not a sequence because its elements
;; are **unordered**.
(seq? language)

;; However, one can convert a map (and other collections) to a `seq`.
(seq language)

;; Once converted to a `seq`, any function that operates on `seqs`
;; will work.
(nth (seq language) 1)

;; Remember, many functions simply work on collections (that may not
;; be `seqs`.) In these cases, one can **omit** "wrapping" the collection
;; in a `seq`. These collections can be passed as arguments to functions
;; like:
;;
;; - `first`
;; - `rest`
;; - `last`
(first (nth (seq language) 1))
(last (nth (seq language) 1))
(rest (seq language))

;; Some other examples
(first #{:a :b :c})
(rest #{:a :b :c})
(last #{:a :b :c})

;; The value of converting collections to `seq`s may seem questionable
;; now, but this conversion also allows the usage of other functions like
;;
;; - `map`
;; - `filter`
;; - `reduce`

;; Another useful function to put elements of one collection into another
;; is `into`.
(into [1 2 3 4] #{5 6 7 8})
(into #{1 2 3 4} [5 6 7 8])

;; One way to deduplicate items in a collection supporting duplicates is
(into #{} [1 2 3 3 3 4])

;; Remember: this action will most likely **change the order** of items
;; compared to the original collection.

;; To put items into a map, one must pass a collection of **tuples**
;; that represent key-value pairs. For example,
(into {} [[:a 1] [:b 2] [:c 3]])

;; The `into` function "conjoins" each item in the original collection;
;; consequently, the semantics of **where** items are added depends on
;; the target collection.
;;
;; For exmaple, using `into` with a target of type list follows LIFO
;; but `into` with a vector target uses FIFO
(into '() [1 2 3 4])
(into '[] [1 2 3 4])

;; Although one could use `into` to concatenate two collections, Clojure
;; actually offers the `concat` function.
(concat '(1 2) '(3 4))
(concat [1 2] [3 4])

;; Contract the behavior of `concat` with `into`
(concat '(1 2) '(3 4))
(into '(1 2) '(3 4))

;; Remember - `concat` returns a `seq`! This behavior may not be **what
;; you expect**!
(concat #{1 2 3} #{1 2 3 4})
(concat {:a 1} ["Hello"])

;; The function, `sort`, is another example of a function that returns
;; a `seq`
(def alphabet #{:a :b :c :d :e :f})
alphabet
(sort alphabet)

(sort [3 7 5 1 9])

;; But what if you wanted a result of type vector?
;; `into` to the rescue!
(into [] *1)

;; Note that `conj` can be used with maps; however, its arguments must
;; be **pairs**.
(conj language [:created 2007])

;; Perhaps unexpectedly, a **vector** is as associative collection
;; of **key-value pairs**.
(assoc [:a :b :c :d] 2 :z)

;; From the next section, Clojure offers a number of methods to manipulate
;; nested maps:
;;
;; - `get-in` gets an item nested within a map
;; - `assoc-in` associates a new item within a nested map
;; - `update-in` updates an existing item within a nested map using
;;    a function
(def gemstones-db {:ruby
                   {:name "Ruby"
                    :stock 480
                    :sales [1990 3644 6376 4918 7882
                            6747 7495 8573 5097 1712]
                    :properties {:dispersion 0.018
                                 :hardness 9.0
                                 :refractive-index [1.77 1.78]
                                 :color "Red"}}})

(get-in gemstones-db [:ruby :properties :dispersion])
(get-in (assoc-in gemstones-db
                  [:ruby :properties :color]
                  "Nearly colorless through pink through red to crimson.")
 [:ruby :properties :color])

;; Replace the vector of refractive indices with the average
(let [path [:ruby :properties :refractive-index]
      refractive-indices (get-in path gemstones-db)
      indices-avg-f #(/ (apply + %) (count %))]
  (get-in (update-in gemstones-db path indices-avg-f) path))
