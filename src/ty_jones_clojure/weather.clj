(ns ty-jones-clojure.weather
  (:require [clj-http.client :as client]
            [environ.core :refer [env]]
            [compojure.route :as route]
            [compojure.core :refer [defroutes GET PUT POST DELETE ANY]]
            [ty-jones-clojure.formatters :refer :all]))

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

 (defroutes routes
    (GET "/weather/:zip/friendly" [zip]
         ((return-weather format-friendly) zip))
    (GET "/weather/:zip/temp" [zip]
         ((return-weather format-temp) zip))
    (GET "/weather/:zip/speed" [zip]
         ((return-weather format-speed) zip)))
