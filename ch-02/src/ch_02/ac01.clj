(ns ch-02.ac01)

;; Create and use an "in-memory database"
;;

;; Helper functions
(def memory-db (atom {}))
(defn read-db [] @memory-db)
(defn write-db [new-db] (reset! memory-db new-db))

;; Start by experimenting
(read-db)
(assoc (read-db) :foo {:data [] :indexes {}})

(defn create-table
  "Create a table for our in-memory database"
  [table-name]
  (let [db (read-db)
        new-db (assoc db table-name {:data [] :indexes {}})]
    (write-db new-db)))

;; Some tests
(read-db)
(create-table :foo)
(read-db)

(defn drop-table
  "Drop a table from our in-memory database."
  [table-name]
  (let [db (read-db)
        new-db (dissoc db table-name)]
    (write-db new-db)))

(read-db)
(drop-table :foo)
(read-db)


(update-in {:foo {:data [] :indexes {}}} [:foo :data] conj {:bar "baz" :quux :zork})
(update-in {:foo {:data [] :indexes {}}} [:foo :indexes] conj {:bar {"baz" 0}})
(update-in {:foo {:data [] :indexes {:bar {"baz" 0}}}} [:foo :indexes] conj {:bar {"quux" 1}})

(defn insert
  "Insert `record` with key `id-key` in `table` of our in-memory database."
  [table-name record id-key]
  (let [db (read-db)
        new-db (update-in db [table-name :data] conj record)
        new-record-index (- (count (get-in new-db [table-name :data])) 1)]
    (println db)
    (println new-db)
    (println new-record-index)
    (write-db
     (update-in new-db [table-name :indexes id-key] assoc (id-key record) new-record-index))))

(drop-table :foo)
(create-table :foo)
(insert :foo {:bar 0 :baz 1} :bar)
(insert :foo {:bar 3 :baz 4} :bar)
