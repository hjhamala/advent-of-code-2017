(ns day-10.core)

(def input [120,93,0,90,5,80,129,74,1,165,204,255,254,2,50,113])

(def example-input (range 5))

(def example-commands [3 4 1 5])

(def elements (into [] (range 256)))

(defn solve
  [elements input]
  (loop [e elements
         i input
         skip 0
         head 0]
    (cond
      (empty? i) [e head]
      :else (do (let [input-f (first i)
                      reversed-part (reverse (take input-f e))
                      rest-from-reversed (drop input-f e)
                      skipped-part (take skip rest-from-reversed)
                      rest-from-skipped (drop skip rest-from-reversed)]
                  ;(println [e i skip input-f reversed-part rest-from-reversed skipped-part rest-from-skipped])
                  (recur (flatten [rest-from-skipped reversed-part skipped-part])
                         (rest i)
                         (inc skip)
                         (+ head skip input-f)))))))

(defn traverse-and-give-two-first
  [[xs c]]
  (loop [xsl xs cl c]
    (cond
      (= 0 cl) xsl
      :else (recur (concat (concat (rest xsl) [(first xsl)])) (dec cl)))))

(defn main
  [input commands]
  (let [skipped (reduce + commands)
        intermediatary (solve input commands)]
    (println [intermediatary skipped])
    (traverse-and-give-two-first (first intermediatary) (second intermediatary))))


  (defn foo
    "I don't do a whole lot."
    [x]
    (println x "Hello, World!"))
