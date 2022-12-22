package com.jeanbarrossilva.ongoing.core.session.user

import java.io.Serializable

data class User(val id: String, val name: String, val email: String, val avatarUrl: String?):
    Serializable {
    companion object {
        private const val AVATAR_URL =
            "https://en.gravatar.com/userimage/153558542/cb04b28164b6ec24f7f4cdee8d20d1c9.png"

        val sample = User(
            id = "7bf0999c-d301-4b45-99a0-b85e60bdb4e0",
            name = "Jean",
            email = "me@jeanbarrossilva.com",
            AVATAR_URL
        )
    }
}