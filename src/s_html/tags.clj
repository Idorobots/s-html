(ns s-html.tags
  (:refer-clojure :exclude [meta time])) ;; NOTE Redefining these below.

(defn xml? [obj]
  (and (map? obj)
       (= (:type obj) ::xml)))

(defn xml
  ([] (xml {}))
  ([{:keys [encoding version] :as attrs}]
   {:type ::xml
    :attrs (dissoc attrs
                   :encoding
                   :version)
    :encoding (or encoding "UTF-8")
    :version (or version "1.0")}))

(defn doctype? [obj]
  (and (map? obj)
       (= (:type obj) ::doctype)))

(defn doctype [type]
  {:type ::doctype
   :doctype type})

(defn void-tag? [obj]
  (and (map? obj)
       (= (:type obj) ::void-tag)))

(defn tag? [obj]
  (and (map? obj)
       (or (= (:type obj) ::tag)
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
  [a abbr acronym address applet b bdo big blockquote body button caption center cite code
   colgroup dd del dfn dir div dl dt em fieldset font form frame frameset h1 h2 h3 h4 h5 h6 head
   html i iframe ins kbd label legend li menu noframe noscript object ol optgroup option p pre q
   s samp script select small span strike strong style sub sup table tbody td textarea tfoot th
   thead title tr tt u ul var])

(defvoidtags
  [area base basefont br col hr img input isindex link meta param])

;; HTML5 tags:
(deftags
  [article aside audio bdi canvas datalist details dialog figcaption figure footer header main
   mark menuitem meter nav output progress rp rt ruby section summary svg time video])

(defvoidtags
  [embed keygen source track wbr])
