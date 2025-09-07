(ns ch-03.ex-05)

;; Using Multimethods
;;

;; Here is an example of a "new" player in our game that captures the
;; position of the player
{:name "Lea"
 :health 200
 :position {:x 10
            :y 10
            :facing :north}}

;; Let's now remember a modified player
(def player
  {:name "Lea"
   :health 200
   :position {:x 10
              :y 10
              :facing :north}})

;; Let's create the `move` (multi-)method.
(ns-unmap 'ch-03.ex-05 'move)
(defmulti move #(:facing (:position %)))

;; An alternative using `get-in`
(ns-unmap 'ch-03.ex-05 'move)
(defmulti move #(get-in [:facing :position] %))

;; The "recommended" alternative using `comp` to compose our lookup
(ns-unmap 'ch-03.ex-05 'move)
(defmulti move (comp :facing :position))

;; Create our first implementation of `move` with the `:north` dispatch
;; value
(defmethod move :north
  [entity]
  (update-in entity [:position :y] inc))

;; Let's test this implementation
(move player)

;; Let's create the remaining "compass point" implemntations
(defmethod move :south
  [entity]
  (update-in [:position :y] dec))

(defmethod move :east
  [entity]
  ;; I think we are using a right-handed coordinate system.
  ;; The authors seem to think differently.
  #_(update-in [:position :x] inc)
  (update-in [:position :x] dec))

(defmethod move :west
  [entity]
  ;; I think we are using a right-handed coordinate system.
  ;; The authors seem to think differently.
  #_(update-in [:position :x] dec)
  (update-in [:position :x] inc))
