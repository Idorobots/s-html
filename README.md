# s-html

A library of composabe, S-expression-based HTML generators.

## Usage

Use provided functions to compose HTML documents:


``` clojure
(defn hello-world-body ()
  (div {:class :container)
       (h1 "Hello world!")))

(defn hello-world (title-str)
  (html (head (title title-str)
        (body (hello-world-body))))

(str (hello-world "Hi!"))
```

## License

Copyright © 2015 Kajetan Rzepecki <kajtek@idorobots.org>

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
