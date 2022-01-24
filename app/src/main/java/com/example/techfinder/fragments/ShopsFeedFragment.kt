package com.example.techfinder.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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






class ShopsFeedFragment : Fragment(), ShopsFeedAdapter.OnClickListener {

    lateinit var binding: FragmentShopsFeedBinding

    private val viewModel: ShopsFeedViewModel by lazy {
        ViewModelProvider(this).get(
            ShopsFeedViewModel::class.java
        )
    }

    val args by navArgs<ShopsFeedFragmentArgs>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentShopsFeedBinding.inflate(inflater)
        val adapter = ShopsFeedAdapter(this)
        binding.feed.adapter = adapter

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


