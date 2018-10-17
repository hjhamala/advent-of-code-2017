(ns day-15.core)


(defn calculate-value
  (^long [^long seed ^long prev-value]
   (rem (* seed prev-value) 2147483647)))

(defn calculate-value-with-cond
  [^long seed ^long prev-value ^long multiple-of]
   (loop [p prev-value]
     (cond
       (= 0 (rem (calculate-value seed p) multiple-of)) (calculate-value seed p)
       :else (recur (calculate-value seed p)))))

(defn match-right-16
  [^long x ^long y]
  (let [mask 2r00000000000000001111111111111111]
    (= (bit-and mask x) (bit-and mask y))))

(defn calculate-rounds
  [rounds gen-a-seed gen-b-seed gen-a-value gen-b-value]
  (loop [r rounds matches 0 gen-a-v gen-a-value gen-b-v gen-b-value]
    (cond
      (= r 0) matches
      :else (let [val-a (calculate-value gen-a-seed gen-a-v)
                  val-b (calculate-value gen-b-seed gen-b-v)]
              ;(println [val-a val-b])
              (recur (dec r) (if (match-right-16 val-a val-b)
                               (inc matches)
                               matches) val-a val-b)))))

(defn calculate-rounds-2
  [rounds gen-a-seed gen-b-seed gen-a-value gen-b-value]
  (loop [r rounds matches 0 gen-a-v gen-a-value gen-b-v gen-b-value]
    (cond
      (= r 0) matches
      :else (let [val-a (calculate-value-with-cond gen-a-seed gen-a-v 4)
                  val-b (calculate-value-with-cond gen-b-seed gen-b-v 8)]
              ;(println [val-a val-b])
              (recur (dec r) (if (match-right-16 val-a val-b)
                               (inc matches)
                               matches) val-a val-b)))))

(defn solve-example
  []
  (calculate-rounds 5 16807 48271 65 8921))

(defn solve-example-part-2
  []
  (calculate-rounds-2 5000000 16807 48271 65 8921))

(defn solve-part-1
  []
  (calculate-rounds 40000000 16807 48271 618 814))

(defn solve-part-2
  []
  (calculate-rounds-2 5000000 16807 48271 618 814))