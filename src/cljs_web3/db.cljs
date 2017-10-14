(ns cljs-web3.db
  "Functions on LevelDB.

  A fast key-value storage library that provides an ordered mapping from string
  keys to string values."
  (:require [cljs-web3.utils :as u :refer [js-apply]]))


(defn get-db
  "Gets leveldb object from web3-instance.

  Parameter:
  web3 - web3 instance"
  [web3]
  (aget web3 "db"))


(defn put-string!
  "This method should be called, when we want to store a string in the local
  leveldb database.

  Parameters:
  web3 - web3 instance
  args:
    db    - The database (string) to store to.
    key   - The name (string) of the store.
    value - The string value to store.

  Returns true if successful, otherwise false."
  [web3 & [db key value cb :as args]]
  (js-apply (get-db web3) "putString" args))


(defn get-string
  "This method should be called, when we want to get string from the local
  leveldb database.

  Parameters:
  db  - The database (string) name to retrieve from.
  key - The name (string) of the store.

  Returns the stored value string."
  [web3 & [db key :as args]]
  (js-apply (get-db web3) "getString" args))


(defn put-hex!
  "This method should be called, when we want to store binary data in HEX form
  in the local leveldb database.

  Parameters:
  db    - The database (string) to store to.
  key   - The name (string) of the store.
  value - The HEX string to store.

  Returns true if successful, otherwise false."
  [web3 & [db key value :as args]]
  (js-apply (get-db web3) "putHex" args))


(defn get-hex
  "This method should be called, when we want to get a binary data in HEX form
  from the local leveldb database.

  Parameters:
  db  - The database (string) to store to.
  key - The name (string) of the store.

  Returns the stored HEX value."
  [web3 & [db key :as args]]
  (js-apply (get-db web3) "getHex" args))
