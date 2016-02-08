(ns s-html.utils
  "Useful utilities for handling S-HTML datastructures."
  (:require [clojure.set :refer [difference intersection union]]))

(defn- settify [value]
  (cond (nil? value) #{}
        (coll? value) (into #{} value)
        :else #{value}))

(defn- union-values [a b]
  (-> (union (settify a)
             (settify b))
      seq
      sort))

(defn add-attr
  "Adds all `values` to the `tag`'s `attribute`."
  [tag attribute & values]
  (update-in tag [:attrs attribute]
             (partial union-values values)))

(defn- diff-values [a b]
  (-> (difference (settify a)
                  (settify b))
      seq
      sort))

(defn- dissoc-in [m [k & ks]]
  (if ks
    (assoc m k (dissoc-in (m k) ks))
    (dissoc m k)))

(defn remove-attr
  "Removes all `values` from `tag`'s `attribute`."
  [tag attribute & values]
  (let [a (get-in tag [:attrs attribute])
        d (diff-values a values)]
    (if (empty? d)
      (dissoc-in tag [:attrs attribute])
      (assoc-in tag [:attrs attribute] d))))

(defn has-attr
  "Checks wether `tag`'s `attribute` contains all `values`."
  [tag attribute & values]
  (let [a (get-in tag [:attrs attribute])
        sv (settify values)]
    (= (intersection (settify a) sv)
       sv)))

(defn add-class
  "Adds all classes in `cs` to the `tag`'s `:class` attribute."
  [tag & cs]
  (apply add-attr tag :class cs))

(defn remove-class
  "Removes all classes in `cs` from `tag`'s `:class` attribute."
  [tag & cs]
  (apply remove-attr tag :class cs))

(defn has-class
  "Checks wether `tag`'s `:class` attribute contains all classes in `cs`."
  [tag & cs]
  (apply has-attr tag :class cs))
