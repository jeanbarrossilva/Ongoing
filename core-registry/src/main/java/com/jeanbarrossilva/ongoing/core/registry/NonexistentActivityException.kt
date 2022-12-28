package com.jeanbarrossilva.ongoing.core.registry

class NonexistentActivityException(activityId: String):
    IllegalArgumentException("Activity $activityId does not exist.")