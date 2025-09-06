(ns ch-03.ex-02)

;; Parsing MapJet Data with Associative Destructuring
;;

(def mapjet-booking
  {
   :id 8773
   :customer-name "Alice Smith"
   :catering-notes "Vegetarian on Sudays"
   :flights [{:from {:lat 48.9615
                     :lon 2.4372
                     :name "Paris Le Berget Airport"},
              :to {:lat 37.742
                   :lon -25.6976
                   :name "Ponta Delgada Airport"}},
              {:from {:lat 37.742
                      :lon -25.6976
                      :name "Ponta Delgada Airport"},
               :to {:lat 49.9615
                    :lon 2.4372
                    :name "Paris Le Bourget Airport"}}]})

;; Let's experiment with associative destructuring of this information
(let [{:keys [customer-name flights]} mapjet-booking]
  (println (str customer-name
                " booked "
                (count flights)
                " flights.")))

;; After this experiment, we can now write a function performing similar
;; functions.
(defn print-mapjet-flight [flight]
  (let [{:keys [from to]} flight
        {lat1 :lat lon1 :lon} from
        {lat2 :lat lon2 :lon} to]
    (println (str "Flying from latitude, "
                  lat1
                  ", and longitude, "
                  lon1
                  ". "
                  "Flying to latitude, "
                  lat2
                  ". and longitude, "
                  lon2
                  "."))))

;; And a test.
(print-mapjet-flight (first (:flights mapjet-booking)))

;; Let's rewrite `print-mapjet-flight` but nest our associative
;; destructuring techniques.
(defn print-mapjet-flight [flight]
  (let [{{lat1 :lat lon1 :lon} :from,
         {lat2 :lat lon2 :lon} :to} flight]
    (println (str "Flying from latitude, "
                  lat1
                  ", and longitude, "
                  lon1
                  ". "
                  "Flying to latitude, "
                  lat2
                  ", and longitude, "
                  lon2
                  "."))))
(print-mapjet-flight (first (:flights mapjet-booking)))

;; And now we write our final function to print the booking summary to
;; the console
(defn print-mapjet-booking [booking]
  (let [{:keys [customer-name flights]} booking]
    (println (str customer-name
                  " booked "
                  (count flights)
                  " flights."))
    (let [[flight1 flight2 flight3] flights]
      (when flight1 (print-mapjet-flight flight1))
      (when flight2 (print-mapjet-flight flight2))
      (when flight3 (print-mapjet-flight flight3)))))
(print-mapjet-booking mapjet-booking)
