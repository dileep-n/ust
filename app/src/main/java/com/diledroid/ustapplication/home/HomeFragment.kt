package com.diledroid.ustapplication.home

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.diledroid.ustapplication.R
import com.diledroid.ustapplication.data.model.DeviceModel
import com.diledroid.ustapplication.databinding.FragmentHomeBinding
import com.diledroid.ustapplication.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth


class HomeFragment : Fragment(), HomeAdapter.HomeItemListener {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: HomeAdapter
    private val viewModel: HomeViewModel by viewModels {
        HomeViewModelFactory(generateImageList())
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // men and search view created for searching
        setupRecyclerView()
        setupObservers()
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
    // to generate image list
    private fun generateImageList(): ArrayList<DeviceModel> {
        // ArrayList of class ItemsViewModel
        val data = ArrayList<DeviceModel>()

        data.add(DeviceModel("Mac", "168.91.9.0"))
        data.add(DeviceModel("Windows","168.91.9.0"))
        data.add(DeviceModel("Asus", "168.91.9.0"))
        data.add(DeviceModel("Samsung", "168.91.9.0"))
        return data
    }

    override fun onClickedImage(deviceItem: DeviceModel) {
        // this image item details pass to the detail fragment
        val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(deviceItem)
        findNavController().navigate(action)
    }

    // observer from view model class. based on search text we got the filtered value from here
    private fun setupObservers() {
        viewModel.oldFilteredDevice.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {

            } else {

            }
            adapter.setItems(it)
        }
    }

    private fun setupRecyclerView() {
        adapter = HomeAdapter(this)
        binding.homeRecyclerview.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL ,false)
        binding.homeRecyclerview.adapter = adapter
    }

    private fun signOut() {
        FirebaseAuth.getInstance().signOut()
        activity?.finish()
        val intent = Intent(activity, LoginActivity::class.java)
        startActivity(intent)
    }


}