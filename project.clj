(defproject lambdaproperties "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0-alpha10"] [org.clojure/test.check "0.9.0"]]
  :plugins [[cider/cider-nrepl "0.8.1"] [lein-kibit "0.1.2"] [jonase/eastwood "0.2.3"]]
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
