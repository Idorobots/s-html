(ns s-html.tags.atom
  "RFC4287 Atom feed tags."
  (:refer-clojure :exclude [name])
  (:require [s-html.tags :refer [deftags tag xml]]
            [s-html.utils :refer [add-attr]]))

;; Person Constructs
(deftags [email name uri])

;; Container Elements
(deftags [content entry])

(defn feed
  "Constructs an RFC4287 Atom feed element with given `attributes` and `contents`."
  [& attrs-or-contents]
  (add-attr (apply tag 'feed attrs-or-contents)
            :xmlns "http://www.w3.org/2005/Atom"))

(defn feed*
  "Constructs an Atom feed element with preceding XML declaration. See [[feed]] for details."
  [& attrs-or-contents]
  [(xml) (apply feed attrs-or-contents)])

;; Metadata Elements
(deftags [author category contributor generator icon id link logo
          published rights source subtitle summary title updated])
