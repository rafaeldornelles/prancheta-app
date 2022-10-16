package com.rafael.featurebriefing.presentation.ui.components

import androidx.compose.runtime.Composable
import com.rafael.featurebriefing.domain.entity.BriefingFormQuestion
import com.rafael.featurebriefing.domain.entity.QuestionType

@Composable
fun BriefingFormQuestion.mapToComponent() {
    when(this.question.questionType) {
        QuestionType.TEXT -> {

        }
    }
}