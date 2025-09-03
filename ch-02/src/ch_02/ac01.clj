(ns ch-02.ac01)

;; Creating a simple, in-memory database
;; Chapter 02 Activity 01
;;

;; Helper functions to
;;
;; - Define
;; - Read from
;; - Write to
;;
;; a simple, in-memory database.

(def memory-db (atom {}))

(defn read-db []
  @memory-db)

(defn write-db
  [new-db]
  (reset! memory-db new-db))

(defn create-table
  "Create an in-memory table named `name`"
  [name]
  (let [db (read-db)
        new-data {:data []
                  :indexes {}}]
    (write-db (assoc db name new-data))))

(read-db)
(create-table :foo)

(defn drop-table
  "Drop the table named `name` from our in-memory database."
  [name]
  (let [db (read-db)]
       (write-db (dissoc db name))))

(read-db)
(drop-table :foo)

;; First, lets try some experiments.
(let [db (read-db)]
  (assoc db
         :golfer
         {:data [{:name "Scheffler"
                  :season 2025
                  :majors 2}]
          :indexes {:name {"Scheffler" 0}}}))

(defn insert
  "Insert `record` in `table` identified by `id-key`.

  - `record` is a hash map containing information to be inserted
  - `table` is the name of an existing in-memory \"table\"
  - `id-key` is a key in the `record` that contains data used as a
     unique index."
  [table record id-key]
  (let [db (read-db)
        db-table (get db table)]
    (println db)
    (println db-table)
    (if (nil? db-table)
      (write-db (assoc db table {:data [record]
                       :indexes {id-key {(id-key record) 0}}})))))
(drop-table :golfer)
(insert :golfer {:name "Scheffler" :season 2025 :majors 2} :name)
(read-db)
