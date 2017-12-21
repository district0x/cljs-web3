# Cljs Web3

ClojureScript API for [Ethereum](https://ethereum.org/) blockchain [Web3 API](https://github.com/ethereum/wiki/wiki/JavaScript-API)

## See also
* [How to create decentralised apps with Clojurescript re-frame and Ethereum](https://medium.com/@matus.lestan/how-to-create-decentralised-apps-with-clojurescript-re-frame-and-ethereum-81de24d72ff5#.kul24x62l)
* [re-frame-web3-fx](https://github.com/district0x/re-frame-web3-fx)

## Installation
```clojure
;; Add to dependencies
[cljs-web3 "0.19.0-0-9"]
```
```clojure
(ns my.app
  (:require [cljsjs.web3] ; You only need this, if you don't use MetaMask extension or Mist browser
            [cljs-web3.bzz :as web3-bzz]
            [cljs-web3.core :as web3]
            [cljs-web3.db :as web3-db]
            [cljs-web3.eth :as web3-eth]
            [cljs-web3.evm :as web3-evm]
            [cljs-web3.net :as web3-net]
            [cljs-web3.personal :as web3-personal]
            [cljs-web3.settings :as web3-settings]
            [cljs-web3.shh :as web3-shh]))
```

## Usage
So basically, stick with the Web3 API [docs](https://github.com/ethereum/wiki/wiki/JavaScript-API), all methods there have their kebab-cased version in this library. Also, return values and responses in callbacks are automatically kebab-cased and keywordized. Instead of calling method of the web3 object, you pass it as a first argument. For example:
```javascript
web3.eth.accounts
web3.version.api
web3.eth.defaultAccount
web3.isConnected()
web3.net.peerCount
web3.net.getPeerCount(function(error, result){ ... })
```
becomes
```clojure
(web3-eth/accounts web3)
(web3/version-api web3)
(web3-eth/default-account web3)
(web3/connected? web3)
(web3-net/peer-count web3)
(web3-net/peer-count web3 (fn [error result]))
```

Some functions in `cljs-web3.core` don't really need a web3 instance, even though they're called as object methods in Web3 docs. To make our lives easier, in ClojureScript, you can just call it without a web3 instance. For example:
```
(web3/sha3 "1")
=> 0xc89efdaa54c0f20c7adf612882df0950f5a951637e0307cdcb4c672f298b8bc6
(web3/to-hex "A")
=> 0x41
(web3/to-wei 1 :ether)
=> "1000000000000000000"
```
#### Extra functions for a better life
These are a few extra functions, which you won't find in the Web3 API:
```clojure
;; Create web3 instance
(web3/create-web3 "http://localhost:8545/")

;; Deploy new contract
(web3-eth/contract-new web3 abi
  {:data bin
   :gas gas-limit
   :from (first (web3-eth/accounts w3))}
  (fn [err res]))

;; Create contract instance from already deployed contract
(web3-eth/contract-at web3 abi address)

;; This way you can call any contract method
(web3-eth/contract-call ContractInstance :multiply 5)

;; This library contains the special namespace cljs-web3.evm for controlling a testrpc server
;; See https://github.com/ethereumjs/testrpc for more info
(web3-evm/increase-time! web3 [1000] callback)
(web3-evm/mine! web3 callback)
(web3-evm/revert! web3 [0x01] callback)
(web3-evm/snapshot! web3 callback)
```

#### cljs.core.async integration
There's an alternative async namespace for each original namespace, which provides an async alternative instead of the callback approach. For example:
```clojure
(ns test
  (:require
    [cljs-web3.async.eth :as web3-eth-async]
    [cljs.core.async :refer [<! >! chan]]
    [clojure.string :as string])
  (:require-macros [cljs.core.async.macros :refer [go]]))

(go
  (<! (web3-eth-async/accounts web3))
  ;; returns [nil ["0x56727ca3132d00307051a4fa6c6a2c3f07cb3f91"]]

  ;; Alternatively, you can always pass a core.async channel as a first argument. The response will be put onto this channel
  ;; For example, if you pass a channel with a transducer:
  (<! (web3-eth-async/accounts
            (chan 1 (comp (map second)
                          (map (partial map string/upper-case))))
            web3))
  ;; returns ("0X56727CA3132D00307051A4FA6C6A2C3F07CB3F91")

  )
```


#### Code is documentation
Don't hesitate to open lib files or test files of this library to see how to use it. It's not bloated with implementation, so it's easy to read.

Docstrings for the methods and namespaces are adjusted to ClojureScript from the [web3.js documentation](https://github.com/ethereum/wiki/wiki/JavaScript-API#web3netlistening)
## DAPPS using cljs-web3
* [emojillionaire](https://github.com/madvas/emojillionaire)
* [ethlance](https://github.com/madvas/ethlance)
