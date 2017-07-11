(ns cljs-web3.async.net
  (:require
    [cljs-web3.net :as web3-net]
    [cljs-web3.utils :as u]))

(def listening? (u/create-async-fn web3-net/listening?))
(def peer-count (u/create-async-fn web3-net/peer-count))