(defproject ty-jones-weather "1.0.0-SNAPSHOT"
  :description "A simple weather proxy"
  :url "http://http://ty-jones-weather.herokuapp.com/"
  :license {:name "Eclipse Public License v1.0"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [compojure "1.1.8"]
                 [clj-http "1.1.0"]
                 [ring/ring-jetty-adapter "1.2.2"]
                 [environ "1.0.1"]]
  :min-lein-version "2.0.0"
  :plugins [[lein-environ "1.0.1"]]
  :hooks [environ.leiningen.hooks]
  :uberjar-name "ty-jones-weather.jar"
  :profiles {:production {:env {:production true}}})
