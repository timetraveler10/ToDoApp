package com.todocompose.todoapp.ui.theme.view_models

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.todocompose.todoapp.data.models.Priority
import com.todocompose.todoapp.data.models.ToDoTask
import com.todocompose.todoapp.data.repo.DataStoreRepo
import com.todocompose.todoapp.data.repo.ToDoRepo
import com.todocompose.todoapp.util.Action
import com.todocompose.todoapp.util.RequestState
import com.todocompose.todoapp.util.SearchAppBarState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val repo: ToDoRepo,
    private val dataStoreRepo: DataStoreRepo
) : ViewModel() {
    /***************************** Observable App Data For UI **********************************/

    val id: MutableState<Int> = mutableStateOf(0)

    val title: MutableState<String> = mutableStateOf("")

    val description: MutableState<String> = mutableStateOf("")

    val priority: MutableState<Priority> = mutableStateOf(Priority.NONE)

    /***************************** Observable App Data For UI **********************************/

    /********************************* database search ********************************************/

    private val _searchedTasks = MutableStateFlow<RequestState<List<ToDoTask>>>(RequestState.Idle)
    val searchedTasks: StateFlow<RequestState<List<ToDoTask>>> = _searchedTasks

    /********************************* database search ********************************************/

    var searchAppBarState: MutableState<SearchAppBarState> =
        mutableStateOf(SearchAppBarState.CLOSED)

    val searchTextState: MutableState<String> = mutableStateOf("")


    private val _allTasks = MutableStateFlow<RequestState<List<ToDoTask>>>(RequestState.Idle)
    val allTasks: StateFlow<RequestState<List<ToDoTask>>> = _allTasks

    private val _selectedTask: MutableStateFlow<ToDoTask?> = MutableStateFlow(null)
    val selectedTask: MutableStateFlow<ToDoTask?> = _selectedTask

    // for observing

    val action: MutableState<Action> = mutableStateOf(Action.NO_ACTION)

    val lowPriorityTasks: StateFlow<List<ToDoTask>> =
        repo.sortByLowPriority.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = emptyList()
        )

    val highPriorityTasks: StateFlow<List<ToDoTask>> =
        repo.sortByHighPriority.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = emptyList()
        )


    private val _sortState =
        MutableStateFlow<RequestState<Priority>>(RequestState.Idle)
    val sortState: StateFlow<RequestState<Priority>> = _sortState
    /*************************************CRUD*******************************/
    fun getAllTasks() {
        _allTasks.value = RequestState.Loading
        try {
            viewModelScope.launch {

                repo.getAllTasks.collect {
                    _allTasks.value = RequestState.Success(it)
                }

            }
        } catch (e: Exception) {
            _allTasks.value = RequestState.Error(e)
        }

    }

    /****************************************SORTING*******************************************/



    fun readSortState() {
        _sortState.value = RequestState.Loading
        try {
            viewModelScope.launch {
                dataStoreRepo.readSortState
                    .map { Priority.valueOf(it) }
                    .collect {
                        _sortState.value = RequestState.Success(it)
                    }
            }
        } catch (e: Exception) {
            _sortState.value = RequestState.Error(e)
        }
    }

    fun persistSortState(priority: Priority) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepo.persistSortState(priority = priority)
        }
    }

    /****************************************SORTING*******************************************/


    fun searchDatabase(searchQuery: String) {
        _searchedTasks.value = RequestState.Loading
        try {
            viewModelScope.launch {

                repo.searchDatabase("%$searchQuery%").collect {
                    _searchedTasks.value = RequestState.Success(it)
                }

            }
        } catch (e: Exception) {
            _searchedTasks.value = RequestState.Error(e)
        }

        searchAppBarState.value = SearchAppBarState.TRIGGERED


    }


    private fun addTask() {
        viewModelScope.launch(Dispatchers.IO) {

            val toDoTask = ToDoTask(
                0,
                title = title.value,
                description = description.value,
                priority = priority.value
            )
            repo.addTask(toDoTask = toDoTask)
            searchAppBarState.value = SearchAppBarState.CLOSED

        }

    }

    private fun deleteData() {
        viewModelScope.launch {
            viewModelScope.launch(Dispatchers.IO) {
                val toDoTask = ToDoTask(
                    id = id.value,
                    title = title.value,
                    description = description.value,
                    priority = priority.value
                )
                repo.deleteTask(toDoTask = toDoTask)
            }

        }
    }

    private fun updateData() {
        viewModelScope.launch(Dispatchers.IO) {
            val toDoTask = ToDoTask(
                id = id.value,
                title = title.value,
                description = description.value,
                priority = priority.value
            )
            repo.updateTask(toDoTask = toDoTask)
        }
    }

    private fun deleteAllTasks() {
        viewModelScope.launch {

            repo.deleteAll()

        }
    }

    /*************************************CRUD*******************************/

    fun handleDatabaseAction(action: Action) {

        when (action) {

            Action.ADD -> {
                addTask()
            }
            Action.DELETE -> {
                deleteData()
            }
            Action.DELETE_ALL -> {
                deleteAllTasks()
            }
            Action.UPDATE -> {
                updateData()
            }
            Action.UNDO -> {
                addTask()
            }
            else -> {

            }

        }
        this.action.value = Action.NO_ACTION


    }

    fun getSelectedTask(taskId: Int) {
        viewModelScope.launch {
            repo.getSelectedTask(taskId = taskId).collect {
                _selectedTask.value = it

            }
        }
    }

    fun updateTaskFields(selectedTask: ToDoTask?) {


        if (selectedTask != null) {

            id.value = selectedTask.id
            title.value = selectedTask.title
            description.value = selectedTask.description
            priority.value = selectedTask.priority

        } else {

            id.value = 0
            title.value = ""
            description.value = ""
            priority.value = Priority.LOW
        }


    }

    fun validateFields(): Boolean = title.value.isNotEmpty() && description.value.isNotEmpty()

    fun updateTitle(newTitle: String) {
        if (newTitle.length < 20) {
            title.value = newTitle
        }
    }


}