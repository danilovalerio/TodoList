package projetos.danilo.todolist.data

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

abstract class TodoListDatabase: RoomDatabase() {

    abstract fun todoListDao(): TodoListDao

    companion object {
        @Volatile
        private var INSTANCE: TodoListDatabase? = null

        fun getDatabase(ctx: Context): TodoListDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    ctx.applicationContext,
                    TodoListDatabase::class.java,
                    "todolist_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}