(ns s-html.tags.html
  "HTML4 & HTML5 tags."
  (:refer-clojure :exclude [meta time])
  (:require [s-html.tags :refer [deftags defvoidtags]]))

;; HTML4 tags:
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
