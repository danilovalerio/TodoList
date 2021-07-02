package projetos.danilo.todolist.model

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.parcelize.Parcelize
import projetos.danilo.todolist.utils.Constants

@Entity(tableName = Constants.TODO_LIST_TABLE)
@Parcelize
data class Task(
    val id: Int = 0,
    val title: String,
    val description: String,
    val date: String,
    val hour: String
): Parcelable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Task

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id
    }
}
