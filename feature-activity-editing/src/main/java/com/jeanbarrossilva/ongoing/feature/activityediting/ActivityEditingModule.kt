package com.jeanbarrossilva.ongoing.feature.activityediting

import com.jeanbarrossilva.ongoing.feature.activityediting.infra.ActivityEditingGateway
import com.jeanbarrossilva.ongoing.feature.activityediting.infra.inmemory.EditingMode
import com.jeanbarrossilva.ongoing.feature.activityediting.infra.inmemory.InMemoryActivityEditingGateway
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val activityEditingModule = module {
    single<ActivityEditingGateway> { (mode: EditingMode) ->
        InMemoryActivityEditingGateway(sessionManager = get(), mode, androidContext())
    }
}