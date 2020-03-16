(ns pet-owners.core-test
  (:require [expectations :refer :all]
            [pet-owners.core :refer :all]
            [datomic.api :as d]))

(defn create-empty-in-memory-db []
  (let [uri "datomic:mem://pet-owners-test-db"]
    (d/delete-database uri)
    (d/create-database uri)
    (let [conn (d/connect uri)
          schema (load-file "resources/datomic/schema.edn")]
      (d/transact conn schema)
      conn)))

(expect #{["Jhon"]}
  (with-redefs [conn (create-empty-in-memory-db)]
    (do 
      (add-pet-owner "Jhon")
      (find-all-pet-owners))))
