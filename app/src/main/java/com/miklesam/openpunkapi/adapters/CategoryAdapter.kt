package com.miklesam.openpunkapi.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.miklesam.openpunkapi.R
import com.miklesam.openpunkapi.data.Beer
import com.miklesam.openpunkapi.utils.Constants
import de.hdodenhof.circleimageview.CircleImageView
import java.util.ArrayList

class CategoryAdapter(onCategoryListener: OnCategoryListener) :RecyclerView.Adapter<RecyclerView.ViewHolder>(){


    private val mOnCategoryListener=onCategoryListener
    private var mBeer: List<Beer>? = null
    companion object {
        const val BEER_TYPE = 1
        const val LOADING_TYPE = 2
        const val CATEGORY_TYPE = 3
        const val END_TYPE = 4

        const val CATEGORY_IMAGE_DIR = "android.resource://com.miklesam.openpunkapi/drawable/"
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var view: View? = null
        return when (viewType) {
            CATEGORY_TYPE -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.layout_category_list_item, parent, false)
                return CategoryViewHolder(view, mOnCategoryListener)
            }
            BEER_TYPE-> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.layout_beer_list_item, parent, false)
                return BeerCategoryViewHolder(view, mOnCategoryListener)
            }
            LOADING_TYPE->{
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.layout_loading_list_item, parent, false)
                return LoadingViewHolder(view!!)
            }
            else -> CategoryViewHolder(view!!, mOnCategoryListener)
        }
    }

    override fun getItemCount(): Int {
        return mBeer!!.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemViewType = getItemViewType(position)
        var myHolder =holder
        when(itemViewType){
            CATEGORY_TYPE->{
                myHolder=holder as CategoryViewHolder
                val requestOptions = RequestOptions()
                    .placeholder(R.drawable.ic_launcher_background)
                val path = Uri.parse(
                    CATEGORY_IMAGE_DIR + mBeer?.get(position)?.image_url
                )
                Glide.with(holder.itemView.context)
                    .setDefaultRequestOptions(requestOptions)
                    .load(path)
                    .into(myHolder.categoryImage)
                myHolder.categoryTitle.setText(mBeer?.get(position)?.name)
            }
            BEER_TYPE->{
                myHolder=holder as BeerCategoryViewHolder
                val requestOptions = RequestOptions()
                    .placeholder(R.drawable.ic_launcher_background)

                Glide.with(holder.itemView.context)
                    .setDefaultRequestOptions(requestOptions)
                    .load(mBeer?.get(position)?.image_url)
                    .into((myHolder).imageView)

                (myHolder).name.setText(mBeer?.get(position)?.name)
                (myHolder).tagline.setText(mBeer?.get(position)?.tagline)
                myHolder.beerId.text= mBeer!!.get(position).id
            }

        }
    }


    fun setmBeer(beers: List<Beer>) {
        mBeer = beers
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        if(mBeer?.get(position)?.id.equals("-1")){
            return CATEGORY_TYPE
        } else if (mBeer?.get(position)?.name.equals("LOADING...")) {
            return LOADING_TYPE
        }else{
            return BEER_TYPE
        }
    }


    fun displayCategories() {
        val categories = ArrayList<Beer>()
        for (i in 0 until Constants.DEFAULT_SEARCH_CATEGORIES.size) {
            val beer = Beer("","","","","")
            beer.name=(Constants.DEFAULT_SEARCH_CATEGORIES[i])
            beer.image_url=(Constants.DEFAULT_SEARCH_CATEGORY_IMAGES[i])
            beer.id="-1"
            categories.add(beer)
        }
        mBeer = categories
        notifyDataSetChanged()
    }


    fun displayLoading() {
        if (!isLoading()) {
            val beer = Beer("","","","","")
            beer.name="LOADING..."
            val loadinglist = ArrayList<Beer>()
            loadinglist.add(beer)
            mBeer = loadinglist
            notifyDataSetChanged()
        }
    }

    private fun isLoading(): Boolean {
        if (mBeer != null) {
            if (mBeer!!.isNotEmpty()) {
                if (mBeer!!.get(mBeer!!.size - 1).name.equals("LOADING...")) {
                    return true
                }
            }
        }
        return false
    }


    class CategoryViewHolder(itemView: View, internal var listener: OnCategoryListener) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {
        internal var categoryImage: CircleImageView
        internal var categoryTitle: TextView

        init {
            categoryImage = itemView.findViewById(R.id.category_image)
            categoryTitle = itemView.findViewById(R.id.category_Title)

            itemView.setOnClickListener(this)

        }
        override fun onClick(view: View) {
            listener.onCategoryClick(categoryTitle.text.toString())
        }
    }

    class BeerCategoryViewHolder(itemView: View, internal var onRecipeListener: OnCategoryListener) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {
        internal var name: TextView
        internal var tagline: TextView
        internal var beerId: TextView
        internal var imageView: AppCompatImageView

        init {
            name = itemView.findViewById(R.id.beerName)
            tagline = itemView.findViewById(R.id.beerTagline)
            beerId = itemView.findViewById(R.id.beerIid)
            imageView = itemView.findViewById(R.id.beer_image)

            itemView.setOnClickListener(this)


        }

        override fun onClick(view: View) {
            onRecipeListener.onBeerCategoryClick(adapterPosition)
        }
    }

    class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}