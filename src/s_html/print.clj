(ns s-html.print
  (:require [s-html.tags :refer [doctype? tag? void-tag? xml?]]))

(defn- value->str [val]
  (if (sequential? val)
    (reduce #(str %1 " " %2)
            (map value->str val))
    (name val)))

(defn- attrs->str [attrs]
  (apply str
         (map (fn [[n v]]
                (format " %s=\"%s\"" (name n) (value->str v)))
              (sort (seq attrs)))))

(defn- xml->str [{:keys [attrs encoding version]}]
  (format "<?xml%s%s%s ?>"
          (if version (str " version=\"" version "\"") "")
          (if encoding (str " encoding=\"" encoding "\"") "")
          (attrs->str attrs)))

(defn- doctype->str [{:keys [doctype]}]
  (let [html "<!DOCTYPE html>"]
    (case doctype
      :html html
      :html5 html
      :html4 (format "<!DOCTYPE %s %s %s>\n"
                     "HTML"
                     "PUBLIC \"-//W3C//DTD HTLM 4.01//EN\""
                     "\"http://www.w3.org/TR/html4/strict.dtd\"")

      :xhtml html
      :xhtml5 html
      :xhtml1.1 (format "<!DOCTYPE %s %s \"%s\">\n"
                        "html"
                        "PUBLIC \"-//W3C//DTD XHTML 1.1//EN"
                        "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd")
      :xhtml1.1-basic (format "<!DOCTYPE %s %s \"%s\">\n"
                              "html"
                              "PUBLIC \"-//W3C//DTD XHTML Basic 1.1//EN"
                              "http://www.w3.org/TR/xhtml-basic/DTD/xhtml-basic11.dtd")

      :xhtml1.0-basic (format "<!DOCTYPE %s %s \"%s\">\n"
                              "html"
                              "PUBLIC \"-//W3C//DTD XHTML Basic 1.0"
                              "http://www.w3.org/TR/xhtml1/DTD/xhtml1-basic11.dtd")
      :xhtml1.0-strict (format "<!DOCTYPE %s %s \"%s\">\n"
                               "html"
                               "PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\""
                               "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd")
      :xhtml1.0-transitional (format "<!DOCTYPE %s %s \"%s\">\n"
                                     "html"
                                     "PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN"
                                     "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd")
      :xhtml1.0-frameset (format "<!DOCTYPE %s %s \"%s\">\n"
                                 "html"
                                 "PUBLIC \"-//W3C//DTD XHTML 1.0 Frameset//EN\""
                                 "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd")
      (format "<!DOCTYPE %s>\n" (name doctype)))))

(declare html->str)

(defn- tag->str [{:keys [attrs contents tag]}]
  (let [t (name tag)]
    (apply str
           "<" t (attrs->str attrs) ">"
           (apply str (map html->str contents))
           "</" t ">")))

(defn- void-tag->str [{:keys [attrs tag]}]
  (apply str
         "<" (name tag) (attrs->str attrs) " />"))

(defn html->str [html]
  (cond (xml? html)
        (xml->str html)

        (doctype? html)
        (doctype->str html)

        (void-tag? html)
        (void-tag->str html)

        (tag? html)
        (tag->str html)

        (sequential? html)
        (apply str (map html->str html))

        :else
        (str html)))
