(ns cljs-web3.async.personal
  (:require
    [cljs-web3.personal :as web3-personal]
    [cljs-web3.utils :as u]))

(def list-accounts (u/create-async-fn web3-personal/list-accounts))
(def lock-account (u/create-async-fn web3-personal/lock-account))
(def new-account (u/create-async-fn web3-personal/new-account))
(def unlock-account (u/create-async-fn web3-personal/unlock-account))
(def ec-recover (u/create-async-fn web3-personal/ec-recover))
(def import-raw-key (u/create-async-fn web3-personal/import-raw-key))
(def send-transaction (u/create-async-fn web3-personal/send-transaction))
(def sign (u/create-async-fn web3-personal/sign))



