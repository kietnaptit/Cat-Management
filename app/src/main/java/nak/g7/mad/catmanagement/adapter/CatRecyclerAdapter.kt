package nak.g7.mad.catmanagement.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import nak.g7.mad.catmanagement.R
import nak.g7.mad.catmanagement.db.DBHandler
import nak.g7.mad.catmanagement.model.Cat

class CatRecyclerAdapter(val cats : List<Cat>, val itemClickListener: (Cat) -> Unit) : RecyclerView.Adapter<CatRecyclerAdapter.MyViewHolder>() {
    private var deleteClickListener: ((Cat) -> Unit)? = null

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var catImage = view.findViewById<ImageView>(R.id.catImage)
        var catName = view.findViewById<TextView>(R.id.nameField)
        var catPrice = view.findViewById<TextView>(R.id.priceField)
        var catDes = view.findViewById<TextView>(R.id.desField)
        var delBtn = view.findViewById<Button>(R.id.delBtn)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.single_cat, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return cats.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val cat = cats[position]
        holder.catName.text = cat.name
        holder.catDes.text = cat.des
        holder.catPrice.text = cat.price.toString()
        holder.catImage.setImageResource(cat.imageID)
        holder.itemView.setOnClickListener {
            itemClickListener(cat)
        }
        holder.delBtn.setOnClickListener {
            deleteClickListener?.invoke(cat)
        }

    }

    fun setOnDeleteClickListener(listener: (Cat) -> Unit) {
        deleteClickListener = listener
    }


}