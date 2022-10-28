package com.jeanbarrossilva.ongoing.platform.designsystem.extensions

inline fun <T> T.`if`(condition: () -> Boolean, update: T.() -> T): T {
    return `if`(condition(), update)
}

inline fun <T> T.`if`(condition: Boolean, update: T.() -> T): T {
    return if (condition) update() else this
}