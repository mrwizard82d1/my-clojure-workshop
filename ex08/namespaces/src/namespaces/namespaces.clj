(ns namespaces.namespaces)

;; Ex. 8.02 Navigating Namespaces
;;

;; Switch REPL namespace
;; (in-ns 'new-namespace)

;; Define a var "in 'new-namespace"
(def fruits ["orange" "apple" "melon"])

;; Switch REPL namespace again
;; (in-ns 'other-nampsace)

;; In `other-namespace, one **cannot** access a var defined in 'new-namespace`
fruits

;; However, one can access this var from a different namespace using a
;; fully-qualified name
new-namespace/fruits
