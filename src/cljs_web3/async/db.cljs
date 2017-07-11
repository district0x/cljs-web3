(ns cljs-web3.async.db
  (:require
    [cljs-web3.db :as web3-db]
    [cljs-web3.utils :as u]))

(def put-string! (u/create-async-fn web3-db/put-string!))
(def get-string (u/create-async-fn web3-db/put-string!))
(def put-hex! (u/create-async-fn web3-db/put-string!))
(def get-hex (u/create-async-fn web3-db/put-string!))