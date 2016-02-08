(ns s-html.utils-test
  (:require [clojure.test :refer :all]
            [s-html.tags :refer [i]]
            [s-html.utils :refer [add-class remove-class]]))

(deftest can-add-a-class
  (is (= (add-class (i) :hurr)
         (i {:class '(:hurr)})))
  (is (= (add-class (i) :hurr :durr)
         (i {:class '(:durr :hurr)})))
  (is (= (add-class (i {:class :herp}) :hurr)
         (i {:class '(:herp :hurr)})))
  (is (= (add-class (i {:class [:herp :derp]}) :hurr)
         (i {:class '(:derp :herp :hurr)})))
  (is (= (add-class (i {:class [:herp :derp]}) :hurr :durr)
         (i {:class '(:derp :durr :herp :hurr)}))))

(deftest can-remove-a-class
  (is (= (remove-class (i) :hurr)
         (i)))
  (is (= (remove-class (i) :hurr :durr)
         (i)))
  (is (= (remove-class (i {:class :hurr}) :hurr)
         (i)))
  (is (= (remove-class (i {:class [:hurr :durr]}) :hurr :durr)
         (i)))
  (is (= (remove-class (i {:class [:hurr :durr]}) :hurr)
         (i {:class '(:durr)})))
  (is (= (remove-class (i {:class [:hurr :durr :derp]}) :herp :derp)
         (i {:class '(:durr :hurr)}))))
