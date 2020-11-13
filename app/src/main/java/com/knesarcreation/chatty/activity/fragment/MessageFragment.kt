package com.knesarcreation.chatty.activity.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.knesarcreation.chatty.R

class MessageFragment : Fragment() {
    lateinit var searchView: SearchView
    lateinit var arrowBack: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_message, container, false)

        searchView = view.findViewById(R.id.searchView)
        arrowBack = view.findViewById(R.id.imgArrowBack)

        arrowBack.setOnClickListener {
            activity?.finish()
        }

        val searchEditText: EditText =
            searchView.findViewById(androidx.appcompat.R.id.search_src_text)
        searchEditText.setHintTextColor(resources.getColor(R.color.grey))
        searchEditText.setTextColor(resources.getColor(R.color.black))

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })

        return view
    }
}