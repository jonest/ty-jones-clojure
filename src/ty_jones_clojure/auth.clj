(ns ty-jones-clojure.auth
  (:require [clj-http.client :as client]
            [environ.core :refer [env]]
            [ring.util.codec :as rcodec]
            [compojure.route :as route]
            [noir.response :as response]
            [cheshire.core :as parse]
            [compojure.core :refer [defroutes GET PUT POST DELETE ANY]]))

(def GOOGLE_CLIENT_ID
  (env :google-client-id))

(def GOOGLE_CLIENT_SECRET
  (env :google-client-secret))

(def GOOGLE_REDIRECT_URI
  (env :google-redirect-uri))

(def google-user
  (atom {:google-id "" :google-name "" :google-email ""}))

(def google-signin-redirect-uri (str "https://accounts.google.com/o/oauth2/auth?"
    "scope=email%20profile&"
    "redirect_uri=" (rcodec/url-encode GOOGLE_REDIRECT_URI) "&"
    "response_type=code&"
    "client_id=" (rcodec/url-encode GOOGLE_CLIENT_ID) "&"
    "approval_prompt=force"))

(defn google [params]
 (let [access-token-response (client/post "https://accounts.google.com/o/oauth2/token"
                                          {:form-params {:code (get params :code)
                                           :client_id GOOGLE_CLIENT_ID
                                           :client_secret GOOGLE_CLIENT_SECRET
                                           :redirect_uri GOOGLE_REDIRECT_URI
                                           :grant_type "authorization_code"}})
       user-details (parse/parse-string (:body (client/get (str "https://www.googleapis.com/oauth2/v2/userinfo?access_token="
       (get (parse/parse-string (:body access-token-response)) "access_token")))))]
       (swap! google-user #(assoc % :google-id %2 :google-name %3 :google-email %4) (get user-details "id") (get user-details "name") (get user-details "email"))))

(defroutes routes
   (GET "/auth/google" {params :params}
   {:status 200
    :headers {"Content-Type" "text/plain"}
    :body (pr-str (google params))})
   (GET "/google/signin" []
      (response/redirect google-signin-redirect-uri)))
