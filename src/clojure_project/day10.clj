(ns clojure-project.day10
  (:use clojure-project.core))

(defn registerx [lines]
  (->> lines
       (map #(let [[inst & attr] (clojure.string/split % #" ")] (if (= inst "addx") [:addx (Integer/parseInt (first attr))] [:noop])))
       (map #(if (= (first %) :addx)
               (list 0 (second %))
               (list 0)))
       (apply concat)
       (reduce #(cons (+ (first %1) %2) %1) (list 1))
       (reverse)
       (cons 1)
       ))

(defn func [lines]
  (->> lines
       (registerx)
       (map vector (range))
       (drop 20)
       (take-nth 40)
       (map (partial apply *))
       (apply +)
       ))

(defn produce-pixel [a b]
  (if (>= 1 (abs (- a b))) \# \.))

(defn func2 [lines]
  (->> lines
       (registerx)
       (rest)
       (partition 40)
       (map (partial map produce-pixel (range)))
       (map (partial apply str))
       (clojure.string/join "\n")
       (println)
       ))

;; (process-input "resources/day10" func)
(process-input "resources/day10" func2)
