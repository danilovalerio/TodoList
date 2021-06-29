package projetos.danilo.todolist.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import projetos.danilo.todolist.databinding.ItemFilterDayBinding
import projetos.danilo.todolist.model.DateFilter
import projetos.danilo.todolist.model.Task

class DatesFilterListAdapter : ListAdapter<DateFilter, DatesFilterListAdapter.DateFilterViewHolder>(DiffCallbackFilter()) {

    //Create functions empty type unit with param Task
    var listenerEdit : (Task) -> Unit = {}
    var listenerDelete : (Task) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DateFilterViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemFilterDayBinding.inflate(inflater, parent, false)
        return DateFilterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DateFilterViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class DateFilterViewHolder(
        private val binding: ItemFilterDayBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: DateFilter) {
            binding.tvDay.text = item.day.toString()
            binding.tvDayOfWeek.text = item.dayOfWeek
        }
    }
}

/**
 * Usado para adicionar regras de validação dos itens, que podemos personalizar se assim quiser
 */
class DiffCallbackFilter : DiffUtil.ItemCallback<DateFilter>() {
    override fun areItemsTheSame(oldItem: DateFilter, newItem: DateFilter): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: DateFilter, newItem: DateFilter): Boolean {
        return oldItem.day == newItem.day
    }

}