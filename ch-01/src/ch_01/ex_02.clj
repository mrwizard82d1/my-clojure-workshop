(ns ch-01.ex-02)

;; Getting around in the REPL
;;

;; We start with this form to experiment with `*1`, `*2` and `*3`` in the REPL`
(inc 10)

;; We'll also generate an exception to work with `*e`
;; (/ 1 0)

;; Using `doc`, `find-doc` and `apropos`.
(doc str)
(doc doc)
(doc find-doc)
(doc apropos)

;; A `find-doc` examsqle
(find-doc "conj")

;; An `arpropos` example
(apropos "conj")

;; Let's use the `str` function
(str "I" "will" "be" "concatenated")
(str "This" " works " "too")

;; I'm too aggressive
(doc doc)

;; One can look at the documentation of a namespace
(doc clojure.repl)

;; Use `find-doc` when you're uncertain of the name.
(find-doc "modulus")

;; Although we found nothing matching "modulus", remember that the `find-doc`
;; search is **case sensitive**. However, since it is a regular expression
;; search, we can make it case insensitive.
(find-doc #"(?i)modulus")

;; THis search finds the `clojure.core/mod` function - because the
;; description contains the word "modulus".

;; Let's test out the `mod` function
(mod 7 3)

;; We can use `apropos` to search for one or more functions by name
;; (not) descriptions. For example,
(apropos "case")

;; Then, we can experiment with one of the matches
(clojure.string/upper-case "Shout, shout, let it all out")
