(ns pub.core
  (:require [compojure.api.sweet :refer :all]
            [ring.util.http-response :refer :all]
            [aleph.http :as http]
            [schema.core :as s])
  (:gen-class))

(def db (atom {}))

(defn add-key-to-email [email key]
  (let [current-keys (or (get @db email) [])
        
        ]
    (swap! db merge {email (conj current-keys key)})
    )
  )

(defapi app
  {:swagger
   {:ui "/docs"
    :spec "/swagger.json"
    :data {:info {:title "Some cool name"
                  :description "Maybe store some keys"}
           :tags [{:name "api", :description "some apis"}]}}}
  (context "api" []
           :tags ["api"]
           (POST "/store" []
                :return {:message String}
                :body [key :- String
                       email :- String]
                :summary "Says hello"
                (swap! db assoc email key))))

(defn -main
  [& args]
  (http/start-server (var app) {:port 8080}))
