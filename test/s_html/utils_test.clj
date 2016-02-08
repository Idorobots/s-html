(ns s-html.utils-test
  (:require [clojure.test :refer :all]
            [s-html.tags :refer [i]]
            [s-html.utils :refer [add-class]]))

(deftest can-add-a-class
  (is (= (add-class (i) :hurr)
         (i {:class '(:hurr)})))
  (is (= (add-class (i) :hurr :durr)
         (i {:class '(:hurr :durr)})))
  (is (= (add-class (i {:class :herp}) :hurr)
         (i {:class '(:herp :hurr)})))
  (is (= (add-class (i {:class [:herp :derp]}) :hurr)
         (i {:class [:herp :derp :hurr]})))
  (is (= (add-class (i {:class [:herp :derp]}) :hurr :durr)
         (i {:class '(:herp :derp :hurr :durr)}))))
