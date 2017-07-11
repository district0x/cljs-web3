(ns cljs-web3.async.shh
  (:refer-clojure :exclude [filter])
  (:require
    [cljs-web3.shh :as web3-shh]
    [cljs-web3.utils :as u]))

(def post! (u/create-async-fn web3-shh/post!))
(def new-identity (u/create-async-fn web3-shh/new-identity))
(def has-identity? (u/create-async-fn web3-shh/has-identity?))
(def new-group (u/create-async-fn web3-shh/new-group))
(def add-to-group (u/create-async-fn web3-shh/add-to-group))