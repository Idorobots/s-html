(ns s-html.tags.rss
  "RSS 2.0 tags."
  (:require [s-html.tags :refer [deftags tag xml]]
            [s-html.utils :refer [add-attr]]))

;; Person constructs:
(deftags [author managingEditor webMaster])

;; Container Elements:
(deftags [channel item])

(defn rss
  "Constructs an RSS 2.0 rss element with given `attributes` and `contents`."
  [& attrs-or-contents]
  (add-attr (apply tag 'rss attrs-or-contents)
            :version "2.0"))

(defn rss*
  "Constructs an RSS 2.0 rss element with preceding XML declaration. See [[rss]] for details."
  [& attrs-or-contents]
  [(xml) (apply rss attrs-or-contents)])

;; Metadata Elements
(deftags [enclosure category cloud comments copyright description docs
          generator guid image language lastBuildDate link pubDate rating
          skipHours skipDays source textInput title ttl])
