# s-html [![Build Status](https://travis-ci.org/Idorobots/s-html.svg?branch=master)](https://travis-ci.org/Idorobots/s-html) [![Clojars Project](https://img.shields.io/clojars/v/s-html.svg)](https://clojars.org/s-html)

A library of composable, S-expression-based HTML generators.

## Usage

[API docs.](https://idorobots.github.io/s-html/) Use provided functions to compose HTML documents:


``` clojure
(require '[s-html.tags.html :refer [body div head html h1 h2 h3 h4 h5 h6 title]])

(def container (partial div {:class :container}))

(defn hello-world-div [tag]
  (container (tag "Hello world!")))

(defn hello-world [title-str]
  (html (head (title title-str)
        (body (map hello-world-div
              [h1 h2 h3 h4 h5 h6])))))

(require '[s-html.print :refer [html->str]])

(html->str (hello-world "Hi!"))
```

## License

Copyright © 2015-2016 Kajetan Rzepecki <kajtek@idorobots.org>

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
