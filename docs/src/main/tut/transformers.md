---
layout: page
title:  "Transformers"
section: "transformers"
position: 2
---

# Transformers

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



