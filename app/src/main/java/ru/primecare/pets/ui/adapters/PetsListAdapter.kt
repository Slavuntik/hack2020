package ru.primecare.pets.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import ru.primecare.pets.R
import ru.primecare.pets.data.Net
import ru.primecare.pets.utils.decodeBase64ToByteArray
import ru.primecare.pets.ui.entities.PetListItemInfo

class PetsListAdapter(val data:MutableList<PetListItemInfo>): RecyclerView.Adapter<PetsListAdapter.ViewHolder>() {

    var onItemClick:(item:PetListItemInfo)->Unit ={}
    val net = Net()
    class ViewHolder(val view: View): RecyclerView.ViewHolder(view) {
        val image:ImageView = view.findViewById(R.id.image)
        val code:TextView = view.findViewById(R.id.code)
        val name:TextView = view.findViewById(R.id.name)
        val species:TextView = view.findViewById(R.id.species)
        val breed:TextView = view.findViewById(R.id.breed)
        val root: CardView = view.rootView as CardView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.pets_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(data[position]){
            holder.name.text = name
            holder.code.text = cardNumber.toLowerCase()
            holder.breed.text = breed
            holder.species.text = species
            if(photo.isNotEmpty()){
                val img = photo.decodeBase64ToByteArray()
                    Glide.with(holder.image.context)
                        .load(img).circleCrop()
                        .placeholder(R.drawable.image_placeholder)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .into(holder.image)
            }

        }
        holder.root.setOnClickListener { onItemClick(data[position]) }
    }

    override fun getItemCount(): Int = data.size
}