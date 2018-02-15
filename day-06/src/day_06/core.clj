(ns day-06.core
  (:gen-class))

(def input
  [5 1 10 0 1 7 13 14 3 12 8 10 7 12 0 6])

(defn update-index-to-zero
  [xs i]
  (assoc xs i 0))

(defn get-max-key-val
  [xs]
  (let [max (apply max xs)
        map-indexed (map-indexed vector xs)]
    (some #(when (= (second %) max) %) map-indexed)))

(defn distribute-from-index
  [xs i v]
  (loop [lxs xs li i lv v]
    ;(println [lxs li lv])
    (cond
      (= 0 lv) lxs
      (= (count lxs) (inc li)) (recur (update lxs li inc) 0 (dec lv))
      :else (recur (update lxs li inc) (inc li) (dec lv)))))

(defn start-from
  [xs max-index]
  (if (= max-index (dec (count xs))) 0 (inc max-index) ))

(defn distribute
  [xs]
  (let [[max-index max-value] (get-max-key-val xs)]
    ;(println {:max-index max-index :max-value max-value})
    (distribute-from-index (update-index-to-zero xs max-index)  (start-from xs max-index) max-value)))


(defn solve-part-1
  [xs]
  (loop [lxs xs combinations-done #{} redistributions 0]
    ;(println [lxs combinations-done redistributions])
    (cond
      (contains? combinations-done lxs) [redistributions lxs]
      :else (recur (distribute lxs) (conj combinations-done lxs ) (inc redistributions)))))

(defn solve-part-2
  [xs]
  (solve-part-1 (second (solve-part-1 xs))))

(defn -main
  [& args]

  ;(solve original)
  )