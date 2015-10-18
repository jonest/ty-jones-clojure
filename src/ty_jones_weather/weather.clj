(ns ty-jones-weather.weather
  (:require [clj-http.client :as client]
            [environ.core :refer [env]]))

(defn deep-select [data keywords]
   (map #(get-in data %) keywords))

(defn get-weather [zip formatter]
  (let [query-string (str "http://api.openweathermap.org/data/2.5/weather?zip=" zip ",us&APPID=" (env :apiid) "&units=imperial")]
  (-> (client/get query-string {:as :json})
      (:body)
      (deep-select [[:name] [:wind :speed] [:main :temp]])
      (formatter))))

(defn return-weather [formatter]
  (fn [zip]
  {:status 200
   :headers {"Content-Type" "text/plain"}
   :body (pr-str (get-weather zip formatter))}))
