(ns cljs-web3.utils
  (:require [camel-snake-kebab.core :as cs :include-macros true]
            [camel-snake-kebab.extras :refer [transform-keys]]
            [cljs.core.async :refer [>! chan]]
            [clojure.string :as string])
  (:require-macros [cljs.core.async.macros :refer [go]]))

(defn safe-case [case-f]
  (fn [x]
    (cond-> (subs (name x) 1)
      true (string/replace "_" "*")
      true case-f
      true (string/replace "*" "_")
      true (->> (str (first (name x))))
      (keyword? x) keyword)))

(def camel-case (safe-case cs/->camelCase))
(def kebab-case (safe-case cs/->kebab-case))

(def js->cljk #(js->clj % :keywordize-keys true))

(def js->cljkk
  "From JavaScript to Clojure with kekab-cased keywords."
  (comp (partial transform-keys kebab-case) js->cljk))

(def cljkk->js
  "From Clojure with kebab-cased keywords to JavaScript."
  (comp clj->js (partial transform-keys camel-case)))

(defn callback-js->clj [x]
  (if (fn? x)
    (fn [err res]
      (x err (js->cljkk res)))
    x))

(defn args-cljkk->js [args]
  (map (comp cljkk->js callback-js->clj) args))

(defn js-apply
  ([this method-name]
   (js-apply this method-name nil))
  ([this method-name args]
   (let [method-name (name method-name)]
     (if-let [method (aget this (camel-case method-name))]
       (js->cljkk (.apply method this (clj->js (args-cljkk->js args))))
       (throw (str "Method: " method-name " was not found in object."))))))

(defn js-prototype-apply [js-obj method-name args]
  (js-apply (aget js-obj "prototype") method-name args))

(defn prop-or-clb-fn
  "Constructor to create an fn to get properties or to get properties and apply a
  callback fn."
  [& ks]
  (fn [web3 & args]
    (if (fn? (first args))
      (js-apply (apply aget web3 (butlast ks))
                (str "get" (cs/->PascalCase (last ks)))
                args)
      (js->cljkk (apply aget web3 ks)))))

(defn create-async-fn [f]
  (fn [& args]
    (let [[ch args] (if (instance? cljs.core.async.impl.channels/ManyToManyChannel (first args))
                      [(first args) (rest args)]
                      [(chan) args])]
      (apply f (concat args [(fn [err res]
                               (go (>! ch [err res])))]))
      ch)))
