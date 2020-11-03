package com.satriaamrudito.thehappyplaceap.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.satriaamrudito.thehappyplaceap.R
import com.satriaamrudito.thehappyplaceap.activities.AddHappyPlace
import com.satriaamrudito.thehappyplaceap.activities.MainActivity
import com.satriaamrudito.thehappyplaceap.handler.DatabaseHandler
import com.satriaamrudito.thehappyplaceap.model.HappyPlacesModel
import kotlinx.android.synthetic.main.item_happy_places.view.*

open class HappyPlacesAdapter(
    private val context: Context,
    private var list: ArrayList<HappyPlacesModel>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_happy_places, parent, false)
        )
    }

    private var onClickListener : OnClickListener? = null

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = list[position]
        if (holder is MyViewHolder) {
            holder.itemView.iv_place_image
                .setImageURI(Uri.parse(model.image))
            holder.itemView.tvTitle.text = model.title
            holder.itemView.tvDescription.text =
                model.description

            holder.itemView.setOnClickListener{
                if (onClickListener!=null){
                    onClickListener!!.onClick(position,model)
                }
            }
        }
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    interface OnClickListener {
        fun onClick(position: Int, model: HappyPlacesModel)
    }

    override fun getItemCount(): Int {
        return list.size
    }
    fun notifyEditItem(activity: Activity, position: Int, requestCode: Int) {
        val int = Intent(context, AddHappyPlace::class.java)
        int.putExtra(MainActivity.EXTRA_PLACE_DETAIL, list[position])
        activity.startActivityForResult(int,requestCode)
        notifyItemChanged(position)
    }

    fun removeAt(position: Int){
        val dbHandler = DatabaseHandler(context)
        val isDeleted = dbHandler.deleteHappyPlace(list[position])
        if (isDeleted>0){
            list.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    private class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)

}
