package projetos.danilo.todolist.ui

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import projetos.danilo.todolist.databinding.ItemFilterDayBinding
import projetos.danilo.todolist.model.Task
import java.util.*
import java.text.SimpleDateFormat

class DatesFilterListAdapter : ListAdapter<String, DatesFilterListAdapter.DateFilterViewHolder>(DiffCallbackFilter()) {

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
        fun bind(item: String) {

            val c: Date = Calendar.getInstance().time
            val sdf: SimpleDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val dateFormatted = sdf.format(c)

            val itemDate: String = item
            val strDayOfWeek = formatStringToDayOfWeek(item)

            if(itemDate == dateFormatted) {
                binding.clDayFilter.setBackgroundColor(Color.BLACK)
                binding.tvDay.setTextColor(Color.WHITE)
                binding.tvDayOfWeek.setTextColor(Color.WHITE)
                binding.viewDivider.setBackgroundColor(Color.WHITE)

            } else {
                binding.clDayFilter.setBackgroundColor(Color.WHITE)
                binding.tvDay.setTextColor(Color.BLACK)
                binding.tvDayOfWeek.setTextColor(Color.BLACK)
                binding.viewDivider.setBackgroundColor(Color.BLACK)
            }

            binding.tvDayOfWeek.text = strDayOfWeek
            binding.tvDay.text = item.take(2)//.day.toString()
        }

        fun formatStringToDayOfWeek(dateItem: String): String {
            val sdf: SimpleDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val date: Date = sdf.parse(dateItem)
            val sdfDayOfWeek: SimpleDateFormat = SimpleDateFormat("dd/MM/yyyy E", Locale.getDefault())
            val dateFormatted = sdfDayOfWeek.format(date)
            val diaDaSemana = dateFormatted.takeLast(3)
            val diaDoMes = dateFormatted.take(2)
            return diaDaSemana
        }
    }
}

/**
 * Usado para adicionar regras de validação dos itens, que podemos personalizar se assim quiser
 */
class DiffCallbackFilter : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

}