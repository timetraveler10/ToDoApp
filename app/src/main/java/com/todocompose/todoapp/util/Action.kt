package com.todocompose.todoapp.util

enum class Action {
    ADD, UPDATE, DELETE, DELETE_ALL, UNDO, NO_ACTION

}

fun String?.toAction(): Action {

    return when {

        this == "ADD" -> {
            Action.ADD
        }
        this == "UPDATE" -> {
            Action.UPDATE
        }
        this == "DELETE" -> {
            Action.DELETE
        }
        this == "UNDO" -> {
            Action.UNDO
        }
        this == "DELETE_ALL" -> {
            Action.DELETE_ALL
        }

        else -> {
            Action.NO_ACTION
        }


    }


}