package com.miklesam.openpunkapi.ui.random

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.ScrollView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.miklesam.openpunkapi.R
import com.miklesam.openpunkapi.data.Beer
import com.miklesam.openpunkapi.data.DownloadAndSaveImageTask

class RandomFragment : Fragment() {

    private lateinit var randomViewModel: RandomViewModel
    private lateinit var my_beer: Beer

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        randomViewModel =
            ViewModelProviders.of(this).get(RandomViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_random, container, false)
        val favorite=root.findViewById<ImageView>(R.id.add_to_favorite)
        val beerName=root.findViewById<TextView>(R.id.beer_name)
        val fab =root.findViewById<FloatingActionButton>(R.id.fab)
        val image = root.findViewById<ImageView>(R.id.beer_image)
        val progress = root.findViewById<ProgressBar>(R.id.progressBar)
        val scrolView = root.findViewById<ScrollView>(R.id.scrollContainer)
        val errorText=root.findViewById<TextView>(R.id.textError)
        val beerDescription=root.findViewById<TextView>(R.id.beer_description)
        val beerId=root.findViewById<TextView>(R.id.beer_id)
        val beerTagLine=root.findViewById<TextView>(R.id.beer_tagline)

        favorite.setOnClickListener {
            if(!randomViewModel.wasFavorite()!!){
                this.context?.let {
                        it1 -> DownloadAndSaveImageTask(it1,my_beer.id).execute(my_beer.image_url) }
            }
            val path = Uri.parse(context?.filesDir.toString()+"/Images/")
            //randomViewModel.setFavorite( Beer(my_beer.name,my_beer.description,path.path+my_beer.id+".png",my_beer.tagline,my_beer.id))

      }
        fab.setOnClickListener {
            fab.hide()
            scrolView.visibility=GONE
            errorText.visibility=GONE
            progress.visibility= VISIBLE
            randomViewModel.getRandomBeer()
        }

        randomViewModel.getBeer().observe(this, Observer {
        if(it!=null){
        beerName.text=it.name
        beerDescription.text=it.description
        beerId.text=it.id
        beerTagLine.text=it.tagline

            randomViewModel.checkId(it.id).observe(this, Observer { if(it!=null){
                randomViewModel.setFavorite(true)
            }else{
                  randomViewModel.setFavorite(false)
            } })
        val requestOptions = RequestOptions()
        .placeholder(R.drawable.ic_launcher_background)
        my_beer=it
        scrolView.visibility= VISIBLE
        errorText.visibility=GONE
        progress.visibility=GONE
        fab.show()
        if(it.image_url!=null){
        Glide.with(this)
            .setDefaultRequestOptions(requestOptions)
            .load(it.image_url)
            .into(image)
    }else{
        Log.w("path","null_url")
    }
}
        })

        randomViewModel.getError().observe(this, Observer {
            if(it!=null){
                scrolView.visibility= GONE
                progress.visibility= GONE
                errorText.visibility= VISIBLE
                fab.show()
                errorText.text="Error:\n"+it
            }

        })

        randomViewModel.isFavorite().observe(this, Observer { isLoading ->
            if(isLoading) {
                favorite.setImageResource(R.drawable.heaert_fool)
            }else{
                favorite.setImageResource(R.drawable.heaert_empty)
            }
        })

        return root
    }
}