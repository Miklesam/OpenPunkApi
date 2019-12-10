package com.miklesam.openpunkapi.ui.category

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.miklesam.openpunkapi.R
import com.miklesam.openpunkapi.adapters.CategoryAdapter
import com.miklesam.openpunkapi.adapters.OnCategoryListener
import com.miklesam.openpunkapi.data.Beer

class CategoryFragment : Fragment(),OnCategoryListener {
    lateinit var mBeers:List<Beer>
    override fun onBeerCategoryClick(position: Int) {
        Log.w("ClickCategory", "positionClick= "+position)
        categoryViewModel.setBeerView(true)
        categoryViewModel.setMyBeer(mBeers.get(position))
    }

    override fun onCategoryClick(category: String) {
        Log.w("ClickCategory", "categoryClick= "+category)
        mAdapter.displayLoading()
        categoryViewModel.setLoading(true)
        categoryViewModel.setCategory(false)
        categoryViewModel.beerWithFood(1, "5", category)
    }

    private lateinit var categoryViewModel: CategoryViewModel
    lateinit var mAdapter:CategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        categoryViewModel =
            ViewModelProviders.of(this).get(CategoryViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_category, container, false)
        val recyclerCategory=root.findViewById<RecyclerView>(R.id.recycler_category)
        val scrolView = root.findViewById<ScrollView>(R.id.scrollContainer)
        val beerName =root.findViewById<TextView>(R.id.beer_name)
        val image = root.findViewById<ImageView>(R.id.beer_image)
        val beerTagLine=root.findViewById<TextView>(R.id.beer_tagline)
        val beerDescription=root.findViewById<TextView>(R.id.beer_description)
        val mbeer_id=root.findViewById<TextView>(R.id.beer_id)

        mAdapter = CategoryAdapter(this)
        recyclerCategory.setLayoutManager(LinearLayoutManager(context))
        recyclerCategory.setAdapter(mAdapter)
        mAdapter.displayCategories()
        recyclerCategory.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!categoryViewModel.isCategory()!! &&!recyclerCategory.canScrollVertically(1)) {
                    //search the next page
                    categoryViewModel.searchNextPage()
                    categoryViewModel.setLoading(true)
                    Log.w("Scroll","End")
                }
            }
        })


        categoryViewModel.getBeerFood().observe(this, Observer {
            if(it!=null){
                mBeers=it
                categoryViewModel.setLoading(false)
                mAdapter.setmBeer(it)
            }

        })

        categoryViewModel.isCategoryDisplay().observe(this, Observer {
          if(it){
              mAdapter.displayCategories()
          }
        })

        categoryViewModel.isExhausted().observe(this, Observer {
            if(it){
               Log.w("Exhausted","Exhausted In Fragment")
                mAdapter.setQueryExhausted()
            }
        })

        categoryViewModel.isBeerViewing().observe(this, Observer {
            if(it){
                recyclerCategory.visibility=GONE
                scrolView.visibility= VISIBLE
            }else{
                recyclerCategory.visibility=VISIBLE
                scrolView.visibility= GONE
            }
        })

        categoryViewModel.getMyBeer().observe(this, Observer {
            beerName.text=it.name
            mbeer_id.text=it.id
            val ttt=it.image_url

            if(it.image_url!=null){

                val requestOptions = RequestOptions()
                    .placeholder(R.color.white)
                Glide.with(this)
                    .setDefaultRequestOptions(requestOptions)
                    .load(ttt)
                    .into(image)
            }else{
                image.setImageResource(R.drawable.baltic9)
            }


            beerTagLine.text=it.tagline
            beerDescription.text=it.description
        })






        return root
    }
}