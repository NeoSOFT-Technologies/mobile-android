package com.arch.logger

import org.jetbrains.annotations.NonNls

/** Logging for lazy people */
internal interface AppLoggerBinder {


    /** Log a verbose message with optional format args.  */
    fun v(@NonNls message: String?, vararg args: Any?)

    /** Log a verbose exception and a message with optional format args.  */
    fun v(t: Throwable?, @NonNls message: String?, vararg args: Any?)

    /** Log a verbose exception.  */
    fun v(t: Throwable?)

    /** Log a debug message with optional format args.  */
    fun d(@NonNls message: String?, vararg args: Any?)

    /** Log a debug exception and a message with optional format args.  */
    fun d(t: Throwable?, @NonNls message: String?, vararg args: Any?)

    /** Log a debug exception.  */
    fun d(t: Throwable?)

    /** Log an info message with optional format args.  */
    fun i(@NonNls message: String?, vararg args: Any?)

    /** Log an info exception and a message with optional format args.  */
    fun i(t: Throwable?, @NonNls message: String?, vararg args: Any?)

    /** Log an info exception.  */
    fun i(t: Throwable?)

    /** Log a warning message with optional format args.  */
    fun w(@NonNls message: String?, vararg args: Any?)

    /** Log a warning exception and a message with optional format args.  */
    fun w(t: Throwable?, @NonNls message: String?, vararg args: Any?)

    /** Log a warning exception.  */
    fun w(t: Throwable?)

    /** Log an error message with optional format args.  */
    fun e(@NonNls message: String?, vararg args: Any?)

    /** Log an error exception and a message with optional format args.  */
    fun e(t: Throwable?, @NonNls message: String?, vararg args: Any?)

    /** Log an error exception.  */
    fun e(t: Throwable?)

    /** Log an assert message with optional format args.  */
    fun wtf(@NonNls message: String?, vararg args: Any?)

    /** Log an assert exception and a message with optional format args.  */
    fun wtf(t: Throwable?, @NonNls message: String?, vararg args: Any?)

    /** Log an assert exception.  */
    fun wtf(t: Throwable?)

    /** Log at `priority` a message with optional format args.  */
    fun log(priority: Int, @NonNls message: String?, vararg args: Any?)

    /** Log at `priority` an exception and a message with optional format args.  */
    fun log(priority: Int, t: Throwable?, @NonNls message: String?, vararg args: Any?)

    /** Log at `priority` an exception.  */
    fun log(priority: Int, t: Throwable?)
}