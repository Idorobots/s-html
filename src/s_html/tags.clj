(ns s-html.tags)

(defn doctype? [{:keys [type]}]
  (= type ::doctype))

(defn doctype [type]
  {:type ::doctype
   :doctype type})

(defn tag? [{:keys [type]}]
  (= type ::tag))

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

(defn make-tag [constructor name]
  `(def ~name (partial ~constructor '~name)))

(defmacro deftag [name]
  (make-tag tag name))

(defmacro defemptytag [name]
  (make-tag empty-tag name))

(defmacro deftags [tags]
  `(do ~@(map (partial make-tag tag) tags)))

(defmacro defemptytags [tags]
  `(do ~@(map (partial make-tag empty-tag) tags)))

(deftags
  [a abbr acronym address applet b bdo big blockquote body body button caption center cite code
   colgroup dd del dfn dir div dl dt em fieldset font form frame frameset h1 h2 h3 h4 h5 h6 head
   html i iframe ins kbd label legend li menu noframe noscript object ol optgroup option p pre q
   s samp script select small span strike strong style sub sup table tbody td textarea tfoot th
   thead title tr tt u var])

(defemptytags
  [area base basefont br col hr img input isindex link meta param])
