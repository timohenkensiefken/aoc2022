(ns clojure-project.day03
  (:use clojure-project.core))

(defn halfs [s]
  (let [n (count s)]
    [(subs s 0 (/ n 2))
     (subs s (/ n 2) n)]))

(defn prioritize [c]
  (let [i (int c)]
    (cond
      (<= 97 i 122) (- i 96)
      (<= 65 i 90) (- i 38)
      :else 0)))

(defn func
  [lines]
  (->> (map halfs lines)
       (map #(mapv set %))
       (map #(apply clojure.set/intersection %))
       (map vec)
       (map first)
       (map prioritize)
       (apply +)
       ))

(defn func2
  [lines]
  (->> (map set lines)
       (partition 3)
       (map (partial apply clojure.set/intersection))
       (map vec)
       (map first)
       (map prioritize)
       (apply +)
       ))


(process-input "resources/day03" func2)
