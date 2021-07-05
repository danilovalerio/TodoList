package projetos.danilo.todolist.ui

import android.graphics.Color
import android.util.Log
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

    private var dateSelected: String = ""

    var listenerClick: (String) -> Unit = {}
    var dateSelectedClean: () -> Unit = {}

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

            formatChosenDate(itemDate, dateSelected)

            binding.tvDayOfWeek.text = strDayOfWeek
            binding.tvDay.text = item.take(2)

            binding.cvDayFilter.setOnClickListener {
                listenerClick(item)
                dateSelected = item
                notifyDataSetChanged()
            }

            dateSelectedClean = {
                dateSelected = ""
                notifyDataSetChanged()
            }
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

        fun formatChosenDate(date: String, currentDate: String) {
            if(date == currentDate) {
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