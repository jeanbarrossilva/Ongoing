package com.jeanbarrossilva.ongoing.core.session.user

import java.io.Serializable

data class User(val id: String, val name: String, val avatarUrl: String?): Serializable {
    companion object {
        private const val AVATAR_URL =
            "https://en.gravatar.com/userimage/153558542/cb04b28164b6ec24f7f4cdee8d20d1c9.png"

        val sample = User(id = "97e2d0cf-f38e-4ddb-903c-85db085cedf2", name = "Jean", AVATAR_URL)
    }
}