package projetos.danilo.todolist.model

import java.time.DayOfWeek

data class DateFilter(
    val day: Int,
    val month: Int,
    val year: Int,
    val dayOfWeek: String? = "-"
)
