(ns day-18.core)


(def example-source
  ["set a 1"
   "add a 2"
   "mul a a"
   "mod a 5"
   "snd a"
   "set a 0"
   "rcv a"
   "jgz a -1"
   "set a 1 "
   "jgz a -2"])

(defn get-value
  [value memory]
  (println ["get-value" value memory])

  (if (integer? value)
    value
    (get memory value 0)))


(defn move-to-next-command
  [memory]
  (println "move-to-next" memory)
  (update memory :next-command inc))

(defn snd
  [address memory]
  (println ["snd" address memory])
  (-> memory
      (assoc :last-played (get-value address memory))
      move-to-next-command))

(defn c-set
  [address value memory]
  (println ["set" address value memory])
    (-> memory
        (assoc address (get-value value memory))
        move-to-next-command))

(defn add
  [address value memory]
  (println ["add" address value memory])
  (-> memory
    (update  address #(if (nil? %)
                        (get-value value memory)
                        (+ % (get-value value memory))))
      move-to-next-command))

(defn mul
  [address value memory]
  (println ["mul" address value memory])
  (-> memory
      (update address #(if (nil? %)
                         0
                         (* % (get-value value memory))))
      move-to-next-command))

(defn c-mod
  [address value memory]
  (println ["mod" address value memory])
  (-> memory
      (update  address #(mod % (get-value value memory)))
      move-to-next-command))

(defn rcv
  [address memory]
  (println ["rcv" address  memory])
  (if (= 0 (get-value address memory))
    (move-to-next-command memory)
    (assoc memory :next-command -1)))

(defn jgz
  [address value memory]
  (println ["jgz" address value memory])
  (if (> (get-value address memory) 0)
    (update memory :next-command #(+ %(get-value value memory)))
    (move-to-next-command memory)))

(defn convert-to-int
  [x]
  (try (Integer/parseInt x)
       (catch Exception e x)))

(defn map-to-commands
  [entry]
  (let [[operation operand-1 operand-2] (clojure.string/split entry #" ")
        operand-1 (convert-to-int operand-1)
        operand-2 (convert-to-int operand-2)]
    (case operation
      "snd" (partial snd operand-1)
      "set" (partial c-set operand-1 operand-2)
      "add" (partial add operand-1 operand-2)
      "mul" (partial mul operand-1 operand-2)
      "mod" (partial c-mod operand-1 operand-2)
      "rcv" (partial rcv operand-1)
      "jgz" (partial jgz operand-1 operand-2))))

(defn solve
  [commands]
  (loop [memory {:next-command 0}]
    (cond
      (= -1 (:next-command memory)) memory
      :else (recur ((get commands (:next-command memory)) memory)))))

(defn solve-example
  []
  (solve (into {} (map-indexed (fn [idx itm] [idx itm])  (map map-to-commands example-source)))))

(defn solve-part-1
  []
  (-> (into {} (map-indexed (fn [idx itm] [idx itm])
                            (->> (slurp "input.txt")
                                (clojure.string/split-lines)
                                (map map-to-commands))))
      solve))