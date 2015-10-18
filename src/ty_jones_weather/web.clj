(ns ty-jones-weather.web
  (:require [compojure.core :refer [defroutes GET PUT POST DELETE ANY]]
            [compojure.handler :refer [site]]
            [compojure.route :as route]
            [clojure.java.io :as io]
            [ring.adapter.jetty :as jetty]
            [environ.core :refer [env]]
            [ty-jones-weather.formatters :refer :all]
            [ty-jones-weather.weather :refer [return-weather]]))

(defroutes app
  (GET "/weather/:zip/friendly" [zip]
       ((return-weather format-friendly) zip))
  (GET "/weather/:zip/temp" [zip]
       ((return-weather format-temp) zip))
  (GET "/weather/:zip/speed" [zip]
       ((return-weather format-speed) zip))
  (ANY "*" []
       (route/not-found (slurp (io/resource "404.html")))))

(defn -main [& [port]]
  (let [port (Integer. (or port (env :port) 5000))]
    (jetty/run-jetty (site #'app) {:port port :join? false})))
