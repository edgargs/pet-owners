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

;; Adding one owner should allow us to find that owner
(expect #{["Jhon"]}
  (with-redefs [conn (create-empty-in-memory-db)]
    (do 
      (add-pet-owner "Jhon")
      (find-all-pet-owners))))

;; Adding multiple owners should allow us to find those owners
(expect #{["Jhon"] ["Paul"] ["George"]}
  (with-redefs [conn (create-empty-in-memory-db)]
    (do 
      (add-pet-owner "Jhon")
      (add-pet-owner "Paul")
      (add-pet-owner "George")
      (find-all-pet-owners))))

;; Adding one owner with one pet
(expect #{["Salt"]}
  (with-redefs [conn (create-empty-in-memory-db)]
    (do
      (add-pet-owner "Jhon")
      (add-pet "Salt" "Jhon")
      (find-pets-for-owner "Jhon"))))

;; Adding one owner with multiple pets
(expect #{["Pepper"] ["Jet"]}
  (with-redefs [conn (create-empty-in-memory-db)]
    (do
      (add-pet-owner "Paul")
      (add-pet "Pepper" "Paul")
      (add-pet "Jet" "Paul")
      (find-pets-for-owner "Paul"))))      