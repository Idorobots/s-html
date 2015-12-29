(ns s-html.print
  (:require [s-html.tags :refer [doctype? tag? void-tag?]]))

(defn- doctype->str [{:keys [doctype]}]
  (format "<!DOCTYPE %s>" (name doctype)))

(defn- value->str [val]
  (if (list? val)
    (reduce (fn [a b]
              (str a " " b))
            (map value->str val))
    (name val)))

(defn- attrs->str [attrs]
  (apply str
         (map (fn [[n v]]
                (format " %s=\"%s\"" (name n) (value->str v)))
              (seq attrs))))

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
  (cond (list? html)
        (apply str (map html->str html))

        (doctype? html)
        (doctype->str html)

        (void-tag? html)
        (void-tag->str html)

        (tag? html)
        (tag->str html)

        :else
        (str html)))
