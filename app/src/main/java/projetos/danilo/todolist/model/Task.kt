package projetos.danilo.todolist.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import projetos.danilo.todolist.utils.Constants

@Entity(tableName = "todolist_table")
@Parcelize
data class Task(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var title: String,
    var description: String,
    var date: String,
    var hour: String
): Parcelable
