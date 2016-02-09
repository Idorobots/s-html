(ns s-html.tags-test
  (:require [clojure.test :refer :all]
            [s-html.tags :refer [xml?]]
            [s-html.tags.html :as html]
            [s-html.tags.atom :as atom]
            [s-html.tags.rss :as rss]
            [s-html.utils :refer [attr]]))

(deftest subelements-arent-attributes
  (let [inner (html/a {:href "#"})
        {:keys [attrs contents]} (html/div inner)]
    (is (= attrs {}))
    (is (= contents (list inner)))))

(deftest having-attributes-doesnt-affect-contents
  (is (= (:contents (html/i))
         (:contents (html/i {:class :class}))))
  (is (= (:contents (html/i "foo"))
         (:contents (html/i {:class :class} "foo")))))

(deftest atom-feed-is-well-formed
  (is (attr (atom/feed) :xmlns))
  (is (attr (atom/feed {:type "text"}) :xmlns))
  (is (attr (second (atom/feed*)) :xmlns))
  (is (xml? (first (atom/feed*)))))

(deftest rss-feed-is-well-formed
  (is (attr (rss/rss) :version))
  (is (attr (rss/rss {:xmlns "..."}) :version))
  (is (attr (second (rss/rss*)) :version))
  (is (xml? (first (rss/rss*)))))
