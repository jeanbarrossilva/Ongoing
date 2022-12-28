package com.jeanbarrossilva.ongoing.feature.activityediting.rule;

import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;

import com.jeanbarrossilva.ongoing.core.registry.ActivityRegistry;
import com.jeanbarrossilva.ongoing.core.registry.activity.Activity;
import com.jeanbarrossilva.ongoing.feature.activityediting.infra.inmemory.*;

import javax.annotation.Nonnull;

/**
 * Provides an {@link EditingMode} through {@link EditingModeProvider#provide} for tests to use so
 * that they don't have to create it themselves, since creating one also requires an
 * {@link ActivityRegistry} an {@link Activity} ID.
 **/
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
@SuppressWarnings("KotlinInternalInJava")
public enum EditingModeProvider {
    /** Provides an {@link EditingMode.Addition}. **/
    ADDITION {
        @Override
        @NonNull
        EditingMode provide(ActivityRegistry activityRegistry, String activityId) {
            return new EditingMode.Addition(activityRegistry);
        }
    },
    /** Provides an {@link EditingMode.Modification}. **/
    MODIFICATION {
        @Override
        @NonNull
        EditingMode provide(ActivityRegistry activityRegistry, String activityId) {
            return new EditingMode.Modification(activityRegistry, activityId);
        }
    };

    /** Provides an {@link EditingMode}. **/
    @Nonnull
    abstract EditingMode provide(ActivityRegistry activityRegistry, String activityId);
}
