package com.jeanbarrossilva.ongoing.feature.activitydetails.extensions

import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.jeanbarrossilva.ongoing.feature.activitydetails.domain.ContextStatus
import com.jeanbarrossilva.ongoing.platform.designsystem.configuration.ContentAlpha
import com.pushpal.jetlime.data.JetLimeItemsModel
import com.pushpal.jetlime.data.config.JetLimeItemConfig

/** Converts the given [ContextStatus] into a [JetLimeItemsModel.JetLimeItem]. **/
@Composable
internal fun ContextStatus.toJetLimeItem(): JetLimeItemsModel.JetLimeItem {
    val contentColor = LocalContentColor.current
    val descriptionColor = contentColor.copy(ContentAlpha.MEDIUM)

    return JetLimeItemsModel.JetLimeItem(
        title = title,
        description = changeDate,
        jetLimeItemConfig = JetLimeItemConfig(
            titleColor = contentColor,
            descriptionColor = descriptionColor,
            iconColor = MaterialTheme.colorScheme.background,
            iconBorderColor = contentColor
        )
    )
}