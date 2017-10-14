(defproject cljs-web3 "0.19.0-0-7"
  :description "Clojurescript API for Ethereum Web3 API"
  :url "https://github.com/madvas/cljs-web3"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojurescript "1.9.227"]
                 [camel-snake-kebab "0.4.0"]
                 [cljsjs/web3 "0.19.0-0"]]
  :plugins [[lein-cljsbuild "1.1.4"]]

  :figwheel {:server-port 6612}

  :clean-targets ^{:protect false} ["resources/public/js/compiled" "target"]

  :repl-options {:nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}
  :resource-paths []

  :profiles {:dev
             {:dependencies [[org.clojure/clojure "1.8.0"]
                             [binaryage/devtools "0.8.1"]
                             [com.cemerick/piggieback "0.2.1"]
                             [figwheel-sidecar "0.5.13"]
                             [org.clojure/tools.nrepl "0.2.11"]]
              :plugins [[lein-figwheel "0.5.13"]]
              :source-paths ["env/dev"]
              :resource-paths ["resources"]
              :cljsbuild {:builds [{:id "dev"
                                    :source-paths ["src" "test"]
                                    :figwheel {:on-jsload cljs-web3.run-tests/run-all-tests}
                                    :compiler {:main "cljs-web3.run-tests"
                                               :output-to "resources/public/js/compiled/app.js"
                                               :output-dir "resources/public/js/compiled/out"
                                               :asset-path "/js/compiled/out"
                                               :source-map-timestamp true
                                               :optimizations :none
                                               :preloads [print.foo.preloads.devtools]
                                               }}]}}})
