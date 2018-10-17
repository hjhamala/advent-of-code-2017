(ns day-17.core
  (:import (clojure.lang PersistentVector)))

(defn solve
  [ numbers times steps ^long current-pos]
  (loop [n numbers current-pos current-pos cnt 1 times times]

    (let [spot (.indexOf n (nth (cycle n) (+ current-pos steps)))]
      (if (=  0 times)
        n
        (recur
          (concat (take (inc spot) n) [cnt] (drop (inc spot) n))
          (long (inc spot))
          (inc cnt)
          (dec times))))))


(defn example
  []
  (let [res (solve [0] 2017 3 0)]
    (nth res (inc (.indexOf res 2017)))))

(defn part-1
  []
  (let [res (solve [0] 2017 355 0)]
    (nth res (inc (.indexOf res 2017)))))