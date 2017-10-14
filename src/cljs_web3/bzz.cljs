(ns cljs-web3.bzz
  "Interface to the web3-bzz that allows you to interact with swarm
  decentralized file store"
  (:refer-clojure :exclude [get])
  (:require [cljs-web3.utils :as u :refer [js-apply]]))

(defn get-bzz [web3]
  (aget web3 "bzz"))

(defn block-network-read [web3 & args]
  (js-apply (get-bzz web3) "blockNetworkRead" args))

(defn download [web3 & args]
  (js-apply (get-bzz web3) "download" args))

(defn get [web3 & args]
  (js-apply (get-bzz web3) "get" args))

(defn put [web3 & args]
  (js-apply (get-bzz web3) "put" args))

(def hive (u/prop-or-clb-fn "bzz" "hive"))
(def info (u/prop-or-clb-fn "bzz" "info"))

(defn modify [web3 & args]
  (js-apply (get-bzz web3) "modify" args))

(defn retrieve [web3 & args]
  (js-apply (get-bzz web3) "retrieve" args))

(defn store [web3 & args]
  (js-apply (get-bzz web3) "store" args))

(defn upload [web3 & args]
  (js-apply (get-bzz web3) "upload" args))

(defn swap-enabled? [web3 & args]
  (js-apply (get-bzz web3) "swapEnabled" args))

(defn sync-enabled? [web3 & args]
  (js-apply (get-bzz web3) "syncEnabled" args))
