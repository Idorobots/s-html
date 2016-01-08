(ns s-html.print-test
  (:require [clojure.test :refer :all]
            [s-html.tags :as t]
            [s-html.print :refer [html->str]]))

(deftest can-stringify-html
  (is (= (html->str nil) ""))
  (is (= (html->str (list)) ""))
  (is (= (html->str (t/html)) "<html></html>"))
  (is (= (html->str (t/doctype :html)) "<!DOCTYPE html>"))
  (is (= (html->str (t/xml)) "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>"))
  (is (= (html->str (list (t/doctype :html)
                          (t/html)))
         "<!DOCTYPE html><html></html>"))
  (is (= (html->str (list (t/xml)
                          (t/doctype :xhtml)
                          (t/html)))
         "<?xml version=\"1.0\" encoding=\"UTF-8\" ?><!DOCTYPE html><html></html>"))
  (is (= (html->str (t/xml {:version "0.9"}))
         "<?xml version=\"0.9\" encoding=\"UTF-8\" ?>"))
  (is (= (html->str (t/div {:class :container}))
         "<div class=\"container\"></div>"))
  (is (= (html->str (t/div (t/a {:href "#"})))
         "<div><a href=\"#\"></a></div>"))
  (is (= (html->str (t/div "Test"))
         "<div>Test</div>"))
  (is (= (html->str (t/span {:class '(:a :b)}))
         "<span class=\"a b\"></span>")))

(deftest void-tags-are-properly-terminated
  (is (= (html->str (t/br)) "<br />"))
  (is (= (html->str (t/img {:src "test.png"}))
         "<img src=\"test.png\" />")))

(deftest seqs-are-properly-rendered
  (is (= (html->str (t/div (list (t/h1 "Hi!") (t/h2 "Hi!"))))
         "<div><h1>Hi!</h1><h2>Hi!</h2></div>"))
  (is (= (html->str (t/div [(t/h1 "Hi!") (t/h2 "Hi!")]))
         "<div><h1>Hi!</h1><h2>Hi!</h2></div>"))
  (is (= (html->str (t/div [(t/h1 "Hi!") (t/h2 "Hi!")] (t/h3 "Hi!")))
         "<div><h1>Hi!</h1><h2>Hi!</h2><h3>Hi!</h3></div>"))
  (is (= (html->str (t/div (map #(% "Hi!") [t/h1 t/h2])))
         "<div><h1>Hi!</h1><h2>Hi!</h2></div>")))

(deftest odd-numbered-seqs-properly-work
  ;; NOTE Courtesy of Clojure: ((fn [{:keys [k]}] k) [1 2])) works properly,
  ;; NOTE while ((fn [{:keys [k]}] k) [1 2 3]) does not, so using destructuring
  ;; NOTE when processing nested datastructures is a no-no.
  (is (= (html->str (t/div (map #(% "Hi!") [t/h1 t/h2 t/h3])))
         "<div><h1>Hi!</h1><h2>Hi!</h2><h3>Hi!</h3></div>"))
  (is (= (html->str (t/div [(t/h1 "Hi!") (t/h2 "Hi!") (t/h3 "Hi!")]))
         "<div><h1>Hi!</h1><h2>Hi!</h2><h3>Hi!</h3></div>")))

(deftest attribute-values-can-be-sequential
  (is (= (html->str (t/div {:class [:a]}))
         "<div class=\"a\"></div>"))
  (is (= (html->str (t/div {:class [:a :b]}))
         "<div class=\"a b\"></div>"))
  (is (= (html->str (t/div {:class (list :a :b)}))
         "<div class=\"a b\"></div>"))
  (is (= (html->str (t/div {:class (map #(name %) [:a :b :c])}))
         "<div class=\"a b c\"></div>")))
