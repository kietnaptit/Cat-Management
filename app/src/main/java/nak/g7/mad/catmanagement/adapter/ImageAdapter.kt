package nak.g7.mad.catmanagement.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import nak.g7.mad.catmanagement.R

class ImageAdapter(context: Context, val imageResources: Array<Int>) : ArrayAdapter<Int>(context, R.layout.image_spinner_item, imageResources){
    val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createViewFromResource(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createViewFromResource(position, convertView, parent)
    }

    private fun createViewFromResource(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        if (view == null) {
            view = inflater.inflate(R.layout.image_spinner_item, parent, false)
        }

        val imageView = view?.findViewById<ImageView>(R.id.spinnerImageView)
        imageView?.setImageResource(imageResources[position])

        return view!!
    }
}