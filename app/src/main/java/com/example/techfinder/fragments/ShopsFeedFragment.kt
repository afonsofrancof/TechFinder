package com.example.techfinder.fragments

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.*
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.techfinder.NavGraphDirections
import com.example.techfinder.R
import com.example.techfinder.activities.MainActivity
import com.example.techfinder.adapters.ShopsFeedAdapter
import com.example.techfinder.databinding.FragmentShopsFeedBinding
import com.example.techfinder.objects.LojaPreview
import com.example.techfinder.utils.Extensions.Companion.isLojaOpen
import com.example.techfinder.viewModels.ShopsFeedViewModel
import java.sql.Date
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import android.util.Log
import android.widget.Toast
import java.io.IOException
import androidx.core.content.ContextCompat.getSystemService

import android.location.LocationManager
import com.google.android.gms.common.api.GoogleApiClient
import android.location.Location
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.getSystemService
import com.google.android.gms.location.FusedLocationProviderClient

import com.google.android.gms.location.LocationServices








class ShopsFeedFragment : Fragment(), ShopsFeedAdapter.OnClickListener {

    lateinit var binding: FragmentShopsFeedBinding


    private val viewModel: ShopsFeedViewModel by lazy {
        ViewModelProvider(this).get(
            ShopsFeedViewModel::class.java
        )
    }

    private lateinit var fusedLocationClient: FusedLocationProviderClient


    val args by navArgs<ShopsFeedFragmentArgs>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentShopsFeedBinding.inflate(inflater)



        fusedLocationClient = LocationServices.getFusedLocationProviderClient((activity as MainActivity))
        val loc = Location("")
        if (ActivityCompat.checkSelfPermission(
                (activity as MainActivity),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                (activity as MainActivity),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            val locationPermissionRequest = registerForActivityResult(
                ActivityResultContracts.RequestMultiplePermissions()
            ) { permissions ->
                when {
                    permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                        // Precise location access granted.
                    }
                    permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                        // Only approximate location access granted.
                    } else -> {
                    Toast.makeText((activity as MainActivity),"LOCATION NOT PROVIDED, APP WONT WORK AS EXPECTED",Toast.LENGTH_SHORT).show()
                }
                }
            }
        }

        val adapter = ShopsFeedAdapter(this,loc)
        binding.feed.adapter = adapter

        fusedLocationClient.lastLocation
            .addOnSuccessListener { location->
                if (location != null) {
                    loc.longitude = location.longitude
                    loc.latitude = location.latitude
                    adapter.notifyDataSetChanged()
                }
            }


        viewModel.lojaLista.observe(viewLifecycleOwner, { it ->
            viewModel.sortBoolean()
            if (args.isFavourites) {
                val listaFiltered = it.filter { lojaPreview -> lojaPreview.fav }
                adapter.submitList(listaFiltered)
            } else adapter.submitList(it)
        })

        viewModel.lojaLista.value = null
        viewModel.getLojasPreview()

        val refreshListener = SwipeRefreshLayout.OnRefreshListener {
            binding.swipeRefreshLayout.isRefreshing = true
            viewModel.getLojasPreview()
            binding.swipeRefreshLayout.isRefreshing = false
        }

        binding.swipeRefreshLayout.setOnRefreshListener(refreshListener)

        return binding.root
    }

    override fun onClick(loja: LojaPreview) {

        val action = NavGraphDirections.toLojaInfoFragment()
        val myStart =
            loja.horario?.horarioAbertura?.toInstant()?.let { Date(it.toEpochMilli()) }
        val myEnd = loja.horario?.horarioFecho?.toInstant()?.let { Date(it.toEpochMilli()) }
        val df: DateFormat = SimpleDateFormat("HH:mm")
        val myDateStart: String = df.format(myStart)
        val myDateEnd: String = df.format(myEnd)
        action.idLoja = loja.id
        action.abertura = myDateStart
        action.fecho = myDateEnd
        (activity as MainActivity).findNavController(R.id.host_fragment)
            .navigate(action)
    }


}



