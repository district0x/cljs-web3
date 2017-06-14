# Cljs Web3

Clojurescript API for [Ethereum](https://ethereum.org/) blockchain [Web3 API](https://github.com/ethereum/wiki/wiki/JavaScript-API)

## See also
* [How to create decentralised apps with Clojurescript re-frame and Ethereum](https://medium.com/@matus.lestan/how-to-create-decentralised-apps-with-clojurescript-re-frame-and-ethereum-81de24d72ff5#.kul24x62l)
* [re-frame-web3-fx](https://medium.com/@matus.lestan/how-to-create-decentralised-apps-with-clojurescript-re-frame-and-ethereum-81de24d72ff5#.kul24x62l)

## Installation
```clojure
; Add to dependencies
[cljs-web3 "0.19.0-0-1"]
```
```clojure
(ns my.app
  (:require [cljsjs.web3] ; You only need this, if you don't use MetaMask extension or Mist browser
            [cljs-web3.core :as web3]
            [cljs-web3.eth :as web3-eth]
            [cljs-web3.db :as web3-db]
            [cljs-web3.personal :as web3-personal]
            [cljs-web3.shh :as web3-shh]
            [cljs-web3.net :as web3-net]
            [cljs-web3.bzz :as web3-bzz]
            [cljs-web3.settings :as web3-settings]))
```
r
## Usage
So basically, stick with Web3 API [docs](https://github.com/ethereum/wiki/wiki/JavaScript-API), all methods there have their kebab-case version in this library. Also, return values and responses in callbacks are automatically kebab-cased and keywordized. Instead of calling method of web3 object, you pass it as a first argument. For example:
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

Some functions in `cljs-web3.core` don't really need web3 instance, even though they're called as object methods in Web3 docs. To make our lives easier, in clojurescript, you can just call it without web3 instance. For example:
```
(web3/sha3 "1")
=> 0xc89efdaa54c0f20c7adf612882df0950f5a951637e0307cdcb4c672f298b8bc6
(web3/to-hex "A")
=> 0x41
(web3/to-wei 1 :ether)
=> "1000000000000000000"
```
#### Extra functions for better life
Thses are few extra functions, which you won't find in Web3 API
```clojure
; Create web3 instance
(web3/create-web3 "http://localhost:8545/")

; Deploy new contract
(web3-eth/contract-new web3 abi
  {:data bin
   :gas gas-limit
   :from (first (web3-eth/accounts w3))}
  (fn [err res]))
  
; Create contract instance from already deployed contract
(web3-eth/contract-at web3 abi address)

; This way you can call any contract method
(web3-eth/contract-call ContractInstance :multiply 5)
```

#### Code is documentation
Don't hesitate to open lib files or test file of this library to see how to use it. It's not bloated with implementation, so it's easy to read.

## DAPPS using cljs-web3
* [emojillionaire](https://github.com/madvas/emojillionaire)
* [ethlance](https://github.com/madvas/ethlance)
