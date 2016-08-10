(ns lambdaproperties.core-test
  (:require [clojure.test :refer :all]
            [clojure.test.check :refer [quick-check]]
            [clojure.test.check.generators :as gen]
            [clojure.test.check.properties :refer [for-all]]
            [clojure.test.check.clojure-test :refer [defspec]]
            [clojure.tools.namespace.repl :refer [refresh]]
            [clojure.spec :as s]
            [lambdaproperties.core :refer :all]))


              (<= 0 score 300)
              (= score roll)))))



(defspec bowling-nominal-case
    (for-all [rolls (gen/vector
                     (gen/elements (range 0 5)))]
           (let [score (bowling rolls)]
             (and
              (<= 0 score 300)
              (= score (apply + rolls))))))



(defspec bowling-nominal-case
  (for-all [rolls (gen/vector (gen/elements (range 0 5)) 0 20)]
           (let [score (bowling rolls)]
             (and
              (<= 0 score 300)
              (= score (apply + rolls))))))



(defn t []
    (let [tns 'lambdaproperties.core-test]
      (require tns :reload-all)
      (clojure.test/test-ns tns)))


#_(refresh)

(t)
