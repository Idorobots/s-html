(ns s-html.tags-test
  (:require [clojure.test :refer :all]
            [s-html.tags :as t]))

(deftest subelements-arent-attributes
  (let [inner (t/a {:href "#"})
        {:keys [attrs contents]} (t/div inner)]
    (is (= attrs {}))
    (is (= contents (list inner)))))

(deftest having-attributes-doesnt-affect-contents
  (is (= (:contents (t/i))
         (:contents (t/i {:class :class}))))
  (is (= (:contents (t/i "foo"))
         (:contents (t/i {:class :class} "foo")))))
