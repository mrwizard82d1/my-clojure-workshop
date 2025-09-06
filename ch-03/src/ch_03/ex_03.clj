(ns ch-03.ex-03)

;; Multi-arity and Destructuring with Parenthmazes
;;

;; The damage caused by different weapons
(def weapon-damage
  {:fists 10.0
   :staff 35.0
   :sword 100.0
   :cast-iron-saucepan 150.0})

;; Strike an enemy with a weapon
(defn strike
  ([target weapon]
   (let [points (weapon weapon-damage)]
     (if (= :gnomes (:camp target))
       (update target :health + points)
       (update target :health - points)))))

;; What happens with an enemy?

;; Create an enemy entity for testing
(def enemy {:name "Zulkaz"
            :health 250
            :camp :trolls})

;; Strike the enemy with a sword
(strike enemy :sword)

;; What happens with a friendly player?

;; Create an ally
;;
;; But one must spell `:health` correctly to work correctly
(def ally {:name "Carla"
           :health 80
           :camp :gnomes})

(strike ally :staff)

;; Add the "armor" feature to `strike`
(defn strike
  ([target weapon]
   (let [points (weapon weapon-damage)]
     (if (= :gnomes (:camp target))
       (update target :health + points)
       (let [armor (or (:armor target) 0)
             damage (* points (- 1 armor))]
         (update target :health - damage))))))

;; Some more testing
(strike enemy :cast-iron-saucepan)

;; But what if our enemy is wearing armor?
(def enemy {:name "Zulkaz"
            :health 250
            :armor 0.8
            :camp :trolls})

;; And. because of the armor, the damage is reduced.
(strike enemy :cast-iron-saucepan)

;; Modify the `strike` function to use associate destructuring in the
;; functions parameters
(defn strike
  [{:keys [camp armor] :as target} weapon]
  (let [points (weapon weapon-damage)]
    (if (= :gnomes camp)
      (update target :health + points)
      (let [damage (* points (- 1 (or armor 0)))]
        (update target :health - damage)))))
(strike enemy :cast-iron-saucepan)

;; Use the special key, `:or`, in our destructured map to provide a
;; default value. Add an extra arity to make the weapon option. Finally,
;; add some documentation.
(defn strike
  "With one argument, strike a `target` with `weapon` (default: `:fists`.

  With two arguments, strike `target` with `weapon`.

  Strike will heal a target that belongs to the gnomes camp."
  ([target] (strike target :fists))
  ([{:keys [camp armor], :or {armor 0}, :as target} weapon]
   (let [points (weapon weapon-damage)]
     (if (= :gnomes camp)
       (update target :health + points)
       (let [damage (* points (- 1 armor))]
         (update target :health - damage))))))

;; Our final tests
(strike enemy)

(strike enemy :cast-iron-saucepan)

(strike ally :staff)
