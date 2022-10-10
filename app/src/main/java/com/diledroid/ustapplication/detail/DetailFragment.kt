package com.diledroid.ustapplication.detail

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.diledroid.ustapplication.R
import com.diledroid.ustapplication.databinding.FragmentDetailBinding
import com.diledroid.ustapplication.databinding.FragmentHomeBinding
import com.diledroid.ustapplication.home.HomeViewModel
import com.diledroid.ustapplication.login.LoginActivity
import com.diledroid.ustapplication.util.NetworkUtility
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private val viewModel: DetailViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.pBar.visibility = View.VISIBLE
       val result = viewModel.getData()
        activity?.let { it ->
            viewModel.ip.observe(it, Observer {
                var fullResult = viewModel.getDetails(it)
        }) }

        activity?.let { it ->
            viewModel.ipFullDetails.observe(it, Observer {
                binding.pBar.visibility = View.GONE
               binding.ipDetailText.text = it
            }) }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater!!.inflate(R.menu.menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //handle item clicks of menu
        //get item id to handle item clicks
        val id = item!!.itemId
        //handle item clicks
        if (id == R.id.action_logout) {
            //do your action here,
            signOut()

        }
        return super.onOptionsItemSelected(item)

    }



    private fun signOut() {
        FirebaseAuth.getInstance().signOut()
        activity?.finish()
        val intent = Intent(activity, LoginActivity::class.java)
        startActivity(intent)
    }
}