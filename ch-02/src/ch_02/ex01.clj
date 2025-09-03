(ns ch-02.ex01
  (:require [clojure.string :as str]))

;; The Obfuscation Machine
;;
(clojure.repl/doc str/replace)

;; Experiment with `str/replace`
;;

;; Replaces all "vword characters" with the character `\!`
(str/replace "Hello World" #"\w" "!")

;; Replace all "word characters" with the result of a function
(str/replace "Hello World" #"\w" (fn [l] (do (println l)
                                             "!")))

;; Let's see how to convert a character to a number. We can use the
;; `int` function as follows.
(int \a)

;; Our replacement function takes an argument of type string. This type
;; allows us to split the resulting string into characters.
(clojure.repl/doc char-array)

;; We should be able to combine our previous steps into code that
;; transforms a character in a string into our numeric code.
(-> "a"
    char-array
    first
    int
    (Math/pow 2))

;; Based on this work, we can now write a function, `encode-letter` that
;; encodse a single letter.
(defn encode-letter
  "Encode a single letter using our super-secret cipher"
  [l]
  (-> l
      char-array
      first
      int
      (Math/pow 2)
      int
      str))
(encode-letter "a")

;; Let's now write our `encode` function that uses `str/replace` and
;; `encode-letter`.
(defn encode
  "Encodes the text, `s` using our super secret cipher."
  [s]
  (str/replace s #"\w" encode-letter))
(encode "Hello World")

;; It works, but our current implementation runs together all our numbers.
;; It looks like an encodded message, but I am uncertain how I might
;; decode an encoded message.
;;
;; We'll change our implementation of `encode` to put a space character
;; between each encoded character.
(defn encode
  "Encodes the text, `s` using our super secret cipher."
  [s]
  (str/replace s #"\w" (fn [c]
                         (str (encode-letter c)
                              \space))))
(encode "Hello World")

;; Hmmm. We must change `encode-letter` to include adding a constante:
;; the number of words in the message.`

(defn encode-letter
  "Encode a single letter using our super-secret cipher"
  [l x]
  (let [code (-> l
                 char-array
                 first
                 int
                 (+ x)
                 (Math/pow 2)
                 int)]
    (str "#" code)))
(encode-letter "H" 11)

;; Test the complete encoding - but it throws an exception
#_(encode "Hello World")

;; Our code currently fails because `encode` calls `encode-letter` but only
;; passes a **single argument to `encode-letter`. We changed the signature
;; of `encoude-letter` to take **two** arguments.
(defn encode
  "Encodes the text, `s` using our super secret cipher."
  [s]
  (let [word-count (count (str/split s #" "))]
        (str/replace s #"\w" (fn [s] (encode-letter s word-count)))))
(encode "Super secret")
(encode "Super secret message")

;; And now, for something completely "decoding"
;;

;; Decode a single letter
(defn decode-letter
  [x y]
  (let [number (Integer/parseInt (subs x 1))
        letter (-> number
                   Math/sqrt
                   (- y)
                   char)]
    (str letter)))

;; Now that we can decode a single letter, let's decode the entire message
(defn decode [s]
  (let [number-of-words (count (str/split s #" "))]
    (str/replace s #"\#\d+"
                 (fn [s]
                   (decode-letter s number-of-words)))))

;; And now for some tests...
(encode "If you want to keep a secret, you must also hide it from yourself.")
(decode *1)
