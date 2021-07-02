package projetos.danilo.todolist.data

import androidx.room.RoomDatabase

abstract class TodoListDatabase: RoomDatabase() {

    abstract fun todoListDao():
}