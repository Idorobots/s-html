# s-html [![Build Status](https://travis-ci.org/Idorobots/s-html.svg?branch=master)](https://travis-ci.org/Idorobots/s-html) [![Clojars Project](https://img.shields.io/clojars/v/s-html.svg)](https://clojars.org/s-html)

A library of composabe, S-expression-based HTML generators.

## Usage

Use provided functions to compose HTML documents:


``` clojure
(defn hello-world-div [tag]
  (div {:class :container}
       (tag "Hello world!")))

(defn hello-world [title-str]
  (html (head (title title-str)
        (body (map hello-world-div
                   [h1 h2 h3 h4 h5 h6])))))

(html->str (hello-world "Hi!"))
```

## License

Copyright © 2015 Kajetan Rzepecki <kajtek@idorobots.org>

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
