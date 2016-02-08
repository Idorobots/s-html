(ns s-html.utils
  "Useful utilities for handling S-HTML datastructures."
  (:require [clojure.set :refer [difference union]]))

(defn- settify [value]
  (cond (nil? value) #{}
        (coll? value) (into #{} value)
        :else #{value}))

(defn- union-values [a b]
  (-> (union (settify a)
             (settify b))
      seq
      sort))

(defn- add-attr [{:keys [attrs] :as tag} attribute value]
  (update-in tag [:attrs attribute]
             #(union-values % value)))

(defn- diff-values [a b]
  (-> (difference (settify a)
                  (settify b))
      seq
      sort))

(defn- dissoc-in [m [k & ks]]
  (if ks
    (assoc m k (dissoc-in (m k) ks))
    (dissoc m k)))

(defn- remove-attr [tag attribute value]
  (let [a (get-in tag [:attrs attribute])
        d (diff-values a value)]
    (if (empty? d)
      (dissoc-in tag [:attrs attribute])
      (assoc-in tag [:attrs attribute] d))))

(defn add-class
  "Adds all classes in `cs` to the `tag`'s `:class` attribute."
  [tag & cs]
  (add-attr tag :class cs))

(defn remove-class
  "Removes all classes in `cs` from `tag`'s `:class` attribute."
  [tag & cs]
  (remove-attr tag :class cs))
