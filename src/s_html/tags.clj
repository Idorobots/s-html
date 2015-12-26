(ns s-html.tags)

(defn tag [name & attrs-or-contents]
  (cond (nil? attrs-or-contents)
        {:type ::tag
         :tag name
         :attrs {}
         :contents nil}

        (map? (first attrs-or-contents))
        {:type ::tag
         :tag name
         :attrs (first attrs-or-contents)
         :contents (rest attrs-or-contents)}

        :else
        {:type ::tag
         :tag name
         :attrs {}
         :contents attrs-or-contents}))

(defn empty-tag
  ([name attrs]
   (tag name attrs))

  ([name]
   (tag name)))
