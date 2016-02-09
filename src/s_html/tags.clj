(ns s-html.tags
  "Generic tag builders.")

(defn xml?
  "Checks wether an object is an XML declaration."
  [obj]
  (and (map? obj)
       (= (:type obj) ::xml)))

(defn xml
  "Constructs an XML declaration of given `encoding` and `version`. Defaults to `UTF-8` and version `1.0`."
  ([] (xml {}))
  ([{:keys [encoding version] :as attrs}]
   {:type ::xml
    :attrs (dissoc attrs
                   :encoding
                   :version)
    :encoding (or encoding "UTF-8")
    :version (or version "1.0")}))

(defn doctype?
  "Checks wether an object is a DOCTYPE declaration."
  [obj]
  (and (map? obj)
       (= (:type obj) ::doctype)))

(defn doctype
  "Constructs a DOCTYPE declaration of given `type`."
  [type]
  {:type ::doctype
   :doctype type})

(defn void-tag?
  "Checks wether an objects is a void HTML tag."
  [obj]
  (and (map? obj)
       (= (:type obj) ::void-tag)))

(defn tag?
  "Checks wether an object is an HTML tag."
  [obj]
  (and (map? obj)
       (or (= (:type obj) ::tag)
           (void-tag? obj))))

(defn tag
  "Creates an HTML tag with optional `attributes` and `contents`. Attributes are a non-[[tag?]] map passed as the first argument."
  [name & attrs-or-contents]
  (let [f (first attrs-or-contents)]
    (cond (nil? attrs-or-contents)
          {:type ::tag
           :tag name
           :attrs {}
           :contents '()}

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
  "Creates a void HTML tag - a tag that cannot have any contents - with optional `attributes`."
  ([name attrs]
   {:type ::void-tag
    :tag name
    :attrs attrs})

  ([name]
   (void-tag name {})))

(defn- make-tag [constructor docstring name]
  `(def ~name
     ~docstring
     (partial ~constructor '~name)))

(defmacro ^:private make-docstring [constructor name]
  `(format "Creates an HTML `%s` tag. Accepts the same arguments as [[%s]] except `name`."
           ~name
           (-> ~constructor var clojure.core/meta :name str)))

(defmacro deftag
  "A helper macro for defining a single HTML tag."
  [name]
  (make-tag tag
            (make-docstring tag name)
            name))

(defmacro defvoidtag
  "A helper macro for defining a single HTML void tag."
  [name]
  (make-tag void-tag
            (make-docstring void-tag name)
            name))

(defmacro deftags
  "A helper macro for defining several HTML tags."
  [tags]
  `(do ~@(map (fn [name]
                (make-tag tag
                          (make-docstring tag name)
                          name))
              tags)))

(defmacro defvoidtags
  "A helper macro for defining several HTML void tags."
  [tags]
  `(do ~@(map (fn [name]
                (make-tag void-tag
                          (make-docstring void-tag name)
                          name))
              tags)))
