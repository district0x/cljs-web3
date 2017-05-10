(ns cljs-web3.core
  (:require [cljs-web3.utils :as u :refer [js-apply js-prototype-apply]]))

(def version-api (u/prop-or-clb-fn "version" "api"))
(def version-node (u/prop-or-clb-fn "version" "node"))
(def version-network (u/prop-or-clb-fn "version" "network"))
(def version-ethereum (u/prop-or-clb-fn "version" "ethereum"))
(def version-whisper (u/prop-or-clb-fn "version" "whisper"))

(defn connected? [web3]
  (js-apply web3 "isConnected"))

(defn sha3 [string & [options]]
  (js-prototype-apply js/Web3 "sha3" [string options]))

(defn to-hex [any]
  (js-prototype-apply js/Web3 "toHex" [any]))

(defn to-ascii [hex-string]
  (js-prototype-apply js/Web3 "toAscii" [hex-string]))

(defn from-ascii [string & [padding]]
  (js-prototype-apply js/Web3 "fromAscii" [string padding]))

(defn to-decimal [hex-string]
  (js-prototype-apply js/Web3 "toDecimal" [hex-string]))

(defn from-decimal [number]
  (js-prototype-apply js/Web3 "fromDecimal" [number]))

(defn from-wei [number unit]
  (js-prototype-apply js/Web3 "fromWei" [number (name unit)]))

(defn to-wei [number unit]
  (js-prototype-apply js/Web3 "toWei" [number (name unit)]))

(defn to-big-number [number-or-hex-string]
  (js-prototype-apply js/Web3 "toBigNumber" [number-or-hex-string]))

(defn pad-left [string chars & [sign]]
  (js-prototype-apply js/Web3 "padLeft" [string chars sign]))

(defn pad-right [string chars & [sign]]
  (js-prototype-apply js/Web3 "padRight" [string chars sign]))

(defn address? [address]
  (js-prototype-apply js/Web3 "isAddress" [address]))

(defn reset [web3]
  (js-apply web3 "reset"))

(defn set-provider [web3 provider]
  (js-apply web3 "setProvider" [provider]))

(defn current-provider [web3]
  (aget web3 "currentProvider"))

;; Providers

(defn http-provider [Web3 uri]
  (let [constructor (aget Web3 "providers" "HttpProvider")]
    (constructor. uri)))

(defn ipc-provider [Web3 uri]
  (let [constructor (aget Web3 "providers" "IpcProvider")]
    (constructor. uri)))

(defn create-web3 [url]
  (new js/Web3 (http-provider js/Web3 url)))