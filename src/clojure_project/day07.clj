(ns clojure-project.day07
  (:use clojure-project.core))

(defn fs-reduce [fs]
  (reduce
   (fn [s [k v]]
     (if (map? v)
       (let [dir (fs-reduce v)]
         (-> s
             (update :sum + (:sum dir))
             (update :puzzle #(assoc (merge % (:puzzle dir)) k (:sum dir)))
             ))
       (update s :sum (partial + v))
       ))
   {:sum 0, :puzzle {}}
   fs))

(defn func [lines]
  (->> lines
       (map #(clojure.string/split % #" "))
       (reduce
        (fn [s [c0 c1 c2]]
          (cond
            (and (= c0 "$") (= c1 "cd") (= c2 "/")) (assoc s :pwd '())
            (and (= c0 "$") (= c1 "cd") (= c2 "..")) (update s :pwd rest)
            (and (= c0 "$") (= c1 "cd")) (update s :pwd (partial cons c2))
            (and (= c0 "$") (= c1 "ls")) s
            (and (= c0 "dir")) s
            :default (assoc-in s (->> s (:pwd) (cons c1) (reverse) (cons :fs) (vec)) (Integer/parseInt c0))
            ))
        {:pwd '() :fs {}})
       (:fs)
       (fs-reduce)
       ((fn [{:keys [sum puzzle]}]
          (filter
           (fn [[k v]] (>= 40000000 (- sum v)))
           (assoc puzzle "/" sum))))
       (sort-by last)
       (first)
       (second)
       ))


((fn [[a b c]] c) [1 2])

(process-input "resources/day07_test" func)
