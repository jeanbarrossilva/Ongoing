package com.jeanbarrossilva.ongoing.feature.activitydetails.infra.inmemory

internal class NonexistentActivityException(activityId: String):
    IllegalArgumentException("Activity $activityId does not exist.")