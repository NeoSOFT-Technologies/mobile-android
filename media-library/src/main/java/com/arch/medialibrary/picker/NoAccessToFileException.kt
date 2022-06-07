package com.arch.medialibrary.picker

class NoAccessToFileException(path: String) : RuntimeException("no access to $path")