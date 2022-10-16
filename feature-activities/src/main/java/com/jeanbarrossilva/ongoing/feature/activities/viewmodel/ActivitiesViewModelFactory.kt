package com.jeanbarrossilva.ongoing.feature.activities.viewmodel

import com.jeanbarrossilva.ongoing.core.registry.ActivityRegistry
import com.jeanbarrossilva.ongoing.core.session.user.UserRepository
import com.jeanbarrossilva.ongoing.platform.designsystem.core.viewmodel.ViewModelFactory

internal class ActivitiesViewModelFactory(
    private val userRepository: UserRepository,
    private val activityRegistry: ActivityRegistry
): ViewModelFactory<ActivitiesViewModel>() {
    override val viewModelClass = ActivitiesViewModel::class

    override fun create(): ActivitiesViewModel {
        return ActivitiesViewModel(userRepository, activityRegistry)
    }
}