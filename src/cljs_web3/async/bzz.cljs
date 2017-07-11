(ns cljs-web3.async.bzz
  (:refer-clojure :exclude [get])
  (:require [cljs-web3.bzz :as web3-bzz]
            [cljs-web3.utils :as u]))

(def block-network-read (u/create-async-fn web3-bzz/block-network-read))
(def download (u/create-async-fn web3-bzz/download))
(def get (u/create-async-fn web3-bzz/get))
(def put (u/create-async-fn web3-bzz/put))
(def hive (u/create-async-fn web3-bzz/hive))
(def info (u/create-async-fn web3-bzz/info))
(def modify (u/create-async-fn web3-bzz/modify))
(def retrieve (u/create-async-fn web3-bzz/retrieve))
(def store (u/create-async-fn web3-bzz/store))
(def upload (u/create-async-fn web3-bzz/upload))
(def swap-enabled? (u/create-async-fn web3-bzz/upload))
(def sync-enabled? (u/create-async-fn web3-bzz/upload))








