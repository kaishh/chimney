---
layout: page
title:  "Transformers"
section: "transformers"
position: 2
---

# Transformers

## Addressed problems

Main motivation for designing this library was annoyance coming from
the fact that in real-life projects it often happens we need to write
lot of repetitive, boring code to create object of some type from the
object of different type, which definitions are almost identical.

#### Rewriting case classes

Let's consider simple example.

```scala
case class Foo(x: String, y: Int, z: Boolean)
case class Bar(y: Int, x: String)

val foo: Foo = Foo("abc", 123, true)
```

If we want to create object of type `Bar` basing on values of instance
`foo : Foo`, we could do it by hand, just rewriting values field by field.

```scala
val bar: Bar = Bar(y = foo.y, x = foo.x)
// bar: Bar = Bar(123,abc)
```

As you may expect, this approach could quickly become tedious, especially
at certain scale, when your types are bigger, highly nested and complex
enough. What you can do instead is automating mapping process with help of
Chimney library.

```scala
import io.scalaland.chimney.dsl._
val bar: Bar = foo.transformInto[Bar]
// bar: Bar = Bar(123,abc)
```

The only requirement that has to be satisfied to successfully derive such
transformation is that for every field of target type `Bar` there has to exist
a field of the same name and type in the source object of type `Foo`.

If the particular case is more complicated and requirement above can't be satisfied,
you can customize transformation and provide values only for fields that can't be
derived automatically in a number of ways.

#### Domain model versioning

In codebases that are mature enough, but still need to evolve and implement new features,
sometimes a problem of domain model versioning starts to appear. In fact there are number of
ways how to solve it that differ when it comes to level of complexity and provided guarantees.
One of the simplest approach that often seems to be sufficient is to maintain evolving
version of the same domain models in different packages, like in the following example.

```scala
package v1 {
  case class User(id: Int, name: String, email: String)
}

package v2 {
  case class User(id: Int, name: String, email: String, age: Int)
}
```






#### DTO objects mapping

## Basic usage

#### Support for nested transformations

#### Providing missing values

#### Fields renaming


## Support for standard Scala types

#### Option[T]

#### Either[A, B]

#### Collections

#### Value classes

## Support for sealed trait hierarchies

#### Basic example

#### Customizing types rewriting












--------------

old structure, need to be removed later:






## Basics

The library defines type class `Transformer` which is basically wrapping for a function
that takes object of type `From` and performs transformation to target object of type `To`.

```scala
trait Transformer[From, To] {
  def transform(src: From): To
}
```

All in all, we could write transformer instances for every pair of data types that we
want to convert between.

```scala
case class DoSthCommand(id: Int, what: String, requestor: String, doImmediately: Boolean)
case class DidSthEvent(id: Int, didAt: Timestamp, what: String, requestor: String)

implicit val commandToEventTransformer: Transformer[DoSthCommand, DidSthEvent] = {
  (command: DoSthCommand) =>
    DidSthEvent(id = command.id,
                didAt = Timestamp.now,
                what = command.what,
                requestor = command.requestor)
}
```

But as you may guess, maintaining lot of such repetitive would quickly become cumbersome. 
Instead, the library provides implicit instance for identical types, which is simply
implemented using identity function.

```scala
implicit def identityTransformer[T]: Transformer[T, T] = identity[T] 
```

Having this instance in implicit scope, we can rewrite our instance to:

```scala
implicit val commandToEventTransformer: Transformer[DoSthCommand, DidSthEvent] = {
  (command: DoSthCommand) =>
    DidSthEvent(id = implicitly[Transformer[Int, Int]].transform(command.id),
                didAt = Timestamp.now,
                what = implicitly[Transformer[String, String]].transform(command.what),
                requestor = implicitly[Transformer[String, String]].transform(command.requestor))
}
```


Beside identity transformer, the library provides rules for deriving transformers
for case classes. All in all, we could alwas



> Real implementation and transformer types used in the library are a bit different to
> allow for flexible customizations, but the general behind idea stays the same.





`x.transformInto[T]` and

`x.into[T].transform` 

## Custom transformers

## Modifiers

#### withFieldConst

#### withFieldFunc

#### withFieldRenamed


## Automatic mappings

#### Option[T]

#### Either[A, B]

#### Collections

#### Value classes


## Sealed trait hierarchies


## Example usages

#### Domain types versioning

#### DTO mapping



