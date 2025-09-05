(ns ch-03.code)

;; Code from chapter 03 of "The Clojure Workshop"
;;

(def some-coords [48.9615 2.4372])
(first some-coords)

(defn print-coords [coords]
  (let [lat (first coords)
        lon (last coords)]
    (println (str "Latitude: - " lat " Longitude: - " lon))))

(print-coords [48.9615 2.4372])

;; The previous example **does not** use destructing. The following
;; implementatoin **uses** destructing (in a basic way).
(defn print-coords [coords]
  (let [[lat lon] coords]
    (println (str "Latitude: " lat " " "Longitude: " lon))))
(print-coords [48.9615 2.4372])

;; Some other examples
(let [[a b c] [1 2 3]]
  (println (str "a=" a " b=" b " c=" c)))

(let [[a b c] '(1 2 3)]
  (println (str "a=" a " b=" b " c=" c)))

(def an-airport-location {:lat 48.9615
                          :lon 2.4372
                          :code "LFPB"
                          :name "Paris Le Bourget Airport"})
(defn print-coords [airport]
  (let [lat (:lat airport)
        lon (:lon airport)
        name (:name airport)]
    (println (str name " is located at latitude, " lat
                  ", and longitude, " lon "."))))
(print-coords an-airport-location)

;; Hmm.. I missed some "simpler" destructuring.
(defn print-coords [airport]
  (let [{lat :lat lon :lon airport-name :name} airport]
    (println (str airport-name
                  " is located at latitude, "
                  lat
                  ", and longitude, "
                  lon
                  "."))))
(print-coords an-airport-location)

;; But, when keys and values all have the same name, we can shorten our
;; bindings even farther.
(defn print-coords [airport]
  (let [{:keys [lat lon name]} airport]
    (println (str name
                  " is located at latitude, "
                  lat
                  ", and longitude, "
                  lon
                  "."))))
(print-coords an-airport-location)
