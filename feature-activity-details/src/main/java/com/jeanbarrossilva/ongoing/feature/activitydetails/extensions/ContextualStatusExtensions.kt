package com.jeanbarrossilva.ongoing.feature.activitydetails.extensions

import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.jeanbarrossilva.ongoing.context.registry.domain.ContextualStatus
import com.jeanbarrossilva.ongoing.feature.activitydetails.ActivityDetailsModel
import com.jeanbarrossilva.ongoing.platform.designsystem.configuration.ContentAlpha
import com.pushpal.jetlime.data.JetLimeItemsModel
import com.pushpal.jetlime.data.config.JetLimeItemConfig

@Composable
internal fun ContextualStatus.toJetLimeItem(): JetLimeItemsModel.JetLimeItem {
    val contentColor = LocalContentColor.current
    val descriptionColor = contentColor.copy(ContentAlpha.MEDIUM)
    val description = ActivityDetailsModel.getDisplayableCreationMomentOf(this)

    return JetLimeItemsModel.JetLimeItem(
        title = title,
        description = description,
        jetLimeItemConfig = JetLimeItemConfig(
            titleColor = contentColor,
            descriptionColor = descriptionColor,
            iconColor = MaterialTheme.colorScheme.background,
            iconBorderColor = contentColor
        )
    )
}