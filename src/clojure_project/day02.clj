(ns clojure-project.day02
  (:use clojure-project.core))

(def scores {[:rock :draw] 4
             [:rock :victory] 8
             [:rock :loss] 3
             [:paper :loss] 1
             [:paper :draw] 5
             [:paper :victory] 9
             [:scissor :victory] 7
             [:scissor :loss] 2
             [:scissor :draw] 6})


(def opp-play {\A :rock \B :paper \C :scissor})
(def me-expect {\X :loss \Y :draw \Z :victory})

(defn func02
  [lines]
  (->> (map vec lines)
       (map (fn [[a b c]] [(opp-play a) (me-expect c)]))
       (map scores)
       (apply +)))

(process-input "resources/day02" func02)
