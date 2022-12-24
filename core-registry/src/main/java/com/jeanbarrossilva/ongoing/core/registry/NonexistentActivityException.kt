package com.jeanbarrossilva.ongoing.core.registry

internal class NonexistentActivityException(activityId: String):
    IllegalArgumentException("Activity $activityId does not exist.")