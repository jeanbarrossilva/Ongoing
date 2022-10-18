package com.jeanbarrossilva.ongoing.core.session.user

import com.jeanbarrossilva.ongoing.core.session.extensions.uuid
import java.io.Serializable

data class User(val id: String, val name: String, val avatarUrl: String): Serializable {
    companion object {
        private const val AVATAR_URL =
            "https://en.gravatar.com/userimage/153558542/cb04b28164b6ec24f7f4cdee8d20d1c9.png"

        val sample = User(uuid(), name = "Jean", AVATAR_URL)
    }
}