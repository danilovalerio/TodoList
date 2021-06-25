package projetos.danilo.todolist.data

import projetos.danilo.todolist.model.Task

object TaskDataSource {
    private val list = arrayListOf<Task>(
        Task(1, "Titulo da minha tarefa", "descricao do item", "01/01/2021", "18:00")
    )

    fun getList() = list.toList()

    fun insertTask(task: Task) {
        if (task.id == 0) {
            list.add(task.copy(id = list.size + 1))
        } else {
            list.remove(task)
            list.add(task)
        }
    }

    fun findById(taskId: Int) = list.find { it.id == taskId }
}