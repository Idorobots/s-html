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

(defn- dissoc-in [m [k & ks]]
  (if ks
    (assoc m k (dissoc-in (m k) ks))
    (dissoc m k)))

(defn- remove-attr [tag attribute value]
  (let [a (get-in tag [:attrs attribute])]
    (cond (= value a)
          (dissoc-in tag [:attrs attribute])

          (sequential? a)
          (let [f (filter #(not= value %) a)]
            (if (empty? f)
              (dissoc-in tag [:attrs attribute])
              (assoc-in tag [:attrs attribute] f)))

          :else
          tag)))

(defn add-class
  "Adds all classes in `cs` to the `tag`'s `:class` attribute."
  [tag & cs]
  (add-attr tag :class cs))

(defn remove-class
  "Removes all classes in `cs` from `tag`'s `:class` attribute."
  [tag & cs]
  (reduce #(remove-attr %1 :class %2)
          tag
          cs))
