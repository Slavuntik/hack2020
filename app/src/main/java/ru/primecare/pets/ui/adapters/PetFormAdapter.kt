package ru.primecare.pets.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import ru.primecare.pets.R
import ru.primecare.pets.data.FormField
import ru.primecare.pets.databinding.FormRowItemBinding

class PetFormAdapter(val data:MutableList<FormField>): RecyclerView.Adapter<PetFormAdapter.ViewHolder>() {
    data class ViewHolder(val view: View):RecyclerView.ViewHolder(view){
        val textLayout:TextInputLayout = view.findViewById(R.id.text_layout)
        val autoLayout:TextInputLayout = view.findViewById(R.id.auto_layout)
        val text:TextInputEditText = view.findViewById(R.id.text)
        val auto:AutoCompleteTextView = view.findViewById(R.id.auto)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.form_row_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(data[position]){
            holder.autoLayout.visibility = View.GONE
            holder.textLayout.hint = cellHeader
            holder.text.setText(cellValue)
            if(cellIndex==0 || cellIndex==14){
                holder.text.isEnabled = false
            }
        }
    }

    override fun getItemCount(): Int = data.size
}