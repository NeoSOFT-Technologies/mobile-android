## Why Kotlin?

Android’s Kotlin-first approach.
At Google I/O 2019, it was announced that Android development will be increasingly Kotlin-first.

Kotlin is an expressive and concise programming language that reduces common code errors and easily integrates into existing apps.

If you’re looking to build an Android app, we recommend starting with Kotlin to take advantage of its best-in-class features.

![android and kotlin logos](https://developer.android.com/images/kotlin/kotlin-for-android-text.svg)

## Why is Android development Kotlin-first?

Google has reviewed feedback that came directly from developers at conferences, the Customer Advisory Board (CAB), Google Developers Experts (GDE), and through the developer research. Many developers already enjoy using Kotlin, and the request for more Kotlin support was clear. Here’s what developers appreciate about writing in Kotlin:

- **Expressive and concise:** You can do more with less. Express your ideas and reduce the amount of boilerplate code. 67% of professional developers who use Kotlin say Kotlin has increased their productivity.

- **Safer code:** Kotlin has many language features to help you avoid common programming mistakes such as null pointer exceptions. Android apps that contain Kotlin code are 20% less likely to crash.

- **Interoperable:** Call Java-based code from Kotlin, or call Kotlin from Java-based code. Kotlin is 100% interoperable with the Java programming language, so you can have as little or as much of Kotlin in your project as you want.

- **Structured Concurrency:** Kotlin coroutines make asynchronous code as easy to work with as blocking code. Coroutines dramatically simplify background task management for everything from network calls to accessing local data.

  

## Kotlin vs Java: Comparison table

|  **Programming Language**  |                          **Kotlin**                          |                           **Java**                           |
| :------------------------: | :----------------------------------------------------------: | :----------------------------------------------------------: |
|                            |                                                              |                                                              |
|      **Null Safety**       | In Kotlin it is not possible to attribute null values to variables or objects by default. | NullPointerExceptions - enables developers to attribute a null value to any variable. |
|  **Extension Functions**   | Kotlin allows the developer to extend the functionality of classes without necessarily having to inherit from a class. | To extend the functionality of an existing class, one must create a new class and inherit the functions from the parent class. |
|          **Code**          | Typically requires less code and is a very concise language. |           Requires more lines of code than Kotlin.           |
|   **Coroutines Support**   | Provides coroutines suport. Coroutines are stackless and allow the developer to write code, suspend the execution and later resume it again. | Java enables the creation of multiple background threads when handling lengthy operations. |
|      **Data Classes**      | Kotlin provides a more straightforward way to create classes to hold data by simply including the "data'' keyword in the class definition. | Developers need to establish the fields (or variables) to store the data, the constructor, and the getter and setter functions for the fields/variables, as well as other functions, such as the hashCode(), equals(), and toString(). |
|      **Smart Casts**       | Casting checks are handled by the smart casts feature. Kotlin's intelligent compiler automatically manages redundant casts. | The developer must check the variables' type in consonance to the operation. |
|   **Checked Exceptions**   | Checked exceptions are not available on Kotlin. Thus, Kotlin's developers do not require to catch or declare exceptions. |       Java developers have checked exceptions support.       |
| **Functional Programming** | Kotlin is a mix of object-oriented and functional programming. It can use lambda expressions and high-order functions. | Java is more limited to the concept of object-oriented programming. |
|    **Primitive Types**     | In Kotlin, as soon as you initiate a variable of a primitive type, it will be automatically considered an object. | In Java, variables of a primitive type are not objects; they are predefined Java's data types. |



## Kotlin vs Java: which is better?

First and foremost, despite their differences, both **Java and Kotlin compile to bytecode**. Therefore, developers can easily call Kotlin code to Java or the other way around, which allows both languages to be used in the same [development project](https://www.imaginarycloud.com/blog/web-design-and-development/).

As we have explained, Kotlin does offer numerous advantages regarding Android development, but **is Kotlin better than Java**? Well, it does have some benefits over its competitor:

- Requires less code
- Deployment is lightweight and faster to compile
- Supports coroutines
- It is compatible with Java's libraries and frameworks
- No more NullPointerException

Nonetheless, let's not forget that Java also has its own benefits:

- Robust coding
- Multiplatform that supports almost any server, operating system, or device
- Android itself is built on Java
- It has been around for longer; thus, it has a bigger community and documentation, as well as an extensive ecosystem with plenty of libraries and tools

Now that we have highlighted each language's benefits, it is even harder to choose between Kotlin and Java, right? Well, let's try to take a pragmatic look.

Kotlin has been emerging as the **new Android language**. Its success comes from the fact that this language introduces vital features that are making developers' life a lot easier, such as extension functions, lambda expressions and high-order functions, coroutines, and no *NullPointerExceptions*.

These are just some of the features that make it safe to affirm that, yes, **Kotlin is better than Java for Android development** and is likely to dominate in the future.

**Is Kotlin Replacing Java?** Everything seems to be moving toward Kotlin, and the new development tools that are rising know it! However, Java still has a lot of value and should not be neglected.


## Conclusion

For Android development, **Kotlin seems to be the best option** at the moment. Many companies and developers are adopting it, and the language will most likely continue to grow.
