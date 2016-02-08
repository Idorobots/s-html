(ns s-html.utils
  "Useful utilities for handling S-HTML datastructures.")

(defn- join-values [a b]
  (cond (nil? a) b
        (nil? b) a
        (and (sequential? a) (sequential? b)) (concat a b)
        (sequential? a) (conj a b)
        (sequential? b) (conj b a)
        :else (list a b)))

(defn- add-attr [{:keys [attrs] :as tag} attribute value]
  (update-in tag [:attrs attribute]
             #(join-values % value)))

(defn add-class
  "Adds all classes in `cs` to the `tag`'s `:class` attribute."
  [tag & cs]
  (add-attr tag :class cs))
