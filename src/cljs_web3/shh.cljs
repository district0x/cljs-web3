(ns cljs-web3.shh
  "The web3-shh package allows you to interact with an the whisper protocol for
  broadcasting. For more see Whisper Overview at
  https://github.com/ethereum/go-ethereum/wiki/Whisper."
  (:refer-clojure :exclude [filter])
  (:require [cljs-web3.utils :as u :refer [js-apply]]))


(defn get-shh
  "Obtain Whisper package from web3 object

  Parameters:
  web3 - web3 instance"
  [web3]
  (aget web3 "shh"))


(defn post!
  "This method should be called, when we want to post whisper a message to the
  network.

  Parameters:
  web3 - web3 instance
  data - The post object:
            :sym-key-id - String (optional): ID of symmetric key for message
                                             encryption (Either symKeyID or
                                             pubKey must be present. Cannot be both.)

            :pub-key - String (optional): The public key for message
                                          encryption (Either symKeyID or pubKey must
                                          be present. Cannot be both.)
            :sig - String (optional): The ID of the signing key.
            :ttl - Number: Time-to-live in seconds.
            :topic - String: 4 Bytes (mandatory when key is symmetric): Message
                                                                        topic.
            :payload - String: The payload of the message to be encrypted.
            :padding - Number (optional): Padding (byte array of arbitrary
                                          length).
            :pow-time - Number (optional)?: Maximal time in seconds to be spent on
                                            proof of work.
            :pow-target - Number (optional)?: Minimal PoW target required for this
                                              message.
            :target-peer - Number (optional): Peer ID (for peer-to-peer message only).
    callback - Function: (optional) Optional callback, returns an error object
                                    as first parameter and the result as second.

  Returns:
  Boolean - returns true if the message was send, otherwise false or error.

  JavaScript example: http://web3js.readthedocs.io/en/1.0/web3-shh.html#id74"
  [web3 & args]
  (js-apply (get-shh web3) "post" args))


(defn new-identity
  "Seems deprecated since no JavaScript documentation available at
  http://web3js.readthedocs.io/en/1.0/web3-shh.html"
  [web3 & args]
  (js-apply (get-shh web3) "newIdentity" args))

(defn has-identity?
  "Seems deprecated since no JavaScript documentation available at
  http://web3js.readthedocs.io/en/1.0/web3-shh.html"
  [web3 & args]
  (js-apply (get-shh web3) "hasIdentity" args))

(defn new-group
  "Seems deprecated since no JavaScript documentation available at
  http://web3js.readthedocs.io/en/1.0/web3-shh.html"
  [web3 & args]
  (js-apply (get-shh web3) "newGroup" args))

(defn add-to-group
  "Seems deprecated since no JavaScript documentation available at
  http://web3js.readthedocs.io/en/1.0/web3-shh.html"
  [web3 & args]
  (js-apply (get-shh web3) "addToGroup" args))
