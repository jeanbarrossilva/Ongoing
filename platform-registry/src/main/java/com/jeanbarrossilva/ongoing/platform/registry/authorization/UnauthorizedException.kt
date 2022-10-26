package com.jeanbarrossilva.ongoing.platform.registry.authorization

internal class UnauthorizedException(private val activityId: String):
    IllegalAccessException("Only the owner of activity $activityId may execute this operation.")