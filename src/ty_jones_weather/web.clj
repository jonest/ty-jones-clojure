(ns ty-jones-weather.web
  (:require [compojure.core :refer [defroutes GET PUT POST DELETE ANY]]
            [compojure.handler :refer [site]]
            [compojure.route :as route]
            [clojure.java.io :as io]
            [ring.adapter.jetty :as jetty]
            [environ.core :refer [env]]
            [cheshire.core :refer :all]
            [ty-jones-weather.weather :as weather :refer [routes]]))

(defn read-name [json-name-string]
  (let [{{first :first last :last} :name} (decode json-name-string true)]
    (str "Hello, " first " " last "!")))

(defroutes app
  weather/routes
  (POST "/name" {body :body}
    (read-name (slurp body)))
  (ANY "*" []
       (route/not-found (slurp (io/resource "404.html")))))

(defn -main [& [port]]
  (let [port (Integer. (or port (env :port) 5000))]
    (jetty/run-jetty (site #'app) {:port port :join? false})))
