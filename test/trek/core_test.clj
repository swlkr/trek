(ns trek.core-test
  (:require [clojure.test :refer :all]
            [clojure.java.jdbc :as jdbc]
            [trek.core :refer :all]))

(defn exec [db sql]
  (jdbc/with-db-connection [conn db]
    (with-open [s (.createStatement (jdbc/db-connection conn))]
      (.addBatch s sql)
      (seq (.executeBatch s)))))

(let [conn {:connection-uri "jdbc:postgresql://localhost:5432/postgres"}
      _ (exec conn "drop database if exists trek_test")
      _ (exec conn "create database trek_test")
      _ (exec conn "drop table if exists schema_migrations")
      db {:connection-uri "jdbc:postgresql://localhost:5432/trek_test"}]

  (deftest contents-test
    (testing "create a migration that creates a table with columns"
      (is (= "-- up\ncreate table users (\nid serial primary key,\nemail text,\npassword text,\ncreated_at timestamp without time zone default (now() at time zone 'utc')\n)\n\n--down\ndrop table users" (contents "create-users" ["email:text" "password:text"])))))

  (deftest pending-test
    (testing "pending migrations"
      (is (= #{} (pending db))))))
