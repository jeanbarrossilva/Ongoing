package com.jeanbarrossilva.ongoing.platform.designsystem.component.input.textfield.submitter

class TextFieldSubmitter internal constructor() {
    private var listeners = mutableListOf<OnSubmissionListener>()

    fun submit() {
        listeners.forEach(OnSubmissionListener::onSubmission)
    }

    internal fun addListener(listener: OnSubmissionListener) {
        listeners.add(listener)
    }

    internal fun removeListener(listener: OnSubmissionListener) {
        listeners.remove(listener)
    }
}