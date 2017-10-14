(ns cljs-web3.eth
  "Contains the ethereum blockchain related methods."
  (:refer-clojure :exclude [filter])
  (:require [cljs-web3.utils :as u :refer [js-val js-apply]]))


(defn eth
  "Gets eth object from web3-instance.

  Parameter:
  web3 - web3 instance"
  [web3]
  (aget web3 "eth"))


(defn get-compile
  "Gets compile object from web3-instance.

  Parameter:
  web3 - web3 instance"
  [web3]
  (aget (eth web3) "compile"))


(defn default-account
  "Gets the default address that is used for the following methods (optionally
  you can overwrite it by specifying the :from key in their options map):

  - `send-transaction!`
  - `call!`

  Parameters:
  web3 - web3 instance

  Returns the default address HEX string.

  Example:
  user> `(default-account web3-instance)`
  \"0x85d85715218895ae964a750d9a92f13a8951de3d\""
  [web3]
  (aget web3 "eth" "defaultAccount"))


(defn set-default-account!
  "Sets the default address that is used for the following methods (optionally
  you can overwrite it by specifying the :from key in their options map):

  - `send-transaction!`
  - `call!`

  Parameters:
  web3    - web3 instance
  hex-str - Any 20 bytes address you own, or where you have the private key for


  Returns a 20 bytes HEX string representing the currently set address.

  Example:
  user> (set-default-account! web3-instance
                              \"0x85d85715218895ae964a750d9a92f13a8951de3d\")
  \"0x85d85715218895ae964a750d9a92f13a8951de3d\""
  [web3 hex-str]
  (aset (eth web3) "defaultAccount" hex-str))


(defn default-block
  "This default block is used for the following methods (optionally you can
  override it by passing the default-block parameter):

  - `get-balance`
  - `get-code`
  - `get-transactionCount`
  - `get-storageAt`
  - `call`
  - `contract-call`
  - `estimate-gas`

  Parameters:
  web3 - web3 instance

  Returns one of:
  - a block number
  - \"earliest\", the genisis block
  - \"latest\", the latest block (current head of the blockchain)
  - \"pending\", the currently mined block (including pending transactions)

  Example:
  user> `(default-block web3-instance)`
  \"latest\""
  [web3]
  (aget web3 "eth" "defaultBlock"))


(defn set-default-block!
  "Sets default block that is used for the following methods (optionally you can
  override it by passing the default-block parameter):

  - `get-balance`
  - `get-code`
  - `get-transactionCount`
  - `get-storageAt`
  - `call`
  - `contract-call`
  - `estimate-gas`

  Parameters:
  web3  - web3 instance
  block - one of:
            - a block number
            - \"earliest\", the genisis block
            - \"latest\", the latest block (current head of the blockchain)
            - \"pending\", the currently mined block (including pending
              transactions)

  Example:
  user> `(set-default-block! web3-instance \"earliest\")`
  \"earliest\""
  [web3 block]
  (aset (eth web3) "defaultBlock" block))


(def syncing
  "This property is read only and returns the either a sync object, when the
  node is syncing or false.

  Parameters:
  web3        - web3 instance
  callback-fn - callback with two parameters, error and result

  Returns a sync object as follows, when the node is currently syncing or false:
  - startingBlock: The block number where the sync started.
  - currentBlock:  The block number where at which block the node currently
                   synced to already.
  - highestBlock:  The estimated block number to sync to.

  Example:
  user> `(syncing web3-instance (fn [err res] (when-not err (println res))))`
  nil
  user> `false`"
  (u/prop-or-clb-fn "eth" "syncing"))


(defn syncing?
  "This convenience function calls the callback everytime a sync starts, updates
  and stops.

  Parameters:
  web3 - web3 instance

  Returns an isSyncing object with the following methods:
  - `.addCallback`  Adds another callback, which will be called when the node
                    starts or stops syncing.
  - `.stopWatching` Stops the syncing callbacks

  Callback return value:

    The callback will be fired with true when the syncing starts and with false
    when it stopped.

    While syncing it will return the syncing object:
    - startingBlock: The block number where the sync started.
    - currentBlock:  The block number where at which block the node currently
                     synced to already.
    - highestBlock:  The estimated block number to sync to

  Example:
  user> `(.addCallback (web3-eth/syncing? web3-instance) (fn [err res] ...))`
  #object[s [object Object]]"
  [web3 & args]
  (js-apply (eth web3) "isSyncing" args))


(def coinbase
  "This property is read only and returns the coinbase address where the mining
  rewards go to.

  Parameters:
  web3 - web3 instance

  Returns a string representing the coinbase address of the client.

  Example:
  user> `(coinbase web3-instance)`
  \"0x85d85715218895ae964a750d9a92f13a8951de3d\""
  (u/prop-or-clb-fn "eth" "coinbase"))


(def mining?
  "This property is read only and says whether the node is mining or not.

  Parameters:
  web3 - web3 instance

  Returns a boolean: true if the client is mining, otherwise false.

  Example:
  `(mining? web3-instance (fn [err res] (when-not err (println res))))`
  nil
  user> `false`"
  (u/prop-or-clb-fn "eth" "mining"))


(def hashrate
  "This property is read only and returns the number of hashes per second that
  the node is mining with.

  Parameters:
  web3 - web3 instance

  Returns a number representing the hashes per second.

  user> `(hashrate web3-instance (fn [err res] (when-not err (println res))))`
  nil
  user> 0
  "
  (u/prop-or-clb-fn "eth" "hashrate"))


(def gas-price
  "This property is read only and returns the current gas price. The gas price
  is determined by the x latest blocks median gas price.

  Parameters:
  web3        - web3 instance
  callback-fn - callback with two parameters, error and result

  Returns a BigNumber instance of the current gas price in wei.

  Example:
  user> `(gas-price web3-instance (fn [err res] (when-not err (println res))))`
  nil
  user> #object[e 90000000000]"
  (u/prop-or-clb-fn "eth" "gasPrice"))


(def accounts
  "This property is read only and returns a list of accounts the node controls.

  Parameters:
  web3        - web3 instance
  callback-fn - callback with two parameters, error and result

  Returns an array of addresses controlled by client.

  Example:
  user> `(accounts web3-instance (fn [err res] (when-not err (println res))))`
  nil
  user> `[0x85d85715218895ae964a750d9a92f13a8951de3d]`"
  (u/prop-or-clb-fn "eth" "accounts"))


(def block-number
  "This property is read only and returns the current block number.

  Parameters:
  web3        - web3 instance
  callback-fn - callback with two parameters, error and result

  Returns the number of the most recent block.

  Example:
  `(block-number web3-instance
                 (fn [err res] (when-not err (println res))))`
  nil
  user> `1783426`"
  (u/prop-or-clb-fn "eth" "blockNumber"))


(defn register
  "(Not Implemented yet) Registers the given address to be included in
  `accounts`. This allows non-private-key owned accounts to be associated
  as an owned account (e.g., contract wallets).

  Parameters:
  web3        - web3 instance
  address     - string representing the address
  callback-fn - callback with two parameters, error and result."
  [web3 address]
  (js-apply (eth web3) "register" [address]))


(defn unregister
  "(Not Implemented yet) Unregisters a given address.

  Parameters:
  web3        - web3 instance
  address     - string representing the address
  callback-fn - callback with two parameters, error and result."
  [web3 address]
  (js-apply (eth web3) "unRegister" [address]))


(defn get-balance
  "Get the balance of an address at a given block.

  Parameters:
  web3          - web3 instance
  address       - The address to get the balance of.
  default-block - If you pass this parameter it will not use the default block
                  set with set-default-block.
  callback-fn   - callback with two parameters, error and result

  Returns a BigNumber instance of the current balance for the given address in
  wei.

  Example:
  user> `(get-balance web3-instance
                      \"0x85d85715218895ae964a750d9a92f13a8951de3d\"
                      \"latest\"
                      (fn [err res] (when-not err (println res))))`
  nil
  user> #object[e 1729597111000000000]"
  [web3 & [address default-block :as args]]
  (js-apply (eth web3) "getBalance" args))


(defn get-storage-at
  "Get the storage at a specific position of an address.

  Parameters:
  web3          - web3 instance
  address       - The address to get the storage from.
  position      - The index position of the storage.
  default-block - If you pass this parameter it will not use the default block
                  set with web3.eth.defaultBlock.
  callback-fn   - callback with two parameters, error and result

  Returns the value in storage at the given position.

  Example:
  user> `(get-storage-at web3-instance
                         \"0x85d85715218895ae964a750d9a92f13a8951de3d\"
                         0
                         \"latest\"
                         (fn [err res] (when-not err (println res))))`
  nil
  user> \"0x0000000000000000000000000000000000000000000000000000000000000000\" "
  [web3 & [address position default-block :as args]]
  (js-apply (eth web3) "getStorageAt" args))


(defn get-code
  "Get the code at a specific address.

  Parameters:
  web3          - web3 instance
  address       - The address to get the code from.
  default-block - If you pass this parameter it will not use the default block set
                  with `get-default-block!`.
  callback-fn   - callback with two parameters, error and result

  Returns the data at given address HEX string.

  Example:
  user> (get-code web3-instance
                  \"0x85d85715218895ae964a750d9a92f13a8951de3d\
                  0
                  \"latest\"
                  (fn [err res] (when-not err (println res))))
  nil
  user> `0x`"
  [web3 & [address default-block :as args]]
  (js-apply (eth web3) "getCode" args))


(defn get-block
  "Returns a block matching the block number or block hash.

  Parameters:
  web3                        - web3 instance

  block-hash-or-number        - The block number or hash. Or the string
                                \"earliest\", \"latest\" or \"pending\"
                                as in the default block parameter.

  return-transaction-objects? - If true, the returned block will contain all
                                transactions as objects, if false it will
                                only contains the transaction hashes.
  callback-fn                 - callback with two parameters, error and result

  Returns the block object:

  - number: Number - the block number. null when its pending block.
  - hash: String, 32 Bytes - hash of the block. null when its pending block.
  - parent-hash: String, 32 Bytes - hash of the parent block.
  - nonce: String, 8 Bytes - hash of the generated proof-of-work. null when its
                             pending block.
  - sha3-uncles: String, 32 Bytes - SHA3 of the uncles data in the block.
  - logs-bloom: String, 256 Bytes - the bloom filter for the logs of the block.
                                   null when its pending block.
  - transactions-root: String, 32 Bytes - the root of the transaction trie of the
                                          block
  - state-root: String, 32 Bytes - the root of the final state trie of the block.
  - miner: String, 20 Bytes - the address of the beneficiary to whom the mining
                              rewards were given.
  - difficulty: BigNumber - integer of the difficulty for this block.
  - total-difficulty: BigNumber - integer of the total difficulty of the chain
                                  until this block.
  - extra- data: String - the \"extra data\" field of this block.
  - size: Number - integer the size of this block in bytes.
  - gas- limit: Number - the maximum gas allowed in this block.
  - gas-used: Number - the total used gas by all transactions in this block.
  - timestamp: Number - the unix timestamp for when the block was collated.
  - transactions: Array - Array of transaction objects, or 32 Bytes transaction
                          hashes depending on the last given parameter.
  - uncles: Array - Array of uncle hashes.

  Example:
  user> `(get-block web3-instance
                    0
                    false
                    (fn [err res] (when-not err (println res))))`
  nil
  user> {:state-root 0x.., :hash 0x.., :number 0, :difficulty #object[e 1048576],
         ...}"
  [web3 & [block-hash-or-number return-transaction-objects? :as args]]
  (js-apply (eth web3) "getBlock" args))


(defn get-block-transaction-count
  "Returns the number of transaction in a given block.

  Parameters
  web3                 - web3 instance
  block-hash-or-number - The block number or hash. Or the string \"earliest\",
                         \"latest\" or \"pending\" as in the default block
                         parameter.
  callback-fn          - callback with two parameters, error and result

  Example:
  user> `(get-block-transaction-count
           web3-instance
           0
           (fn [err res] (when-not err (println res))))`
  nil
  user> 0"
  [web3 & [block-hash-or-number :as args]]
  (js-apply (eth web3) "getBlockTransactionCount" args))


(defn get-uncle
  "Returns a blocks uncle by a given uncle index position.
  Parameters

  Parameters:
  web3                        - web3 instance
  block-hash-or-number        - The block number or hash. Or the string
                                \"earliest\", \"latest\" or \"pending\" as in
                                the default block parameter
  uncle-number                - The index position of the uncle
  return-transaction-objects? - If true, the returned block will contain all
                                transactions as objects, if false it will only
                                contains the transaction hashes
  default-block               - If you pass this parameter it will not use the
                                default block set with (set-default-block)
  callback-fn                 - callback with two parameters, error and result

  Returns the returned uncle. For a return value see `(get-block)`.

  Note: An uncle doesn't contain individual transactions."
  [web3 & [block-hash-or-number uncle-number return-transaction-objects? :as args]]
  (js-apply (eth web3) "getUncle" args))


(defn get-transaction
 "Returns a transaction matching the given transaction hash.

  Parameters:
  web3             - web3 instance
  transaction-hash - The transaction hash.
  callback-fn      - callback with two parameters, error and result

  Returns a transaction object its hash transaction-hash:

  - hash: String, 32 Bytes - hash of the transaction.
  - nonce: Number - the number of transactions made by the sender prior to this
    one.
  - block-hash: String, 32 Bytes - hash of the block where this transaction was
                                   in. null when its pending.
  - block-number: Number - block number where this transaction was in. null when
                           its pending.
  - transaction-index: Number - integer of the transactions index position in the
                                block. null when its pending.
  - from: String, 20 Bytes - address of the sender.
  - to: String, 20 Bytes - address of the receiver. null when its a contract
                           creation transaction.
  - value: BigNumber - value transferred in Wei.
  - gas-price: BigNumber - gas price provided by the sender in Wei.
  - gas: Number - gas provided by the sender.
  - input: String - the data sent along with the transaction.

  Example:
  user> `(get-transaction
           web3-instance
           \"0x...\"
           (fn [err res] (when-not err (println res))))`
  nil
  user> {:r 0x...
         :v 0x2a
         :hash 0xf...
         :transaction-index 3 ...
         (...)
         :to 0x...}"
  [web3 & [transaction-hash :as args]]
  (js-apply (eth web3) "getTransaction" args))


(defn get-transaction-from-block
  "Returns a transaction based on a block hash or number and the transactions
  index position.

  Parameters:
  web3                 - web3 instance
  block-hash-or-number - A block number or hash. Or the string \"earliest\",
                         \"latest\" or \"pending\" as in the default block
                         parameter.
  index                - The transactions index position.
  callback-fn          - callback with two parameters, error and result
  Number               - The transactions index position.

  Returns a transaction object, see `(get-transaction)`

  Example:
  user> `(get-transaction-from-block
           web3-instance
           1799402
           0
           (fn [err res] (when-not err (println res))))`
  nil
  user> {:r 0x...
         :v 0x2a
         :hash 0xf...
         :transaction-index 0 ...
         (...)
         :to 0x...}"
  [web3 & [block-hash-or-number index :as args]]
  (js-apply (eth web3) "getTransactionFromBlock" args))


(defn get-transaction-receipt
  "Returns the receipt of a transaction by transaction hash.

  Note That the receipt is not available for pending transactions.

  Parameters:
  web3              - web3 instance
  transaction-hash  - The transaction hash.
  callback-fn       - callback with two parameters, error and result

  Returns transaction receipt object, or null when no receipt was found:

  - blockHash: String, 32 Bytes - hash of the block where this transaction was
                                  in.
  - blockNumber: Number - block number where this transaction was in.
  - transactionHash: String, 32 Bytes - hash of the transaction.
  - transactionIndex: Number - integer of the transactions index position in the
                               block.
  - from: String, 20 Bytes - address of the sender.
  - to: String, 20 Bytes - address of the receiver. null when its a contract
                           creation transaction.
  - cumulativeGasUsed: Number - The total amount of gas used when this
                                transaction was executed in the block.
  - gasUsed: Number - The amount of gas used by this specific transaction alone.
  - contractAddress: String - 20 Bytes - The contract address created, if the
                                         transaction was a contract creation,
                                         otherwise null.
  - logs: Array - Array of log objects, which this transaction generated.

  Example"
  [web3 & [transaction-hash :as args]]
  (js-apply (eth web3) "getTransactionReceipt" args))


(defn get-transaction-count
  "Get the numbers of transactions sent from this address.

  Parameters:
  web3          - web3 instance
  address       - The address to get the numbers of transactions from.
  default-block - If you pass this parameter it will not use the default block
                  set with set-default-block.
  callback-fn   - callback with two parameters, error and result

  Returns the number of transactions sent from the given address.

  Example:
  user> `(get-transaction-count web3-instance \"0x8\"
           (fn [err res] (when-not err (println res))))`
  nil
  user> 16"
  [web3 & [address default-block :as args]]
  (js-apply (eth web3) "getTransactionCount" args))


(defn send-transaction!
  "Sends a transaction to the network.

  Parameters:
  web3               - web3 instance
  transaction-object - The transaction object to send:

    :from: String - The address for the sending account. Uses the
                    `default-account` property, if not specified.

    :to: String   - (optional) The destination address of the message, left
                               undefined for a contract-creation
                               transaction.

    :value        - (optional) The value transferred for the transaction in
                               Wei, also the endowment if it's a
                               contract-creation transaction.

    :gas:         - (optional, default: To-Be-Determined) The amount of gas
                    to use for the transaction (unused gas is refunded).
    :gas-price:   - (optional, default: To-Be-Determined) The price of gas
                    for this transaction in wei, defaults to the mean network
                    gas price.
    :data:        - (optional) Either a byte string containing the associated
                    data of the message, or in the case of a contract-creation
                    transaction, the initialisation code.
    :nonce:       - (optional) Integer of a nonce. This allows to overwrite your
                               own pending transactions that use the same nonce.
  callback-fn   - callback with two parameters, error and result, where result
                  is the transaction hash

  Returns the 32 Bytes transaction hash as HEX string.

  If the transaction was a contract creation use `(get-transaction-receipt)` to
  get the contract address, after the transaction was mined.

  Example:
  user> (send-transaction! web3-instance {:to \"0x..\"}
          (fn [err res] (when-not err (println res))))
  nil
  user> 0x..."
  [web3 & [transaction-object :as args]]
  (js-apply (eth web3) "sendTransaction" args))


(defn send-raw-transaction!
  "Sends an already signed transaction. For example can be signed using:
  https://github.com/SilentCicero/ethereumjs-accounts

  Parameters:
  web3                    - web3 instance
  signed-transaction-data - Signed transaction data in HEX format

  callback-fn             - callback with two parameters, error and result

  Returns the 32 Bytes transaction hash as HEX string.

  If the transaction was a contract creation use `(get-transaction-receipt)`
  to get the contract address, after the transaction was mined.

  See https://github.com/ethereum/wiki/wiki/JavaScript-API#example-46 for a
  JavaScript example."
  [web3 & [signed-transaction-data :as args]]
  (js-apply (eth web3) "sendRawTransaction" args))


(defn send-iban-transaction!
  "Sends IBAN transaction from user account to destination IBAN address.

  Parameters:
  web3          - web3 instance
  from          - address from which we want to send transaction
  iban-address  - IBAN address to which we want to send transaction
  value         - value that we want to send in IBAN transaction
  callback-fn   - callback with two parameters, error and result

  Note: uses smart contract to transfer money to IBAN account.

  Example:
  user> `(send-iban-transaction! '0xx'
                                 'NL88YADYA02'
                                  0x100
                                  (fn [err res] (prn res)))`"
  [web3 & [from iban-address value :as args]]
  (js-apply (eth web3) "sendIBANTransaction" args))

(defn sign
  "Signs data from a specific account. This account needs to be unlocked.

  Parameters:
  web3          - web3 instance
  address       - The address to sign with
  data-to-sign  - Data to sign
  callback-fn   - callback with two parameters, error and result

  Returns the signed data.

  After the hex prefix, characters correspond to ECDSA values like this:

  r = signature[0:64]
  s = signature[64:128]
  v = signature[128:130]

  Note that if you are using ecrecover, v will be either \"00\" or \"01\". As a
  result, in order to use this value, you will have to parse it to an integer
  and then add 27. This will result in either a 27 or a 28.

  Example:
  user> `(sign web3-instance
               \"0x135a7de83802408321b74c322f8558db1679ac20\"
               \"0x9dd2c369a187b4e6b9c402f030e50743e619301ea62aa4c0737d4ef7e10a3d49\"
               (fn [err res] (when-not err (println res))))`

  user> 0x3..."
  [web3 & [address data-to-sign :as args]]
  (js-apply (eth web3) "sign" args))


(defn sign-transaction
  "Sign a transaction. Method is not documented in the web3.js docs. Not sure if it is safe.

  Parameters:
  web3           - web3 instance
  sign-tx-params - Parameters of transaction
                   See `(send-transaction!)`
  private-key    - Private key to sign the transaction with
  callback-fn    - callback with two parameters, error and result

  Returns signed transaction data."
  [web3 & [sign-tx-params private-key signed-tx :as args]]
  (js-apply (eth web3) "signTransaction" args))


(defn call!
  "Executes a message call transaction, which is directly executed in the VM of
  the node, but never mined into the blockchain.

  Parameters:
  web3          - web3 instance
  call-object   - A transaction object see web3.eth.sendTransaction, with the
                  difference that for calls the from property is optional as
                  well.
  default-block - If you pass this parameter it will not use the default block
                  set with set-default-block.
  callback-fn   - callback with two parameters, error and result

  Returns the returned data of the call as string, e.g. a codes functions return
  value.

  Example:
  user> `(call! web3-instance {:to   \"0x\"
                               :data \"0x\"}
                (fn [err res] (when-not err (println res))))`
  nil
  user> 0x"
  [web3 & [call-object default-block :as args]]
  (js-apply (eth web3) "call" args))


(defn estimate-gas

  "Executes a message call or transaction, which is directly executed in the VM
  of the node, but never mined into the blockchain and returns the amount of the
  gas used.

  Parameters:
  web3          - web3 instance
  call-object   - See `(send-transaction!)`, except that all properties are
                  optional.
  callback-fn   - callback with two parameters, error and result

  Returns the used gas for the simulated call/transaction.

  Example:
  user> `(estimate-gas web3-instance
           {:to   \"0x135a7de83802408321b74c322f8558db1679ac20\",
            :data \"0x135a7de83802408321b74c322f8558db1679ac20\"}
           (fn [err res] (when-not err (println res))))`
  nil
  user> 22361"
  [web3 & [call-object :as args]]

  (js-apply (eth web3) "estimateGas" args))


(defn filter
  "Parameters:
  web3          - web3 instance
  block-or-transaction  - The string \"latest\" or \"pending\" to watch
                          for changes in the latest block or pending
                          transactions respectively. Or a filter options
                          object as follows:

    from-block: Number|String - The number of the earliest block (latest may be
                                given to mean the most recent and pending
                                currently mining, block). By default
                               latest.
    to-block: Number|String   - The number of the latest block (latest may be
                                given to mean the most recent and pending
                                currently mining, block). By default latest.

    address: String           - An address or a list of addresses to only get
                                logs from particular account(s).

    :topics: Array of Strings - An array of values which must each appear in the
                                log entries. The order is important, if you want
                                to leave topics out use null, e.g.
                                `[null, '0x00...']`. You can also pass another array
                                for each topic with options for that topic e.g.
                                `[null, ['option1', 'option2']]`

  Returns a filter object with the following methods:

    `(.get filter callback-fn)`:   Returns all of the log entries that fit the
                                   filter.
    `(.watch filter callback-fn)`: Watches for state changes that fit the
                                   filter and calls the callback.
    `(.stopWatching filter)`:      Stops the watch and uninstalls the filter in the
                                   node. Should always be called once it is done.

  Watch callback return value

    String - When using the \"latest\" parameter, it returns the block hash of
             the last incoming block.

    String - When using the \"pending\" parameter, it returns a transaction hash
             of the most recent pending transaction.
    Object - When using manual filter options, it returns a log object as follows:

        logIndex: Number - integer of the log index position in the block. null
                           when its pending log.
        transactionIndex: Number - integer of the transactions index position log
                                   was created from. null when its pending log.
        transactionHash: String, 32 Bytes - hash of the transactions this log was
                                            created from. null when its pending log.
        blockHash: String, 32 Bytes - hash of the block where this log was in. null
                                      when its pending. null when its pending log.
        blockNumber: Number - the block number where this log was in. null when its
                              pending. null when its pending log.
        address: String, 32 Bytes - address from which this log originated.
        data: String - contains one or more 32 Bytes non-indexed arguments of the log.

        topics: Array of Strings - Array of 0 to 4 32 Bytes DATA of indexed log
                                   arguments. (In solidity: The first topic is the hash
                                   of the signature of the event, except if you declared the
                                   event with the anonymous specifier.)

  Note for event filter return values see Contract Events at
  https://github.com/ethereum/wiki/wiki/JavaScript-API#contract-events"
  [web3 & args]
  (js-apply (eth web3) "filter" args))


(defn get-compilers
  "Compiling features being deprecated https://github.com/ethereum/EIPs/issues/209"
  [web3 & args]
  (js-apply (eth web3) "getCompilers" args))


(defn compile-solidity
  "Compiling features being deprecated https://github.com/ethereum/EIPs/issues/209"
  [web3 & [source-string :as args]]
  (js-apply (get-compile web3) "solidity" args))


(defn compile-lll
  "Compiling features being deprecated https://github.com/ethereum/EIPs/issues/209"
  [web3 & [source-string :as args]]
  (js-apply (get-compile web3) "lll" args))


(defn compile-serpent
  "Compiling features being deprecated https://github.com/ethereum/EIPs/issues/209"
  [web3 & [source-string :as args]]
  (js-apply (get-compile web3) "serpent" args))


(defn namereg
  "Returns GlobalRegistrar object.

  See https://github.com/ethereum/web3.js/blob/master/example/namereg.html
  for an example in JavaScript."
  [web3]
  (aget (eth web3) "namereg"))


(defn contract
  "Creates a contract object for a solidity contract, which can be used to
  initiate contracts on an address.

  Parameters:
  web3          - web3 instance
  abi           - ABI array with descriptions of functions and events of
                  the contract
  callback-fn   - callback with two parameters, error and result

  Returns a contract object."
  [web3 & [abi :as args]]
  (js-apply (eth web3) "contract" args))


(defn contract-at
  "Initiate an existing contract on an address.

  Parameters:
  web3          - web3 instance
  abi           - ABI array with descriptions of functions and events of
                  the contract
  address       - The address of the existing contract

  Example:
  user> `(contract-at web3-instance
                      abi
                      address)`"
  [web3 abi & [address :as args]]
  (js-apply (contract web3 abi) "at" args))


(defn contract-new
  "Deploy a contract asynchronous from a Solidity file.

  Parameters:
  web3             - web3 instance
  abi              - ABI array with descriptions of functions and events of
                     the contract
  transaction-data - map that contains
    - :gas - max gas to use
    - :data the BIN of the contract
    - :from account to use
  callback-fn      - callback with two parameters, error and contract.
                     From the contract the \"address\" property can be used to
                     obtain the address. And the \"transactionHash\" to obtain
                     the hash of the transaction, which created the contract.

  Example:
  `(contract-new web3-instance
                 abi
                 {:from \"0x..\"
                  :data bin
                  :gas  4000000}
                 (fn [err contract]
                   (if-not err
                    (let [address (aget contract \"address\")
                          tx-hash (aget contract \"transactionHash\")]
                      ;; Two calls: transaction received
                      ;; and contract deployed.
                      ;; Check address on the second call
                      (when (address? address)
                        (do-something-with-contract contract)
                        (do-something-with-address address)))
                    (println \"error deploying contract\" err))))`
   nil"
  [web3 abi & [transaction-data callback-fn :as args]]
  (js-apply (contract web3 abi) "new" args))


(defn contract-call
  "Explicitly call a method on a contract.

  Use the kebab-cases version of the original method.
  E.g., function fooBar() can be addressed with :foo-bar.

  Parameters:
  contract-instance - an instance of the contract (obtained via `contract` or
                      `contract-at`)
  method            - the kebab-cased version of the method
  args              - arguments to the method

  Example:
  user> `(web3-eth/contract-call ContractInstance :multiply 5)`
  25"
  [contract-instance method & args]
  (js-apply contract-instance method args))


(defn stop-watching!
  "Stops and uninstalls the filter.

  Arguments:
  filter - the filter to stop"
  [filter & args]
  (js-apply filter "stopWatching" args))
