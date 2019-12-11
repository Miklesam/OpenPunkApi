package com.miklesam.openpunkapi.ui.category

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.miklesam.openpunkapi.R
import com.miklesam.openpunkapi.adapters.CategoryAdapter
import com.miklesam.openpunkapi.adapters.CategoryAdapter.Companion.CATEGORY_IMAGE_DIR
import com.miklesam.openpunkapi.adapters.OnCategoryListener
import com.miklesam.openpunkapi.data.Beer
import com.miklesam.openpunkapi.data.DownloadAndSaveImageTask
import com.miklesam.openpunkapi.utils.Constants.PER_PAGE

class CategoryFragment : Fragment(),OnCategoryListener {
    lateinit var mBeers:List<Beer>
    private lateinit var my_beer: Beer
    override fun onBeerCategoryClick(position: Int) {
        categoryViewModel.setcategoryViewInt(2)
        categoryViewModel.setAppBar(mBeers.get(position).name)
        categoryViewModel.setMyBeer(mBeers.get(position))

    }

    override fun onCategoryClick(category: String) {
        mAdapter?.displayLoading()
        categoryViewModel.setAppBar(category)
        categoryViewModel.setCategory(category)
        categoryViewModel.setLoading(true)
        categoryViewModel.setcategoryViewInt(1)
        categoryViewModel.beerWithFood(1, PER_PAGE, category)
    }

    private lateinit var categoryViewModel: CategoryViewModel
     var mAdapter:CategoryAdapter?=null
     var searchView:SearchView?=null

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
        val arrow=root.findViewById<ImageView>(R.id.arrow_bttn)
        val favorite=root.findViewById<ImageView>(R.id.add_to_favorite)
        val textBar=root.findViewById<TextView>(R.id.text_bar)
        searchView=root.findViewById(R.id.search_view)
        val datebrewed=root.findViewById<TextView>(R.id.date)
        arrow.setOnClickListener {
            if(categoryViewModel.getViewIntCategory()==2){
                categoryViewModel.setAppBar(categoryViewModel.getCategory())
            }else if (categoryViewModel.getViewIntCategory()==1){
                categoryViewModel.setErrorState(false)
                categoryViewModel.clear()
            }
            categoryViewModel.setcategoryViewInt(categoryViewModel.getViewIntCategory()!!-1)
            categoryViewModel.setFavoriteChoose(false)
            categoryViewModel.setFavorite(false)
        }

        mAdapter = CategoryAdapter(this)
        recyclerCategory.setLayoutManager(LinearLayoutManager(context))
        recyclerCategory.setAdapter(mAdapter)
        //mAdapter.displayCategories()
        initSearchView()
        mAdapter?.displayLoading()
        recyclerCategory.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!categoryViewModel.getErrorState()&&(categoryViewModel.getViewIntCategory()==1)&&!recyclerCategory.canScrollVertically(1)) {
                    //search the next page
                    categoryViewModel.searchNextPage()
                    categoryViewModel.setLoading(true)
                }
            }
        })

        categoryViewModel.getBeerFood().observe(this, Observer {
            if(it!=null){
                arrow.visibility= VISIBLE
                mBeers=it
                categoryViewModel.setLoading(false)
                mAdapter?.setmBeer(it)
            }

        })

        categoryViewModel.isExhausted().observe(this, Observer {
            if(it){
                 mAdapter?.setQueryExhausted()
            }
        })

        categoryViewModel.isWhatView().observe(this, Observer {
            if(it==2){
                searchView?.visibility= GONE
                recyclerCategory.visibility=GONE
                scrolView.visibility= VISIBLE
            }else if(it==1){
                searchView?.visibility= GONE
                recyclerCategory.visibility=VISIBLE
                scrolView.visibility= GONE
            }else if(it==0){
                searchView?.visibility= VISIBLE
                categoryViewModel.clear()
                mAdapter?.displayCategories()
                arrow.visibility= INVISIBLE
                categoryViewModel.setAppBar(getString(R.string.category_bar))
            }
        })

        categoryViewModel.getMyBar().observe(this, Observer { text ->
            textBar.text=text
        })

        categoryViewModel.getMyBeer().observe(this, Observer {
            beerName.text=it.name
            mbeer_id.text=it.id
            val ttt=it.image_url
            val concat=getString(R.string.beerDate)+it.first_brewed
            datebrewed.text=concat
            my_beer=it
            categoryViewModel.checkId(it.id).observe(this, Observer { if(it!=null){
                categoryViewModel.setFavorite(true)
            }else{
                categoryViewModel.setFavorite(false)
            } })

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

        categoryViewModel.isFavorite().observe(this, Observer { isLoading ->
            if(isLoading) {
                favorite.setImageResource(R.drawable.heaert_fool)
            }else{
                favorite.setImageResource(R.drawable.heaert_empty)
            }
        })

        favorite.setOnClickListener {
            if(categoryViewModel.isThisFavorite()!!){
                favorite.isEnabled=false
                Toast.makeText(context, "Already Favorite Beer!", Toast.LENGTH_SHORT).show()
            }else{
                categoryViewModel.setFavorite(true)
                categoryViewModel.setFavoriteChoose(true)
                if(my_beer.image_url!=null){
                    //this.context?.let {
                    //        it1 -> DownloadAndSaveImageTask(it1,my_beer.id).execute(my_beer.image_url)
                     //   val path = Uri.parse(context?.filesDir.toString()+"/Images/").path+my_beer.id+".png"
                     //   my_beer.image_url=path}
                }else{
                    my_beer.image_url=CATEGORY_IMAGE_DIR+"baltic9"
                }
                categoryViewModel.insertBeer(my_beer)
            }


        }

        categoryViewModel.getError().observe(this, Observer {
            if(it!=null){
                categoryViewModel.setErrorState(true)
                mAdapter?.setError(it)
                arrow.visibility= VISIBLE
                categoryViewModel.setLoading(false)
             }

        })


        return root
    }


    private fun initSearchView() {
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                mAdapter?.displayLoading()
                categoryViewModel.setAppBar(query)
                categoryViewModel.setCategory(query)
                categoryViewModel.setLoading(true)
                categoryViewModel.setcategoryViewInt(1)
                categoryViewModel.beerWithFood(1, PER_PAGE, query)
                searchView?.setQuery("", false);
                searchView?.clearFocus()
                  return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }

        })

    }

    override fun onDestroyView() {
        mAdapter = null
        searchView = null
        super.onDestroyView()
    }

}