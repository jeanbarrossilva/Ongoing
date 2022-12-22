package com.jeanbarrossilva.ongoing.context.user

import android.os.Parcelable
import com.jeanbarrossilva.ongoing.context.user.extensions.toContextualUser
import com.jeanbarrossilva.ongoing.core.user.User
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable as KSerializable
import java.io.Serializable as JSerializable

@Parcelize
@KSerializable
data class ContextualUser internal constructor(
    val id: String,
    val name: String,
    val email: String,
    val avatarUrl: String?
): Parcelable, JSerializable {
    companion object {
        val sample = User.sample.toContextualUser()
    }
}