(ns ty-jones-clojure.formatters)

(defn format-friendly [[city speed temp]]
  (str "It's " temp " in " city "."))

(defn format-temp [[city speed temp]]
  (str temp))

(defn format-speed [[city speed temp]]
  (str speed))
