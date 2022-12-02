(ns clojure-project.core)

(defn process-input
  [file f]
  (with-open [rdr (clojure.java.io/reader file)]
    (prn (f (line-seq rdr)))))

; https://clojuredocs.org/clojure.core/split-with#example-5e48288ce4b0ca44402ef839
(defn split-by [pred coll]
  (lazy-seq
    (when-let [s (seq coll)]
      (let [[xs ys] (split-with pred s)]
        (if (seq xs)
          (cons xs (split-by pred ys))
          (let [!pred (complement pred)
                skip (take-while !pred s)
                others (drop-while !pred s)
                [xs ys] (split-with pred others)]
            (cons (concat skip xs)
                  (split-by pred ys))))))))

(defn split-by-whitout
  [pred coll]
  (map (fn [ls] (filter #(not (pred %)) ls)) (split-by #(not (pred %)) coll)))
