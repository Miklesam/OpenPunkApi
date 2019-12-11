package com.miklesam.openpunkapi.ui.favorite
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.miklesam.openpunkapi.R
import com.miklesam.openpunkapi.adapters.FavoriteAdapter
import com.miklesam.openpunkapi.adapters.OnBeerListener
import com.miklesam.openpunkapi.data.Beer

class FavoriteFragment : Fragment(),OnBeerListener {
    override fun onBeerClick(position: Int) {
        favoriteViewModel.setMyBeer(mBeers.get(position))
        favoriteViewModel.setView(true)
        }

    private lateinit var favoriteViewModel: FavoriteViewModel
    lateinit var mBeers:List<Beer>

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        favoriteViewModel =
            ViewModelProviders.of(this).get(FavoriteViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_favorite, container, false)
        val recycler = root.findViewById<RecyclerView>(R.id.recycler_view)
        val beerName =root.findViewById<TextView>(R.id.beer_name)
        val image = root.findViewById<ImageView>(R.id.beer_image)
        val beerTagLine=root.findViewById<TextView>(R.id.beer_tagline)
        val beerDescription=root.findViewById<TextView>(R.id.beer_description)
        val scrolView = root.findViewById<ScrollView>(R.id.scrollContainer)
        val mbeer_id=root.findViewById<TextView>(R.id.beer_id)
        val favorite=root.findViewById<ImageView>(R.id.add_to_favorite)
        val datebrewed=root.findViewById<TextView>(R.id.date)

        val arrowBack=root.findViewById<ImageView>(R.id.emo)
        arrowBack.setOnClickListener {favoriteViewModel.setView(false)}
        favorite.setImageResource(R.drawable.heaert_fool)
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.setHasFixedSize(true)
        val adapter = FavoriteAdapter(this)
        recycler.adapter = adapter

        favoriteViewModel.getBeers().observe(this, Observer {
            mBeers=it
            adapter.setBeers(it)
        });

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT.or(
            ItemTouchHelper.RIGHT)) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                favoriteViewModel.delete(adapter.getBeerAt(viewHolder.adapterPosition))
                //Toast.makeText(context, "Beer Deleted!", Toast.LENGTH_SHORT).show()
            }
        }
        ).attachToRecyclerView(recycler)

        favoriteViewModel.getView().observe(this, Observer { isLoading ->
            if(isLoading) {
                recycler.visibility=GONE
                scrolView.visibility=VISIBLE
                arrowBack.visibility= VISIBLE
            }else{
                recycler.visibility= VISIBLE
                scrolView.visibility= GONE
                arrowBack.visibility= INVISIBLE
            }
        })

        favoriteViewModel.getMyBeer().observe(this, Observer {
            beerName.text=it.name
            mbeer_id.text=it.id
            val concat=getString(R.string.beerDate)+it.first_brewed
            datebrewed.text=concat
            val ttt=it.image_url
            val requestOptions = RequestOptions()
                .placeholder(R.color.white)
            Glide.with(this)
                .setDefaultRequestOptions(requestOptions)
                .load(ttt)
                .into(image)

            beerTagLine.text=it.tagline
            beerDescription.text=it.description
        })

        return root
    }



}