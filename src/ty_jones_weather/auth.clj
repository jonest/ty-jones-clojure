(ns ty-jones-weather.auth
  (:require [clj-http.client :as client]
            [environ.core :refer [env]]
            [compojure.route :as route]
            [compojure.core :refer [defroutes GET PUT POST DELETE ANY]]))

(defroutes routes
   (GET "/auth/google"
     (println "auth/google")))
