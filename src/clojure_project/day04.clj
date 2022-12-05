(ns clojure-project.day04
  (:use clojure-project.core))

(defn int-subs [intervals]
  (let [[[a0 a1] [b0 b1]] intervals]
    (or
     (and (>= a0 b0) (<= a1 b1))
     (and (<= a0 b0) (>= a1 b1)))))

(defn int-overlap [intervals]
  (let [[[a0 a1] [b0 b1]] intervals]
    (or
     (<= a0 b0 a1)
     (<= a0 b1 a1)
     (<= b0 a0 b1)
     (<= b0 a1 b1))))

(defn parse-interval [si]
  (->> (clojure.string/split si #"-")
       (mapv #(Integer/parseInt %))))

(defn func [lines]
  (->> (map (fn [l]
              (->> (clojure.string/split l #",")
                   (mapv parse-interval)
                   (int-subs)
                   )) lines)
       (filter identity)
       (count)
       ))

(defn func2 [lines]
  (->> (map (fn [l]
              (->> (clojure.string/split l #",")
                   (mapv parse-interval)
                   (int-overlap)
                   )) lines)
       (filter identity)
       (count)
       ))

(process-input "resources/day04" func)  ;; Part 1
(process-input "resources/day04" func2) ;; Part 2
