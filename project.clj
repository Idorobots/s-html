(defproject s-html "1.0.2"
  :description "A library of composable HTML generators."
  :url "https://github.com/Idorobots/s-html"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]]
  :profiles {:1.6 {:dependencies [[org.clojure/clojure "1.6.0"]]}
             :1.7 {:dependencies [[org.clojure/clojure "1.7.0"]]}}
  :plugins [[jonase/eastwood "0.2.4"]
            [lein-ancient "0.6.10"]
            [lein-cloverage "1.0.9"]
            [lein-codox "0.10.3"]]
  :codox {:metadata {:doc/format :markdown}
          :source-uri "https://github.com/Idorobots/s-html/blob/{version}/{filepath}#L{line}"})
