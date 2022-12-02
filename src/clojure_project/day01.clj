(ns clojure-project.day01
  (:use clojure-project.core))

(defn func01
  [lines]
  (->> (map #(if (clojure.string/blank? %) :sep (Integer/parseInt %)) lines)
       (split-by-whitout #{:sep})
       (map #(apply + %))
       (sort >)
       (take 3)
       (apply +)))

(process-input "resources/day01" func01)
