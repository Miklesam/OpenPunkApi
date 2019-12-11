package com.miklesam.openpunkapi.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.miklesam.openpunkapi.R
import com.miklesam.openpunkapi.data.Beer
import kotlinx.android.synthetic.main.favorite_beer_item.view.*
import java.util.ArrayList

class FavoriteAdapter(onBeerListener: OnBeerListener) :RecyclerView.Adapter<FavoriteAdapter.BeerHolder>(){
    private var beers:List<Beer> = ArrayList()
    private var beersOld:List<Beer> = ArrayList()
    private val mOnBeerListener=onBeerListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeerHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.favorite_beer_item,parent,false);
        return BeerHolder(itemView,mOnBeerListener)
    }

    override fun getItemCount(): Int {
        return beers.size
    }

    override fun onBindViewHolder(holder: BeerHolder, position: Int) {
        val currentBeer: Beer = beers.get(position)
        holder.textViewTitle.text = currentBeer.name

        val requestOptions = RequestOptions()
            .placeholder(R.color.white)
        Glide.with(holder.itemView.context)
            .setDefaultRequestOptions(requestOptions)
            .load(currentBeer.image_url)
            .into(holder.mysqlima)
    }

    fun getBeerAt(position:Int):Beer{
        return beers.get(position)
    }

    fun setBeers(beers:List<Beer>){
        beersOld=this.beers
        this.beers=beers
        updatelist(beersOld,this.beers)
    }

   private fun updatelist( old:List<Beer>,new:List<Beer>){
        val callback = DiffCallback(old, new)
        DiffUtil.calculateDiff(callback).dispatchUpdatesTo(this)
    }

     class BeerHolder(itemView: View, var beerListener: OnBeerListener) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var textViewTitle: TextView
        var mysqlima: ImageView

        init {
            textViewTitle = itemView.text_view_title
            mysqlima = itemView.sqlima
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            beerListener.onBeerClick(adapterPosition)
        }
    }


}