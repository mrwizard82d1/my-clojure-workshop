(ns ch-03.ex-04)

;; Higher-Order Functions with Parenthmazes
;;

;; Define our initiol waapons map
(def weapon-fn-map
  {:fists
   (fn [health] (if (< health 100)
                  (- health 10)
                  health))})

;; Let's test this that we correctly call the function assigned as a value
;; in `weapons-fn-map`.

((weapon-fn-map :fists) 150)
((weapon-fn-map :fists) 50)

;; Let's add the `:staff` weapon
(def weapon-fn-map
  {:fists
   (fn [health]
     (if (< health 100)
       (- health 10)
       health))
   :staff
   ;; The `:staff` weapon heals
   (partial + 35)})

((weapon-fn-map :fists) 150)
((weapon-fn-map :fists) 50)

((weapon-fn-map :staff) 150)

;; Now lest add the `:sword` as a weapon
(def weapon-fn-map
  {:fists (fn [health]
            (if (< health 100)
              (- health 10)
              health))
   :staff (partial + 35)
   :sword #(- % 100)})

((weapon-fn-map :fists) 150)
((weapon-fn-map :fists) 50)
((weapon-fn-map :staff) 150)
((weapon-fn-map :sword) 150)

;; Lets add the `:cast-iron-saucepan`. We'll mixt it up to reflect the
;; inaccuracy of a saucepan by subtracting a random number of points
;; between 0 and 50 from the default damage of 100.
(def weapon-fn-map
  {:fists (fn [health]
            (if (< health 100)
              (- health 10)
              health))
   :staff (partial + 35)
   :sword #(- % 100)
   :cast-iron-saucepan #( - % 100 (rand-int 50))})

((weapon-fn-map :fists) 150)
((weapon-fn-map :fists) 50)
((weapon-fn-map :staff) 150)
((weapon-fn-map :sword) 150)

((weapon-fn-map :cast-iron-saucepan) 200)
((weapon-fn-map :cast-iron-saucepan) 200)

;; Let's add a new weapon, `:sweet-potato`, that does **no** damage.
(def weapon-fn-map
  {:fists (fn [health]
            (if (< health 100)
              (- health 10)
              health))
   :staff (partial + 35)
   :sword #(- % 100)
   :cast-iron-saucepan #( - % 100 (rand-int 50))
   :sweet-potato identity})

((weapon-fn-map :fists) 150)
((weapon-fn-map :fists) 50)
((weapon-fn-map :staff) 150)
((weapon-fn-map :sword) 150)
((weapon-fn-map :cast-iron-saucepan) 200)
((weapon-fn-map :cast-iron-saucepan) 200)

((weapon-fn-map :sweet-potato) 100)

;; Change our strike function, defined in the `ch-02.ex_03` namepsace,
;; to use the weapons function stored in `weapon-fn-map`.
(def weapon-damage
  {:fists 10.0
   :staff 35.0
   :sword 100.0
   :cast-iron-saucepan 150.0})

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

;; Rewrite the `strike` function to use `weapon-fn-map`.
(defn strike
  "With one argument, strike a `target` with `weapon` (default: `:fists`.

  With two arguments, strike `target` with `weapon`.

  Strike will heal a target that belongs to the gnomes camp."
  ([target] (strike target :fists))
  ([target weapon]
   (let [weapon-fn (weapon weapon-fn-map)]
     (update target :health weapon-fn))))

;; And now we test.
(def enemy {:name "Arnold"
            :health 250})

(strike enemy :sweet-potato)
(strike enemy :sword)
(strike enemy :cast-iron-saucepan)

;; One way to strike with multiple weapons
(update enemy :health (comp (:sword weapon-fn-map)
                            (:cast-iron-saucepan weapon-fn-map)))

;; Let's now create "the ultimate weapon", the `:mighty-weapon`, that
;; combines **all weapons**.
(defn mighty-strike
  "Strike a `target` with **all** weapons!"
  [target]
  (let [weapon-fn (apply comp (vals weapon-fn-map))]
    (update target :health weapon-fn)))

(mighty-strike enemy)
