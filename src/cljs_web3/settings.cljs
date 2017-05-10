(ns cljs-web3.settings
  (:require [cljs-web3.utils :as u :refer [js-val js-apply]]))

(defn settings [web3]
  (aget web3 "settings"))

(defn default-account [web3]
  (aget web3 "settings" "defaultAccount"))

(defn set-default-account! [web3 hex-str]
  (aset (settings web3) "defaultAccount" hex-str))

(defn default-block [web3]
  (aget web3 "settings" "defaultBlock"))

(defn set-default-block! [web3 block]
  (aset (settings web3) "defaultBlock" block))

