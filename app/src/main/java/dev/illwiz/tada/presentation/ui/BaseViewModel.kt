package dev.illwiz.tada.presentation.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.illwiz.tada.data.utils.AppUtils
import dev.illwiz.tada.data.utils.TaskState
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.*
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

open class Task

data class Error(
	val throwable: Throwable,
	val message: String
)

abstract class BaseViewModel(
	protected val ioDispatcher: CoroutineDispatcher
):ViewModel() {
	/**
	 * Using [Channel] not [ConflatedBroadcastChannel] because we don't want to emit last error value
	 * On a new Subscriber / Subscription
	 */
	protected val _error = Channel<Error>()
	val error get() = _error.receiveAsFlow()

	private val defaultExceptionHandler = CoroutineExceptionHandler { _, exception ->
		handleError(exception)
	}
	protected val ioContext = ioDispatcher + defaultExceptionHandler
	private val taskResult = hashMapOf<Class<out Task>,MutableLiveData<TaskState<Any>>>()

	init {
		for(action in getTasks()) {
			taskResult[action::class.java] = MutableLiveData()
		}
	}

	open fun getTasks():List<Task> {
		return emptyList()
	}

	/**
	 * Use this method to launch Coroutines with viewModelScope
	 * With this method the viewModelScope already integrated with an exception handler
	 */
	protected fun launchSafe(context: CoroutineContext = EmptyCoroutineContext, block: suspend CoroutineScope.() -> Unit) {
		viewModelScope.launch(context = context + defaultExceptionHandler, block = block)
	}

	protected fun <T> observeFlow(flow: Flow<T>, flowOn: CoroutineContext = ioContext,
			started: SharingStarted = SharingStarted.Eagerly, observer: suspend (T) -> Unit) {
		flow
			.catch { e -> handleError(e) }
			.onEach {
				try {
					observer.invoke(it)
				} catch (e:Exception) {
					handleError(e)
				}
			}
			.flowOn(flowOn)
			.shareIn(viewModelScope,started = started)
	}

	private fun handleError(throwable: Throwable) {
		throwable.printStackTrace()
		_error.offer(Error(throwable,AppUtils.getErrorMessage(throwable)))
	}

	inline fun <reified TASK:Task,RESULT> taskResult(): MutableLiveData<TaskState<RESULT>> {
		return `access$taskResult`[TASK::class.java] as MutableLiveData<TaskState<RESULT>>
	}

	/**
	 * This method created so Fragment/Activity that observes the task could also get the task status like "Loading/Error/Success"
	 * Hence the result of the task are wrapped using [TaskState] class to provide the "Loading/Error/Success" state
	 * This function also handles Error, any errors will be posted to [_error] channel
	 */
	inline fun <reified TASK:Task,RESULT> launchTask(context: CoroutineContext = EmptyCoroutineContext,noinline block: suspend CoroutineScope.() -> RESULT):Job {
		return viewModelScope.launch(Dispatchers.Main) {
			taskResult<TASK,RESULT>().value = TaskState.loading()
			try {
				val result = withContext(context) {
					block.invoke(this)
				}
				taskResult<TASK,RESULT>().value = TaskState.success(result)
			} catch (e:Exception) {
				e.printStackTrace()
				val errMessage = AppUtils.getErrorMessage(e)
				`access$_error`.offer(Error(e, errMessage))
				taskResult<TASK,RESULT>().value = TaskState.error(message = errMessage)
			} finally {
				taskResult<TASK,RESULT>().value = TaskState.nothing()
			}
		}
	}

	@PublishedApi
	internal val `access$taskResult`: HashMap<Class<out Task>, MutableLiveData<TaskState<Any>>> get() = taskResult

	@PublishedApi
	internal val `access$_error`: Channel<Error> get() = _error
}