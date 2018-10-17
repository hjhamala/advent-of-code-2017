(ns day-16.core
  (:import (clojure.lang PersistentVector)))

(defn spin
  [^long i  program ]
  (into [] (concat (take-last i program) (drop-last i program))))

(defn exchange
  ([^long from ^long to  program]
    ;(println  "exchange"  from to)
   (let [f (get program from)
         t (get program to)]
     (-> program
         (assoc from t)
         (assoc to f)))))

(defn partner
  [^Character a ^Character b ^PersistentVector program]
   (let [f (.indexOf program a)
         t (.indexOf program b)]
     (exchange f t program)))



(defn str-to-command
  [command-str]
  (case (first command-str)
    \s (partial spin (Integer/parseInt (subs command-str 1)))
    \p (partial partner (first (subs command-str 1 2)) (first (subs command-str 3 4)))
    \x (partial exchange (first (-> (subs command-str 1)
                                    (clojure.string/split #"/")
                                    (#(map (fn [x] (Integer/parseInt x)) %))))
                (second (-> (subs command-str 1)
                           (clojure.string/split #"/")
                           (#(map (fn [x] (Integer/parseInt x)) %)))))))

(defn example
  []
  (let [inp ["s1" "x3/4" "pe/b"]
        commands (map str-to-command inp)]
    (reduce (fn [programs command] (command programs)) (vec "abcde") commands )
    ))

(defn parse
  []
  (-> (slurp "input.txt")
      (clojure.string/split #",")))

(defn solve
  [starting commands]
  (-> (reduce (fn [programs command] (command programs)) starting commands)))

(defn solve-day-1
  []
  (let [commands (map str-to-command (parse))]
    (time (clojure.string/join (solve (vec "abcdefghijklmnop") commands)))))

(defn solve-day-2
  []
  (let [commands (map str-to-command (parse))]
    (loop [dance (vec "abcdefghijklmnop") times 1000000]
      (cond
        (= 0 times) dance
        :else (recur (solve dance commands) (dec times))))))