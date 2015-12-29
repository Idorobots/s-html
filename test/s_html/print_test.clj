(ns s-html.print-test
  (:require [clojure.test :refer :all]
            [s-html.tags :as t]
            [s-html.print :refer [html->str]]))

(deftest can-stringify-html
  (is (= (html->str nil) ""))
  (is (= (html->str (list)) ""))
  (is (= (html->str (t/html)) "<html></html>"))
  (is (= (html->str (t/doctype :html)) "<!DOCTYPE html>"))
  (is (= (html->str (list (t/doctype :html)
                          (t/html)))
         "<!DOCTYPE html><html></html>"))
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
