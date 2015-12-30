(ns s-html.tags)

(defn doctype? [obj]
  (and (map? obj)
       (-> obj :type (= ::doctype))))

(defn doctype [type]
  {:type ::doctype
   :doctype type})

(defn void-tag? [obj]
  (and (map? obj)
       (-> obj :type (= ::void-tag))))

(defn tag? [obj]
  (and (map? obj)
       (or (-> obj :type (= ::tag))
           (void-tag? obj))))

(defn tag [name & attrs-or-contents]
  (let [f (first attrs-or-contents)]
    (cond (nil? attrs-or-contents)
          {:type ::tag
           :tag name
           :attrs {}
           :contents nil}

          (and (map? f) (not (tag? f)))
          {:type ::tag
           :tag name
           :attrs f
           :contents (rest attrs-or-contents)}

          :else
          {:type ::tag
           :tag name
           :attrs {}
           :contents attrs-or-contents})))

(defn void-tag
  ([name attrs]
   {:type ::void-tag
    :tag name
    :attrs attrs})

  ([name]
   (void-tag name {})))

(defn make-tag [constructor name]
  `(def ~name (partial ~constructor '~name)))

(defmacro deftag [name]
  (make-tag tag name))

(defmacro defvoidtag [name]
  (make-tag void-tag name))

(defmacro deftags [tags]
  `(do ~@(map (partial make-tag tag) tags)))

(defmacro defvoidtags [tags]
  `(do ~@(map (partial make-tag void-tag) tags)))

;; HTML tags:
(deftags
  [a abbr acronym address applet b bdo big blockquote body body button caption center cite code
   colgroup dd del dfn dir div dl dt em fieldset font form frame frameset h1 h2 h3 h4 h5 h6 head
   html i iframe ins kbd label legend li menu noframe noscript object ol optgroup option p pre q
   s samp script select small span strike strong style sub sup table tbody td textarea tfoot th
   thead title tr tt u var])

(defvoidtags
  [area base basefont br col hr img input isindex link meta param])

;; HTML5 tags:
(deftags
  [article aside audio bdi canvas datalist details dialog figcaption figure footer header main
   mark menuitem meter nav output progress rp rt ruby section summary svg time video])

(defvoidtags
  [embed keygen source track wbr])
