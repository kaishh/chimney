# Chimney

[![Build Status](https://travis-ci.org/scalalandio/chimney.svg?branch=master)](https://travis-ci.org/scalalandio/chimney)
[![Maven Central](https://img.shields.io/maven-central/v/io.scalaland/chimney_2.12.svg)](http://search.maven.org/#search%7Cga%7C1%7Cchimney)
[![codecov.io](http://codecov.io/github/scalalandio/chimney/coverage.svg?branch=master)](http://codecov.io/github/scalalandio/chimney?branch=master)
[![License](http://img.shields.io/:license-Apache%202-green.svg)](http://www.apache.org/licenses/LICENSE-2.0.txt)


Chimney is a Scala library for automatic mapping and transforming data structures.

It sometimes happens that you need to transform an object into another target object,
which contains number of the same fields in its definition.

```scala
case class DoSthCommand(id: Int, what: String, requestor: String, doImmediately: Boolean)
case class DidSthEvent(id: Int, didAt: Timestamp, what: String, requestor: String)
```

## Transformations

Instead of constructing target object field by field like in the example below...

```scala
val command = DoSthCommand(10, "Prepare good coffee", "John", true)
val event = DidSthEvent(id = command.id,
                        didAt = Timestamp.now,
                        what = command.what,
                        requestor = command.requestor)
```

...you can use Chimney library to derive transformation of fields, that have the same
name and type, requiring only providing values for fields that are missing in the source
object.

```scala
import io.scalaland.chimney.dsl._

val command = DoSthCommand(10, "Prepare good coffee", "John", true)
val event = command.into[DidSthEvent].withFieldConst(_.didAt, Timestamp.now).transform
```

If you forget about providing a value for `didAt` field, which is not present in the
source object, you will get compile-time error.

More details about Chimney transformers you can read [here](transformers.html).

## Patching

Similar, but a bit different use case is when you already hold an object, but you
want to modify only some subset of its fields, where values for them are contained
inside another object.


```scala
case class User(id: Int, name: String, email: String, city: String, street: String, zip: String)
case class ChangeAddress(city: String, street: String, zip: String)

val user = User(10, "John", "john@example.com", "Sin city" , "Bad street", "00000")
val changeAddress = ChangeAddress("Sun city", "Flowers alley", "12345")

import io.scalaland.chimney.dsl._

val updatedUser = user.patchWith(changeAddress)
// updatedUser = User(10, "John", "john@example.com", "Sun city" , "Flowers alley", "12345")
```

More details about Chimney patchers you can read [here](patchers.html).

## Getting started

Chimney supports both Scala 2.11 and 2.12. To quickly get started, you should add following
line to your `build.sbt` file.

```scala
libraryDependencies += "io.scalaland" %% "chimney" % "0.1.6"

// or if you're using Scala.js:
// libraryDependencies += "io.scalaland" %%% "chimney" % "0.1.6"
```

Due to [SI-7046](https://issues.scala-lang.org/browse/SI-7046) some derivations require at least Scala 2.12.1 or 2.11.9.

## Copyright & license

Copyright 2017 Piotr Krzemiński, Mateusz Kubuszok

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0
Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
