(ns cljs-web3.personal
  "The web3-eth-personal package allows you to interact with the Ethereum node’s
  accounts."
  (:require [cljs-web3.utils :as u :refer [js-apply]]))


(defn get-prs
  "Get the web3-eth-personal package.

  Parameter:
  web3 - web3 instance"
  [web3]
  (aget web3 "personal"))


(def list-accounts
  "List accounts available.

  Parameter:
  web3 - web3 instance

  Returns all the Ethereum account addresses of all keys in the key store.

  Example:
  user> `(list-accounts web3-instance)`
  [\"0x5e97870f263700f46aa00d967821199b9bc5a120\",
   \"0x3d80b31a78c30fc628f20b2c89d7ddbf6e53cedc\"]"
  (u/prop-or-clb-fn "personal" "listAccounts"))


(defn lock-account
  "Removes the private key with given address from memory. The account can no
  longer be used to send transactions.

  Parameters:
  web3    - web3 instance
  address - address to lock"
  [web3 & args]
  (js-apply (get-prs web3) "lockAccount" args))


(defn new-account
  "Creates a new account.

  Note: Never call this function over a unsecured Websocket or HTTP provider, as
  your password will be send in plain text!

  Parameters:
  web3 - web3 instance
  password - String: The password to encrypt this account with.

  Returns:
  Promise returns Boolean: true if the account was created, otherwise
  false."
  [web3 & args]
  (js-apply (get-prs web3) "newAccount" args))


(defn unlock-account
  "Unlocks the given account for duration seconds.

  Parameters:
  web3       - web3 instance
  address    - address to unlock
  passphrase - passphrase
  duration   - time to unlock for in seconds, 0 for indefinitely
  callback   - callback with error and result parameters

  Returns boolean as to whether the account was successfully unlocked.

  Example:
  user> `(web3-personal/unlock-account web3-instance
                                   account
                                   \"password\"
                                   indefinitely
                                   callback)`"
  [web3 & args]
  (js-apply (get-prs web3) "unlockAccount" args))


(defn ec-recover
  "Recovers the Ethereum address which was used to sign the given data.

  Parameters:
  web3 - web3 instance
  signature - String|Object: Either the encoded signature, the v, r, s values as
                             separate parameters, or an object with the following
                             values:
      messageHash - String: The hash of the given message.
      r - String: First 32 bytes of the signature
      s - String: Next 32 bytes of the signature
      v - String: Recovery value + 27

  Returns the Ethereum address used to sign this data."
  [web3 & args]
  (js-apply (get-prs web3) "ecRecover" args))


(defn import-raw-key
  "Imports the given unencrypted private key (hex string) into the key store,
  encrypting it with the passphrase.

  Parameters:
  web3       - web3 instance
  keydata    - hex string representing the unencrypted private key
  passphrase - passphrase
  callback   - callback with error and result as parameters

  Returns the address of the new account."
  [web3 & args]
  (js-apply (get-prs web3) "importRawKey" args))


(defn send-transaction
  "Validate the given passphrase and submit transaction.

  Parameters:
  web3        - web3 instance
  transaction - The same argument as for `(web3-eth/send-transaction! ...)`
                and contains the from address. If the passphrase can be
                used to decrypt the private key belonging to tx.from the
                transaction is verified, signed and send onto the network.
                The account is not unlocked globally in the node and cannot
                be used in other RPC calls.
  passphrase  - passphrase
  callback    - callback with error and result as parameters

  Example:
  user> `(send-transaction web3-instance
           {:from \"0x...\"
            :to   \"0x\"}
          \"password\"
         callback)`"
  [web3 & args]
  (js-apply (get-prs web3) "sendTransaction" args))


(defn sign
  "Signs data using a specific account.

  Note: Sending your account password over an unsecured HTTP RPC connection is
  highly unsecure.

  Parameters:
  web3     - web3 instance
  String   - Data to sign. If String it will be converted using
             web3.utils.utf8ToHex.
  String   - Address to sign data with.
  String   - The password of the account to sign data with.
  Function - (optional) Optional callback, returns an error object as first
                        parameter and the result as second.

  Returns:
  Promise returns String - The signature."
  [web3 & args]
  (js-apply (get-prs web3) "sign" args))
