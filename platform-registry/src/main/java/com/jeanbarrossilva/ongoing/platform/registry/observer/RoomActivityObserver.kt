package com.jeanbarrossilva.ongoing.platform.registry.observer

import com.jeanbarrossilva.ongoing.core.registry.activity.Activity
import com.jeanbarrossilva.ongoing.core.registry.observation.Change
import com.jeanbarrossilva.ongoing.core.registry.observation.Observation
import com.jeanbarrossilva.ongoing.platform.registry.activity.ActivityDao
import kotlinx.coroutines.flow.first

class RoomActivityObserver internal constructor(
    private val activityDao: ActivityDao,
    private val observerDao: ObserverDao
): Activity.Observer {
    private val observations = hashMapOf<String, Observation>()

    override suspend fun attach(userId: String, activityId: String, observation: Observation) {
        val entity = createEntity(userId, activityId) {
            "Cannot attach an observer to a nonexistent activity."
        }
        observerDao.insert(entity)
        observations[entity.id] = observation
    }

    override suspend fun notify(change: Change, activityId: String) {
        observerDao
            .selectAll()
            .first()
            .filter { it.activityId.toString() == activityId }
            .map(ObserverEntity::id)
            .mapNotNull(observations::get)
            .forEach { it.onChange(change, activityId) }
    }

    override suspend fun detach(userId: String, activityId: String) {
        val entity = createEntity(userId, activityId) {
            "Cannot detach an observer from a nonexistent activity."
        }
        observations.remove(entity.id)
        observerDao.delete(entity)
    }

    override suspend fun clear() {
        observations.clear()
        observerDao.deleteAll()
    }

    private suspend fun createEntity(
        userId: String,
        activityId: String,
        nonexistentActivityMessage: () -> String
    ): ObserverEntity {
        assertActivityExists(activityId, nonexistentActivityMessage)
        val activityIdAsLong = activityId.toLong()
        return ObserverEntity(userId, activityIdAsLong)
    }

    private suspend fun assertActivityExists(activityId: String, message: () -> String) {
        val activity = activityDao.selectById(activityId).first()
        assert(activity != null, message)
    }
}