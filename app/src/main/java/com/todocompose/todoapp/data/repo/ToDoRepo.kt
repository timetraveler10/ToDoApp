package com.todocompose.todoapp.data.repo

import com.todocompose.todoapp.data.models.ToDoDao
import com.todocompose.todoapp.data.models.ToDoTask
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class ToDoRepo @Inject constructor(private val toDoDao: ToDoDao) {

    val getAllTasks: Flow<List<ToDoTask>> = toDoDao.getAllTasks()
    val sortByLowPriority: Flow<List<ToDoTask>> = toDoDao.sortByLowPriority()
    val sortByHighPriority: Flow<List<ToDoTask>> = toDoDao.sortByHighPriority()


    fun getSelectedTask(taskId: Int): Flow<ToDoTask> = toDoDao.getSelectedTask(taskId)

    suspend fun addTask(toDoTask: ToDoTask) {
        toDoDao.addTask(task = toDoTask)
    }

    suspend fun updateTask(toDoTask: ToDoTask) {
        toDoDao.updateTask(task = toDoTask)
    }

    suspend fun deleteTask(toDoTask: ToDoTask) {
        toDoDao.deleteTask(task = toDoTask)
    }

    suspend fun deleteAll() {
        toDoDao.deleteAllTasks()
    }

    fun searchDatabase(searchQuery: String): Flow<List<ToDoTask>> =
        toDoDao.searchDatabase(searchQuery = searchQuery)

}