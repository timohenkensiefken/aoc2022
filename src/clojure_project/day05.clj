(ns clojure-project.day05
  (:use clojure-project.core))

(defn parse-stack [stack']
  (let [stack (butlast stack')
        stack-name (take-nth 4 (rest (first (take-last 1 stack'))))]
    (->> (map #(take-nth 4 (rest %)) stack)
         (apply map list)
         (map (fn [stack] (filter #(not (#{\space} %)) stack)))
         (zipmap stack-name))))

(defn parse-operations [ops]
  (->> (map #(clojure.string/split % #" ") ops)
       (map rest)
       (map (partial take-nth 2))
       (map (fn [[n o d]] [(Integer/parseInt n) (first o) (first d)]))
       (map (fn [[n o d]]
              (fn [stacks]
                (let [stacko (stacks o)
                      stackd (stacks d)]
                  (assoc stacks o (drop n stacko) d (concat (take n stacko) stackd))))))
       (reverse)
       (apply comp)))

(defn func [lines]
  (let [stack (parse-stack (take-while #(not (clojure.string/blank? %)) lines))
        operations (parse-operations (rest (drop-while #(not (clojure.string/blank? %)) lines)))]
    (->> stack
         (operations)
         (vals)
         (map first)
         (apply str)
         )))

(process-input "resources/day05" func)
