package dev.illwiz.tada.data.utils

data class TaskState<out T>(val status: Status, val data: T?, val message: String?) {

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING,
        NOTHING
    }

    companion object {
        fun <T> success(data: T): TaskState<T> {
            return TaskState(Status.SUCCESS, data, null)
        }

        fun <T> error(message: String, data: T? = null): TaskState<T> {
            return TaskState(Status.ERROR, data, message)
        }

        fun <T> loading(data: T? = null): TaskState<T> {
            return TaskState(Status.LOADING, data, null)
        }

        fun <T> nothing(): TaskState<T> {
            return TaskState(Status.NOTHING, null, null)
        }
    }
}