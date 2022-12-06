(ns clojure-project.day06
  (:use clojure-project.core))

(defn findn [n line]
  (->> (apply map
         (fn [i & as]
           (-> as
               (set)
               (count)
               (= n)
               (if i :nf))) ; eval to :nf if chars not unique
         (drop n (range))
         (map #(drop % line) (range 0 n)))
       (filter #(not (= :nf %)))
       (first)
       ))

(defn func "Teil 1" [lines]
  (map #(findn 4 %) lines))

(defn func2 "Teil 2" [lines]
  (map #(findn 14 %) lines))

;;(process-input "resources/day06" func)
;;(process-input "resources/day06" func2)
