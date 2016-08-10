(ns lambdaproperties.core
  (:require [clojure.spec :as s]))


(defn- framerize [rolls]
  (lazy-seq
   (let [first-pin (first rolls)
         second-pin (second rolls)
         is-strike (= 10 first-pin)
         is-spare  (= 10 (+ first-pin second-pin))
         has-bonus (or is-strike is-spare)
         to-take (if has-bonus 3 2)
         to-drop (if is-strike 1 2)]
     (cons
      (take to-take rolls)
      (framerize (drop to-drop rolls))))))

(defn- to-frames [rolls]
  (take 10 (framerize (concat rolls (repeat 0)))))


(defn bowling [rolls]
  (reduce + 0 (flatten (to-frames rolls))))



;;; AH HA
(defn myadd [x y]
  (+ x y))


;;; onwards to .spec


(def score? (into #{} (range 0 301)))

(def roll?
  (into #{} (range 0 11)))

(def nominal-frame?
  (into #{}
        (for [a roll? b roll?
              :when (< (+ a b) 10)]
          [a b])))

(def spare-frame?
  (into #{}
        (for [a roll? b roll?
              :when (= 10 (+ a b))]
          [a b])))

(def strike-frame? #{[10]})


(def usual-frame? (clojure.set/union
                   strike-frame?
                   spare-frame?
                   nominal-frame?))

(def strike-final-frame?
  (into #{}
        (for [a roll? b roll?] [10 a b])))

(def spare-final-frame?
  (into #{}
        (for [a roll? b roll? c roll?
              :when (= 10 (+ a b))]
          [a b c])))

(def final-frame? (clojure.set/union
                   strike-final-frame?
                   spare-final-frame?
                   nominal-frame?))


(def frame? (clojure.set/union
             usual-frame?
             final-frame?))


(comment
  ;; VERY BAD IDEA
  ;; pay attention to cardinality
  (def framed-playthrough?
    (into #{}
          (for [a usual-frame?
                b usual-frame?
                c usual-frame?
                d usual-frame?
                e usual-frame?
                f usual-frame?
                g usual-frame?
                h usual-frame?
                i usual-frame?
                j final-frame?]
            [a b c d e f g h i j]))))



;; (defn framed-playthrough?
;;   [frames]
;;   (and
;;    (= 10 (count frames))
;;    (usual-frame? (nth frames 0))
;;    (usual-frame? (nth frames 1))
;;    (usual-frame? (nth frames 2))
;;    (usual-frame? (nth frames 3))
;;    (usual-frame? (nth frames 4))
;;    (usual-frame? (nth frames 5))
;;    (usual-frame? (nth frames 6))
;;    (usual-frame? (nth frames 7))
;;    (usual-frame? (nth frames 8))
;;    (final-frame? (nth frames 9))))

(s/def ::framed-playthrough?
  (s/tuple usual-frame? usual-frame? usual-frame?
           usual-frame? usual-frame? usual-frame?
           usual-frame? usual-frame? usual-frame?
           final-frame?))



(defn spec-bowling [rolls]
  (reduce + 0 (flatten (to-frames rolls))))

(s/fdef spec-bowling
  :args ::framed-playthrough?
  :ret score?)
