package com.jeanbarrossilva.ongoing.feature.activitydetails.infra.inmemory

import androidx.lifecycle.lifecycleScope
import com.jeanbarrossilva.ongoing.context.registry.domain.activity.fetcher.ContextualActivitiesFetcher
import com.jeanbarrossilva.ongoing.core.registry.ActivityRegistry
import com.jeanbarrossilva.ongoing.core.registry.observation.Observation
import com.jeanbarrossilva.ongoing.core.session.Session
import com.jeanbarrossilva.ongoing.core.session.SessionManager
import com.jeanbarrossilva.ongoing.core.session.extensions.session
import com.jeanbarrossilva.ongoing.feature.activitydetails.ActivityDetailsActivity
import com.jeanbarrossilva.ongoing.feature.activitydetails.domain.ContextActivity
import com.jeanbarrossilva.ongoing.feature.activitydetails.extensions.toContextualActivity
import com.jeanbarrossilva.ongoing.feature.activitydetails.extensions.toggle
import com.jeanbarrossilva.ongoing.feature.activitydetails.infra.ActivityDetailsGateway
import com.jeanbarrossilva.ongoing.feature.activitydetails.observation.ActivityDetailsObservationRequesterFactory
import com.jeanbarrossilva.ongoing.platform.loadable.Loadable
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference

internal class InMemoryActivityDetailsGateway(
    private val sessionManager: SessionManager,
    private val activityRegistry: ActivityRegistry,
    private val observation: Observation,
    private val activityId: String,
    private val androidActivityRef: WeakReference<ActivityDetailsActivity>,
    private val fetcher: ContextualActivitiesFetcher,
    private val onObservationToggle: (isObserving: Boolean) -> Unit
): ActivityDetailsGateway {
    private val androidActivity
        get() = androidActivityRef.get()!!

    constructor(
        sessionManager: SessionManager,
        activityRegistry: ActivityRegistry,
        observation: Observation,
        activityId: String,
        androidActivity: ActivityDetailsActivity,
        fetcher: ContextualActivitiesFetcher,
        onObservationToggle: (isObserving: Boolean) -> Unit
    ): this(
        sessionManager,
        activityRegistry,
        observation,
        activityId,
        WeakReference(androidActivity),
        fetcher,
        onObservationToggle
    )

    override suspend fun getActivity(): Loadable<ContextActivity> {
        val coreActivity = activityRegistry.getActivityById(activityId)
        val exception by lazy { NonexistentActivityException(activityId) }
        val failed by lazy { Loadable.Failed<ContextActivity>(exception) }
        val contextActivity =
            coreActivity?.toContextualActivity(sessionManager, androidActivity) ?: return failed
        return Loadable.Loaded(contextActivity)
    }

    override suspend fun fetch() {
        fetcher.fetch()
    }

    override suspend fun setObserving(isObserving: Boolean) {
        ActivityDetailsObservationRequesterFactory.create().request(androidActivity, isObserving) {
            onObservationAcceptance(activityId, isObserving)
        }
    }

    private fun onObservationAcceptance(activityId: String, isObserving: Boolean) {
        sessionManager.session<Session.SignedIn>()?.userId?.let {
            androidActivity.lifecycleScope.launch {
                activityRegistry.observer.toggle(it, activityId, isObserving, observation)
                onObservationToggle(isObserving)
                fetch()
            }
        }
    }
}