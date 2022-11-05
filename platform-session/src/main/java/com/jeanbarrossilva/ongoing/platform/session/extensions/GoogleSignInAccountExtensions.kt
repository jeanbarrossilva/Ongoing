package com.jeanbarrossilva.ongoing.platform.session.extensions

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.jeanbarrossilva.ongoing.core.session.user.User

internal fun GoogleSignInAccount.toUser(): User? {
    val (id, displayName, photoUrl) = Triple(id, displayName, photoUrl)
    return if (id != null && displayName != null) {
        User(id, displayName, photoUrl?.toString())
    } else {
        null
    }
}