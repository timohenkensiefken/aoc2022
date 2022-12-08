(ns clojure-project.day08
  (:use clojure-project.core))

(defn row-visibility [row]
  (->> (mapv vector row (reverse row))
       (reduce (fn [a e] (conj a (mapv max (last a) e))) [[-1 -1]])
       (butlast)
       (apply mapv vector)
       ((fn [[nr r]] [nr (reverse r)]))
       (apply mapv min)
       ))

(defn visibility [forest]
  (let [rf (map row-visibility forest)
        cf (->> forest
                (apply map vector)
                (map row-visibility)
                (apply map vector)
                )]
    (->> (map list rf cf)
         (map (partial apply mapv min))
         )))

(defn func [lines]
  (->> lines
       (map vec)
       (map (fn [r] (mapv #(- (int %) 48) r)))
       ((fn [forest] (map > (flatten forest) (flatten (visibility forest)))))
       (filter identity)
       (count)
       ))

;; Part II

(defn view-east-row [r]
  (->> (mapv #(drop % r) (range 0 (count r)))
       (mapv #(let [split (split-with (partial > (first %)) (rest %))]
                (conj (vec (first split))
                      (first (second split)))))
       (mapv #(filterv identity %))
       (mapv count)
       ))

(def wrap-fn #(comp %1 %2 %1))
(def forest-flip #(map (comp vec reverse) %))
(def forest-transpose #(apply map vector %))
(def view-east (partial map view-east-row))
(def view-west #((wrap-fn forest-flip view-east) %))
(def view-south #((wrap-fn forest-transpose view-east) %))
(def view-north #((wrap-fn forest-transpose (wrap-fn forest-flip view-east)) %))

(defn func2 [lines]
  (->> lines
       (map vec)
       (map (fn [r] (mapv #(- (int %) 48) r)))
       ((fn [forest] [(view-east forest) (view-west forest) (view-south forest) (view-north forest)]))
       (map flatten)
       (apply map *)
       (apply max)
       ))

;(process-input "resources/day08" func)
(process-input "resources/day08" func2)
