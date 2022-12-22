package com.jeanbarrossilva.ongoing.context.user.extensions

import com.jeanbarrossilva.ongoing.context.user.ContextualUser
import com.jeanbarrossilva.ongoing.core.user.User

/** Converts the given [User] into a [ContextualUser]. **/
fun User.toContextualUser(): ContextualUser {
    return ContextualUser(id, name, email, avatarUrl)
}