package com.todocompose.todoapp.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.todocompose.todoapp.util.Constants.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class ToDoTask(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0 ,
    val title:String ,
    val description:String ,
    val priority: Priority

)
