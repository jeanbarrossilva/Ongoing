package com.jeanbarrossilva.ongoing.feature.activityediting

import android.app.Activity
import android.os.Parcelable
import androidx.activity.OnBackPressedCallback
import com.afollestad.materialdialogs.MaterialDialog
import com.jeanbarrossilva.ongoing.context.registry.domain.ContextualActivity
import com.jeanbarrossilva.ongoing.core.registry.ActivityRegistry
import com.jeanbarrossilva.ongoing.feature.activityediting.extensions.onBackPressed
import kotlinx.parcelize.Parcelize

@Parcelize
internal sealed class ActivityEditingMode : Parcelable {
    abstract suspend fun save(activityRegistry: ActivityRegistry, props: ActivityEditingProps)

    open fun getOnBackPressedCallback(activity: Activity, props: ActivityEditingProps):
        OnBackPressedCallback? {
        return null
    }

    object Addition: ActivityEditingMode() {
        override suspend fun save(activityRegistry: ActivityRegistry, props: ActivityEditingProps) {
            activityRegistry.register(props.name)
        }
    }

    class Modification(val activity: ContextualActivity): ActivityEditingMode() {
        override suspend fun save(activityRegistry: ActivityRegistry, props: ActivityEditingProps) {
            activityRegistry.recorder.name(activity.id, props.name)
            activityRegistry.recorder.currentStatus(
                 activity.id,
                requireNotNull(props.currentStatus).toStatus()
            )
        }

        override fun getOnBackPressedCallback(activity: Activity, props: ActivityEditingProps):
            OnBackPressedCallback {
            return onBackPressed(isEnabled = !props.isApplicableTo(this.activity)) {
                MaterialDialog(activity).show {
                    title(R.string.feature_activity_editing_confirmation_dialog_title)
                    message(R.string.feature_activity_editing_confirmation_dialog_message)
                    positiveButton { activity.finish() }
                    negativeButton { dismiss() }
                }
            }
        }
    }
}