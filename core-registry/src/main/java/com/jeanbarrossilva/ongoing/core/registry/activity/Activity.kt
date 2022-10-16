package com.jeanbarrossilva.ongoing.core.registry.activity

data class Activity(
    val id: String,
    val ownerUserId: String,
    val name: String,
    val icon: Icon,
    val statuses: List<Status>,
    val currentStatus: Status
) {
    companion object
}