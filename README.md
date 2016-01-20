# s-html [![Build Status](https://travis-ci.org/Idorobots/s-html.svg?branch=master)](https://travis-ci.org/Idorobots/s-html) [![Clojars Project](https://img.shields.io/clojars/v/s-html.svg)](https://clojars.org/s-html)

A library of composable, S-expression-based HTML generators.

## Usage

[API docs.](https://idorobots.github.io/s-html/) Use provided functions to compose HTML documents:


``` clojure
(require '[s-html.tags :as t])

(def container (partial t/div {:class :container}))

(defn hello-world-div [tag]
  (container (tag "Hello world!")))

(defn hello-world [title-str]
  (t/html (t/head (t/title title-str)
          (t/body (map hello-world-div
                       [t/h1 t/h2 t/h3 t/h4 t/h5 t/h6])))))

(require '[s-html.print :as p])

(p/html->str (hello-world "Hi!"))
```

## License

Copyright © 2015-2016 Kajetan Rzepecki <kajtek@idorobots.org>

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
