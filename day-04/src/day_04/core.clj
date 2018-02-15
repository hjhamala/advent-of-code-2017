(ns day-04.core
  (:gen-class))

(defn get-seed
  [filename]
  (-> (slurp (clojure.java.io/resource filename))
      (clojure.string/split-lines)))

(defn inc-or-one
  [x]
  (if (nil? x)
    1
    (inc x)))

(defn filter-not-one
  [c]
  (filter (fn [m]
            (every? #(= 1 %) (vals m))) c))

(defn count-and-index
  [s]
  (map #(let [s (clojure.string/split % #" ")]
          (reduce (fn [c v] (update c v inc-or-one)) {} s)) s))

(defn solve
  []
  (-> (get-seed "seed")
      count-and-index
      filter-not-one
      count))

(defn count-if-true
  [s]
  (-> (filter true? s)
      count))

(defn check-if-distinct?
  [s]
  (map #(apply distinct? %) s))

(defn count-and-split-to-chars
  [m]
  (map (fn [row]
         (let [words (clojure.string/split row #" ")]
           (map (fn [word]
                  (reduce (fn [acc v]
                            (update acc v inc-or-one)) {} word)) words)))m))

(defn solve-part-2
  []
  (-> (get-seed "seed")
       count-and-split-to-chars
       check-if-distinct?
       count-if-true))

(defn -main
  [& args]

  ;(solve original)
  )