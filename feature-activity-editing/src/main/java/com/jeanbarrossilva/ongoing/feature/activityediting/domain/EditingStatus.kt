package com.jeanbarrossilva.ongoing.feature.activityediting.domain

import java.io.Serializable

internal data class EditingStatus(val id: String, val title: String): Serializable {
    companion object {
        val toDo = EditingStatus(id = "to-do", "To-do")
        val ongoing = EditingStatus(id = "ongoing", title = "Ongoing")
        val done = EditingStatus(id = "done", title = "Done")
        val samples = listOf(toDo, ongoing, done)
        val sample = samples.first()
    }
}