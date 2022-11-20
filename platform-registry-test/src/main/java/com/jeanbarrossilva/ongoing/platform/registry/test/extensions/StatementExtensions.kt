package com.jeanbarrossilva.ongoing.platform.registry.test.extensions

import org.junit.runners.model.Statement

internal fun Statement(evaluate: () -> Unit): Statement {
    return object: Statement() {
        override fun evaluate() {
            evaluate()
        }
    }
}