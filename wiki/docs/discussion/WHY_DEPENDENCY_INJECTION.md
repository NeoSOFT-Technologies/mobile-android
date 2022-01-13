## **Why Dependency Injection?**

Dependency injection is a technique widely used in programming and well suited to Android development. By following the principles of dependency injection, you lay the groundwork for a good app architecture.

Implementing dependency injection provides you with the following advantages:

* Reusability of code.
* Ease of refactoring.
* Ease of testing.

## **Why HILT?**

Hilt is built on top of the popular DI library Dagger to benefit from the compile time correctness, runtime performance, scalability, and Android Studio support that Dagger provides.

Since many Android framework classes are instantiated by the OS itself, there's an associated boilerplate when using Dagger in Android apps. Unlike Dagger, Hilt is integrated with Jetpack libraries and Android framework classes and removes most of that boilerplate to let you focus on just the important parts of defining and injecting bindings without worrying about managing all of the Dagger setup and wiring. It automatically generates and provides:

* Components for integrating Android framework classes with Dagger that you would otherwise need to create by hand.
* Scope annotations for the components that Hilt generates automatically.
* Predefined bindings and qualifiers. 

To learn more about Hilt see [Dependency Injection with Hilt](https://developer.android.com/training/dependency-injection/hilt-android).


## Additional resources

* [Dependency Injection - Wikipedia](https://en.wikipedia.org/wiki/Dependency_injection)
* [Android Dependency Injection](https://developer.android.com/training/dependency-injection)
* [Dependency Injection with Hilt](https://developer.android.com/training/dependency-injection/hilt-android)
