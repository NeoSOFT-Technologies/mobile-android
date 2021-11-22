# Coding Style

## Table of Contents

- [General](#general)
  + [Packages](#packages)
  + [Classes & Interfaces](#classes--interfaces)
  + [Methods](#methods)
  + [Fields](#fields)
  + [Variables & Parameters](#variables--parameters)
  + [Misc](#misc)
- [Declarations](#declarations)
  + [Visibility Modifiers](#visibility-modifiers)
  + [Fields & Variables](#fields--variables)
  + [Classes](#classes)
  + [Data Type Objects](#data-type-objects)
  + [Enum Classes](#enum-classes)
- [Spacing](#spacing)
  + [Line Length](#line-length)
  + [Vertical Spacing](#vertical-spacing)
- [Semicolons](#semicolons)
- [Getters & Setters](#getters--setters)
- [Brace Style](#brace-style)
- [When Statements](#when-statements)
- [Annotations](#annotations)
- [Types](#types)
  + [Type Inference](#type-inference)
  + [Constants vs. Variables](#constants-vs-variables)
  + [Optionals](#optionals)
- [Language](#language)
- [Copyright Statement](#copyright-statement)


## General

### Packages

Package names are similar to Java: all **lower-case**, multiple words concatenated together, without hypens or underscores:

**BAD**:

```kotlin
com.example.funky_widget
```

**GOOD**:

```kotlin
com.example.funkywidget
```

### Classes & Interfaces

Written in **UpperCamelCase**. For example `RadialSlider`. 

### Methods

Written in **lowerCamelCase**. For example `setValue`.

### Fields

Generally, written in **lowerCamelCase**. Fields should **not** be named with Hungarian notation, as Hungarian notation is [erroneously thought](http://jakewharton.com/just-say-no-to-hungarian-notation/) to be recommended by Google.

Example field names:

```kotlin
class MyClass {
  var publicField: Int = 0
  val person = Person()
  private var privateField: Int?
}
```

Constant values in the companion object should be written in **uppercase**, with an underscore separating words:

```kotlin
companion object {
  const val THE_ANSWER = 42
}
```

### Variables & Parameters

Written in **lowerCamelCase**.

Single character values must be avoided, except for temporary looping variables.

### Misc

In code, acronyms should be treated as words. For example:

**BAD:**

```kotlin
XMLHTTPRequest
URL: String? 
findPostByID
```
**GOOD:**

```kotlin
XmlHttpRequest
url: String
findPostById
```

## Declarations

### Visibility Modifiers

Only include visibility modifiers if you need something other than the default of public.

**BAD:**

```kotlin
public val wideOpenProperty = 1
private val myOwnPrivateProperty = "private"
```

**GOOD:**

```kotlin
val wideOpenProperty = 1
private val myOwnPrivateProperty = "private"
```

### Fields & Variables

Prefer single declaration per line.

**GOOD:**

```kotlin
username: String
twitterHandle: String
```

### Classes

Exactly one class per source file, although inner classes are encouraged where scoping appropriate.

### Data Type Objects

Prefer data classes for simple data holding objects.

**BAD:**

```kotlin
class Person(val name: String) {
  override fun toString() : String {
    return "Person(name=$name)"
  }
}
```

**GOOD:**

```kotlin
data class Person(val name: String)
```

### Enum Classes

Enum classes without methods may be formatted without line-breaks, as follows:

```kotlin
private enum CompassDirection { EAST, NORTH, WEST, SOUTH }
```

### Line Length

Lines should be no longer than 100 characters long.


### Vertical Spacing

There should be exactly one blank line between methods to aid in visual clarity and organization. Whitespace within methods should separate functionality, but having too many sections in a method often means you should refactor into several methods.

## Comments

When they are needed, use comments to explain **why** a particular piece of code does something. Comments must be kept up-to-date or deleted.

Avoid block comments inline with code, as the code should be as self-documenting as possible. *Exception: This does not apply to those comments used to generate documentation.*


## Semicolons

Semicolons should be avoided wherever possible in Kotlin. 

**BAD**:

```kotlin
val horseGiftedByTrojans = true;
if (horseGiftedByTrojans) {
  bringHorseIntoWalledCity();
}
```

**GOOD**:

```kotlin
val horseGiftedByTrojans = true
if (horseGiftedByTrojans) {
  bringHorseIntoWalledCity()
}
```

## Getters & Setters

Unlike Java, direct access to fields in Kotlin is preferred. 

If custom getters and setters are required, they should be declared [following Kotlin conventions](https://kotlinlang.org/docs/reference/properties.html) rather than as separate methods.

## Brace Style

Only trailing closing-braces are awarded their own line. All others appear the same line as preceding code:

**BAD:**

```kotlin
class MyClass
{
  fun doSomething()
  {
    if (someTest)
    {
      // ...
    }
    else
    {
      // ...
    }
  }
}
```

**GOOD:**

```kotlin
class MyClass {
  fun doSomething() {
    if (someTest) {
      // ...
    } else {
      // ...
    }
  }
}
```

Conditional statements are always required to be enclosed with braces, irrespective of the number of lines required.

**BAD:**

```kotlin
if (someTest)
  doSomething()
if (someTest) doSomethingElse()
```

**GOOD:**

```kotlin
if (someTest) {
  doSomething()
}
if (someTest) { doSomethingElse() }
```

## When Statements

Unlike `switch` statements in Java, `when` statements do not fall through. Separate cases using commas if they should be handled the same way. Always include the else case.

**BAD:**

```kotlin
when (anInput) {
  1 -> doSomethingForCaseOneOrTwo()
  2 -> doSomethingForCaseOneOrTwo()
  3 -> doSomethingForCaseThree()
}
```

**GOOD:**

```kotlin
when (anInput) {
  1, 2 -> doSomethingForCaseOneOrTwo()
  3 -> doSomethingForCaseThree()
  else -> println("No case satisfied")
}
```


## Types 

Always use Kotlin's native types when available. 

### Type Inference

Type inference should be preferred where possible to explicitly declared types. 

**BAD:**

```kotlin
val something: MyType = MyType()
val meaningOfLife: Int = 42
```

**GOOD:**

```kotlin
val something = MyType()
val meaningOfLife = 42
```

### Constants vs. Variables 

Constants are defined using the `val` keyword, and variables with the `var` keyword. Always use `val` instead of `var` if the value of the variable will not change.

*Tip*: A good technique is to define everything using `val` and only change it to `var` if the compiler complains!


### Nullable Types

Declare variables and function return types as nullable with `?` where a `null` value is acceptable.

Use implicitly unwrapped types declared with `!!` only for instance variables that you know will be initialized before use, such as subviews that will be set up in `onCreate` for an Activity or `onCreateView` for a Fragment.

When naming nullable variables and parameters, avoid naming them like `nullableString` or `maybeView` since their nullability is already in the type declaration.

When accessing a nullable value, use the safe call operator if the value is only accessed once or if there are many nullables in the chain:

```kotlin
editText?.setText("foo")
```


## Language

Use `en-US` English spelling. 🇺🇸

**BAD:**

```kotlin
val colourName = "red"
```

**GOOD:**

```kotlin
val colorName = "red"
```