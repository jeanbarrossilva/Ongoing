package com.jeanbarrossilva.ongoing.platform.registry.authorization

abstract class CurrentUserIdProvider {
    internal abstract suspend fun provide(): String?

    companion object {
        operator fun invoke(provide: suspend () -> String?): CurrentUserIdProvider {
            return object: CurrentUserIdProvider() {
                override suspend fun provide(): String? {
                    return provide()
                }
            }
        }
    }
}