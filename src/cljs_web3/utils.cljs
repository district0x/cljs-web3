(ns cljs-web3.utils
  (:require
    [camel-snake-kebab.core :as cs :include-macros true]
    [camel-snake-kebab.extras :refer [transform-keys]]
    [clojure.string :as string]
    [cljs.core.async :refer [>! chan]])
  (:require-macros [cljs.core.async.macros :refer [go]]))

(defn js-val [clj-or-js-dict]
  (cond
    (map? clj-or-js-dict) (clj->js clj-or-js-dict)
    (vector? clj-or-js-dict) (clj->js clj-or-js-dict)
    :else clj-or-js-dict))

(def js->cljk #(js->clj % :keywordize-keys true))

(def js->cljkk (comp (partial transform-keys cs/->kebab-case) js->cljk))

(def cljkk->js (comp clj->js (partial transform-keys cs/->camelCase)))

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
     (if-let [method (aget this (if (string/includes? method-name "-") ; __callback gets wrongly transformed
                                  (cs/->camelCase method-name)
                                  method-name))]
       (js->cljkk (.apply method this (clj->js (args-cljkk->js args))))
       (throw (str "Method: " method-name " was not found in object."))))))

(defn js-prototype-apply [js-obj method-name args]
  (js-apply (aget js-obj "prototype") method-name args))

(defn prop-or-clb-fn [& ks]
  (fn [web3 & args]
    (if (fn? (first args))
      (js-apply (apply aget web3 (butlast ks)) (str "get" (cs/->PascalCase (last ks))) args)
      (js->cljkk (apply aget web3 ks)))))

(defn create-async-fn [f]
  (fn [& args]
    (let [[ch args] (if (instance? cljs.core.async.impl.channels/ManyToManyChannel (first args))
                      [(first args) (rest args)]
                      [(chan) args])]

      (apply f (concat args [(fn [err res]
                               (go (>! ch [err res])))]))
      ch)))