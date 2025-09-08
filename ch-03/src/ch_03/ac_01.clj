(ns ch-03.ac-01
  (:require clojure.math)
  (:import java.lang.Math))

;; Building a Distance and Cost Calculator
;;

;; Define our speed constants
(def walking-speed 5) ;; 5 km/hr
(def driving-speed 70) ;; 70 km/hr

;; Define our two locations
(def paris {:lat 48.856483 :lon 2.352413})
(def bordeaux {:lat 44.834999 :lon -0.575490})

;; Here is the distance function from the book source code. This code
;; seems to differ from the book in that the book formula takes the
;; cosine of `lat1` but also **does not** square this value.
;;
;; The source code has a
(defn distance
  "Returns a rough estimate of the distance between two coordinate points, in kilometers. Works better with smaller distance"
  [{lat1 :lat lon1 :lon} {lat2 :lat lon2 :lon}]
  (let [deglen 110.25
        x (- lat2 lat1)
        y (* (Math/cos lat2) (- lon2 lon1))]
    (* deglen (Math/sqrt (+ (* y y) (* x x))))))

(distance paris bordeaux)

;; My definition of the distance function.
(defn my-distance [{lat1 :lat lon1 :lon}
                   {lat2 :lat lon2 :lon}]
  (let [square (fn [x] (* x x))
        ns-component (square (- lat2 lat1))
        ew-component (* (Math/cos (Math/toRadians (/ (+ lat1 lat2) 2)))
                        (square (- lon2 lon1)))]
  (* 110.25 (Math/sqrt (+ ns-component ew-component)))))

(my-distance paris bordeaux)

;; The approximation from Google AI.
(def ^:private earth-radius-km 6371.0) ; Earth's radius in kilometers

(defn euclidean-distance-approx
  "Calculates the approximate Euclidean distance between two
  [lat lon] coordinate pairs for short distances."
  [[lat1 lon1] [lat2 lon2]]
  (let [lat1-rad (math/to-radians lat1)
        lon1-rad (math/to-radians lon1)
        lat2-rad (math/to-radians lat2)
        lon2-rad (math/to-radians lon2)

        delta-lat (- lat2-rad lat1-rad)
        delta-lon (- lon2-rad lon1-rad)

        avg-lat-rad (/ (+ lat1-rad lat2-rad) 2)

        x (* delta-lon (math/cos avg-lat-rad))
        y delta-lat

        distance (* earth-radius-km (math/sqrt (+ (* x x) (* y y))))]
    distance))

;; Example usage:
(euclidean-distance-approx [38.8951 -77.0364] [38.8976 -77.0365])
;; => 0.28189... (approx 282 meters)

(euclidean-distance-approx [(:lat paris) (:lon paris)]
                           [(:lat bordeaux) (:lon bordeaux)])
