# pet-owners

A example Clojure project with Datomic database.

- https://youtu.be/ao7xEwCjrWQ
- https://github.com/alexanderkiel/datomic-free
- https://docs.datomic.com/on-prem/dev-setup.html#create-db

## Test

```bash
lein check
lein test
```

## Run docker Datomic

```bash
#Run container
docker run -d -e ADMIN_PASSWORD="admin" -e DATOMIC_PASSWORD="datomic" -p 4334-4336:4334-4336 --name datomic-free akiel/datomic-free
#Enter REPL
lein repl
```

```clojure
;;Create database
(require '[datomic.api :as d])
(def db-uri "datomic:free://localhost:4334/pet-owners-db")
(d/create-database db-uri)
```

```clojure
;;Execute functions
(let [schema (load-file "resources/datomic/schema.edn")]
      (d/transact conn schema))
(add-pet-owner "Belen")
(find-all-pet-owners)      
```

## License

Copyright © 2020 FIXME

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.
