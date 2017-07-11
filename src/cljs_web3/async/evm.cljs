;;   Functions in this namespace are possible to use only with testrpc
(ns cljs-web3.async.evm
  (:require
    [cljs-web3.evm :as web3-evm]
    [cljs-web3.utils :as u]))

(def increase-time! (u/create-async-fn web3-evm/increase-time!))
(def mine! (u/create-async-fn web3-evm/mine!))
(def revert! (u/create-async-fn web3-evm/revert!))
(def snapshot! (u/create-async-fn web3-evm/snapshot!))