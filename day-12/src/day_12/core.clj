(ns day-12.core
  (:require [clojure.string :as string]
            [clojure.set :as set]))

(defn parse-to-lines
  [] (-> (slurp "input.txt")
         (clojure.string/split-lines)))

(defn string->program
  [s]
  (let [[program connections] (clojure.string/split s #"<->")
        connections-to-set (->> (string/split connections #",")
                                (map string/trim)
                                (map keyword)
                                set)]
    {(keyword (string/trim program)) connections-to-set}))

(defn generate-programs-list
  []
  (apply merge (map string->program (parse-to-lines))))

(def example
  {:0 #{:2}
   :1 #{:1}
   :2 #{:0, :3, :4}
   :3 #{:2, :4}
   :4 #{:2, :3, :6}
   :5 #{:6}
   :6 #{:4, :5}}
  )

(defn generate-group
  [programs starting-program]
  (loop [current-program starting-program visited-programs #{} connections-to-check (get programs current-program)]
    (println [current-program visited-programs connections-to-check])
    (cond
      (empty? (set/difference connections-to-check visited-programs)) (conj visited-programs current-program)
      :else (recur (first connections-to-check)
                   (conj visited-programs current-program)
                   (-> (set/union (-> connections-to-check rest set) (get programs (first connections-to-check)))
                       (set/difference #{current-program} visited-programs))))))

(defn solve-part-one
  []
  (count (generate-group (generate-programs-list) :0)))

(defn solve-part-two
  [programs]
  (loop [prg programs groups 0]
    (let [program-group (and (-> prg first first) (generate-group programs (-> prg first first)))]
      (println "Programs left: " (count prg))
      ;println "Programs list: " prg)
      (println "Current program: " (-> prg first first))
      (println "Program group: " program-group)
      ;(println [prg current-program groups program-group])
      (cond (empty? prg) groups
            :else (recur
                    (apply dissoc prg program-group)
                    (inc groups))))))



