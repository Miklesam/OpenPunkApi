package com.miklesam.openpunkapi.ui.category

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.miklesam.openpunkapi.R
import com.miklesam.openpunkapi.adapters.CategoryAdapter
import com.miklesam.openpunkapi.adapters.OnCategoryListener

class CategoryFragment : Fragment(),OnCategoryListener {
    override fun onBeerCategoryClick(position: Int) {
        Log.w("ClickCategory", "positionClick= "+position)
    }

    override fun onCategoryClick(category: String) {
        Log.w("ClickCategory", "categoryClick= "+category)
        mAdapter.displayLoading()
        categoryViewModel.beerWithFood("1", "5", category)
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

        mAdapter = CategoryAdapter(this)
        recyclerCategory.setLayoutManager(LinearLayoutManager(context))
        recyclerCategory.setAdapter(mAdapter)
        mAdapter.displayCategories()

        categoryViewModel.getBeerFood().observe(this, Observer {
            if(it!=null){
                mAdapter.setmBeer(it)
            }

        })

        return root
    }
}