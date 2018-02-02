(ns day-03.core
  (:import (clojure.lang Indexed ISeq)))

(deftype Circle [start cycle last from-c from-f-to-m]
  ISeq
  (first [this]
    {:start start :cycle cycle :last last :from-c from-c :from-f-to-m from-f-to-m})
  (next [this]
    (.more this))
  (more [this]
    (let [new-start (inc last)
          new-cycle (+ cycle 8)
          new-last (- (+ new-start new-cycle) 1)
          new-from-c (inc from-c)
          new-from-f-to-m (if (> new-from-c 0) (dec new-from-c) 0)]
      (Circle. new-start new-cycle new-last new-from-c new-from-f-to-m)))
  (seq [this] this))

(defmethod print-method Circle [circle ^java.io.Writer w]
  (.write w (str "#Circle")))

(deftype Heiluri [s max dir-fn]
  Indexed
  (nth [this index]
    (loop [t this i index]
      (cond
        (= 0 i) (first t)
        :else (recur (.more t) (dec i)))))
  ISeq
  (first [this]
    {:s s :m max})
  (next [this]
    (.more this))
  (more [this]
    (cond
      (= s max) (Heiluri. (dec s) max dec)
      (= s 0) (Heiluri. (inc s) max inc)
      :else (Heiluri. (dir-fn s) max dir-fn)))
  (seq [this] this))

(defmethod print-method Heiluri [this ^java.io.Writer w]
  (.write w (str "#Heiluri:" (.s this) " " (.max this))))

(defn determine-circle
  [x]
  (loop [c (Circle. 1 0 1 0 0)]
    (cond (<= x (-> (first c) :last)) (first c)
          :else (recur (rest c)))))

(defn walk-from-start
  [start x from-f]
  (loop [t (- x start) h (Heiluri. from-f (inc from-f) dec)]
    (cond (= t 0) (first h)
          :else (recur (dec t) (rest h)))))

(defn solve-part-one
  []
  (let [circle (determine-circle 277678)
        walk-result (walk-from-start (:start circle) 277678 (:from-f-to-m circle))]
    (+ (:from-c circle) (:s walk-result))))



