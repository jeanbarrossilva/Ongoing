package com.jeanbarrossilva.ongoing.core.session

abstract class SessionManager {
    /**
     * [OnSessionChangeListener]s that'll get notified of changes to the current [Session].
     *
     * @see session
     **/
    private val listeners = mutableListOf<OnSessionChangeListener>()

    /** Gets the [Session] that's the current one. **/
    abstract fun session(): Session

    /**
     * Attaches the given [callback] and calls it whenever the current [Session] changes to one
     * whose type is [T]. Returns the underlying [OnSessionChangeListener] that runs the [callback]
     * when it's triggered.
     *
     * @param callback Operation to run in the underlying [OnSessionChangeListener] that'll be
     * attached.
     **/
    inline fun <reified T: Session> attach(noinline callback: suspend (T) -> Unit):
        OnSessionChangeListener {
        val listener = OnSessionChangeListener { if (it is T) callback(it) }
        attach(listener)
        return listener
    }

    /**
     * Attaches the [listener] and notifies it whenever the current [Session] changes.
     *
     * @param listener [OnSessionChangeListener] to attach.
     * @see session
     **/
    fun attach(listener: OnSessionChangeListener) {
        listeners.add(listener)
    }

    /**
     * Detaches the [listener] and unsubscribes it from changes made to the current [Session].
     *
     * @param listener [OnSessionChangeListener] to detach.
     * @see session
     **/
    fun detach(listener: OnSessionChangeListener) {
        listeners.remove(listener)
    }

    /**
     * Sets [session] as the current [Session] and notifies the currently attached [listeners].
     *
     * @param session [Session] to replace the current one by.
     **/
    protected suspend fun session(session: Session) {
        onSession(session)
        notifyListeners(session)
    }

    /**
     * Callback for when [session] is being set as the current [Session].
     *
     * @param session [Session] that's replacing the current one.
     **/
    protected abstract fun onSession(session: Session)

    /**
     * Notifies the attached [listeners] of the fact that [session] is now the current [Session].
     *
     * @param session [Session] that's been set as the current one.
     **/
    private suspend fun notifyListeners(session: Session) {
        listeners.forEach {
            it.onSessionChange(session)
        }
    }
}