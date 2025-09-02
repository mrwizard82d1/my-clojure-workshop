(ns ch-02.ex04)

;; A vector can be thought as as an immutable array. Vectors allow
;;
;; - Efficient access by index
;; - Maintenance of items in insertion order
;; - Duplicate items
;;

;; Look up items by index using the `get` function
(get [:a :b :c] 0)
(get [:a :b :c] 2)

;; If the index is outside the bounds of the vector, `get` returns `nil`
(get [:a :b :c] 10)
(get [:a :b :c] -1)

;; We'll bind a vector to a symbol to make usage more convenient
(def fibonacci [0 1 1 2 3 5 8])
(get fibonacci 6)

;; Just like with sets and maps, one can use the vector as a function
(fibonacci 6)

;; But just like with a set of strings, trying to use the index in the
;; function position will fail.
(try
  (8 fibonacci)
  (catch Exception e
    (.getMessage e)))

;; We append items to a vector using `conj`
(conj fibonacci 13 21)

;; Similarly, one can use the definition of the Fibonacci sequence to
;; `conj`(oin) an additial item
(let [;; calculate the size of our sequence
      size (count fibonacci)
      ;; get the last item in our sequence
      last-number (last fibonacci)
      ;; use `fibonacci` as a function to get the next to the last item
      second-to-last-number (fibonacci (- size 2))]
  ;; Finally, calculate the next item in the sequence
  (conj fibonacci (+ last-number second-to-last-number)))
