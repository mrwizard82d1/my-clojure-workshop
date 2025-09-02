(ns ch-02.ex05)

;; A list is a sequential collection similar to a vector but
;;
;; - Items are only **added to the front** of the list
;; - Accessing a "random" item by indexing is not "constant time"
;;   but proportional to the length of the list
;;
;; Lists are mostly used to
;;
;; - Write code
;; - Write macros
;;
;; But can be used if we need a last-in, first-out (LIFO) structure.
;; For example, we might use a list to implement a stack. (Although
;; one could also implement a stack using a vector.)
;;

;; For example, let's create a to-do list as a list
(def my-todo (list "Feed the cat"
                   "Clean the bathroom"
                   "Save the world"))

;; Add an item using `cons`
(cons "Go to work" my-todo)

;; Similary, one can add an item using `conj`
(conj my-todo "Go to work")

;; Notice how the arguent order is **different** between `cons` and `conj`.
;;
;; Remember
;;
;; - `cons` is available on a list because a list is a sequence
;; - `conj` is available on a list because a list is a collection
;;
;; Additionally, `conj` is slightly more "generic." Finally, `conj` supports
;; adding **multiple** items but `cons` will only add a single item to
;; a list.

;; Add multiple items using `conj`
(conj my-todo "Go to work", "Wash my socks")

;; You've had a marvelous morning and are ready to start your day.
;; Retrieve the first item on your to-do list.
(first my-todo)

;; Once you finish that first item, you can retrieve the rest of your tasks
;; using `rest`
(rest my-todo)

;; By calling `first` on the `rest` of the list, one can navigate through
;; all the items in the list. Repeatedly calling `first` **does not** change
;; the list by the calls:
;;
;; - `(first (rest my-todo))`
;; - `(first (rest (rest my-todo)))`
;; - And so on
;;
;; Allows one to navigate through all the items in a list.

;; One can retrive an item from a list "index" by calling `nth`.
;; But remember it is not particularly efficient since navigating a list
;; starts with the `first` item and repeatedly calls `rest` and `first`
;; to get the **next item** in the list.
(nth my-todo 2)

;; Remember that `nth` will throw an exception if no such item exists.
(try
  (nth my-todo 57)
  (catch Exception e
    (println (.getMessage e))))
