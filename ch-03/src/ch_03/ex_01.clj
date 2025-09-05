(ns ch-03.ex-01)

;; Define a booking
(def booking
  [1425
   "Bob Smith"
   "Allergic to unsalted peanuts only"
   [[48.9615 2.4372], [37.742 -25.9676]]
   [[37.742, -25.6976], [48.9615, 2.4372]]])

;; We start be experimenting with destructing this information
(let [[id customer-name sensitive-info
       flight1 flight2 flight3] booking]
  (println id customer-name flight1 flight2 flight3))

;; We can also test "extra" flights. Notice that destructuring **ignores**
;; the "extra" flight.
(let [big-booking (conj booking [[37.742 -25.6976], [51.1537, 0.1821]]
                                [[51.1537, 0.1821] [48.9615, 2.4372]])
      [id customer-name sensitive-info flight1 flight2 flight3] big-booking]
  (println id customer-name flight1 flight2 flight3))

;; We can further clarify our intent. We care about neither
;;
;; - The Fly Vector internal `id`
;; - The sensitive information
;;
;; How might we express this idea in our desctructuring?
(let [[_ customer-name _ flight1 flight2 flight3] booking]
  (println customer-name flight1 flight2 flight3))

;; Printing out an array for each flight does not communicate well.
;; Let's use the ampersand ('&') character to ignore the details of
;; each flight (for now) and just capture **all** the flights.
(let [[_ customer-name _ & flights] booking]
  (println (str customer-name
                " booked "
                (count flights)
                " flights.")))

;; Destructuring can not only be applied at the "top level" of structured
;; data but can capture nested data. The following function illustrates
;; this behavior.
(defn print-flight [flight]
  (let [[[lat1 lon1] [lat2 lon2]] flight]
    (println (str "Flying from latitude, "
                  lat1
                  ", and longitude, "
                  lon1
                  ". Flying to latitude, "
                  lat2
                  ", and longitude, "
                  lon2
                  "."))))
(first (rest (rest (rest booking))))
(print-flight (first (rest (rest (rest booking)))))

;; Hmm. I got something wrong. Lets try "less."
(print-flight [[48.9615 2.4372] [37.742, -25.6976]])

;; Rewrite our `print-flight` function by decomposing the input data in
;; multiple steps
(defn printl-flight [flight]
  (let [[departure arrival] flight
        [lat1 lon1] departure
        [lat2 lon2] arrival]
    (println (str "Flying from latitude, "
                  lat1
                  ", longitude, "
                  lon1
                  " to latitude, "
                  lat2
                  ", longitude, "
                  lon2
                  "."))))
(print-flight [[48.9615 2.4372] [37.742 -25.6976]])

;; Finally, let's write the function, `print-booking`, by combining what
;; we have written so far.
(defn print-booking [booking]
  (let [[_ customer-name _ & flights] booking]
    (println (str customer-name " booked " (count flights) " flights."))
    (let [[flight1 flight2 flight3] flights]
      (when flight1
        (print-flight flight1))
      (when flight2
        (print-flight flight2))
      (when flight3
        (print-flight flight3)))))
(print-booking booking)
