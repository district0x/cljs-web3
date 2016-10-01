(ns cljs-web3.run-tests
  (:require [print.foo :include-macros true]
            [cljs.test :refer-macros [run-tests]]
            [cljs-web3.tests]))

(defn run-all-tests []
  (.clear js/console)
  (run-tests 'cljs-web3.tests))

(comment
  (run-all-tests))

