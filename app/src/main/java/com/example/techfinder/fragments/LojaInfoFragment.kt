package com.example.techfinder.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.techfinder.NavGraphDirections
import com.example.techfinder.R
import com.example.techfinder.activities.MainActivity
import com.example.techfinder.adapters.CategoriasAdapter
import com.example.techfinder.adapters.ComentariosAdapter
import com.example.techfinder.databinding.FragmentLojaInfoBinding
import com.example.techfinder.objects.Comentario
import com.example.techfinder.utils.Extensions.Companion.getUser
import com.example.techfinder.utils.Extensions.Companion.hideKeyboard
import com.example.techfinder.viewModels.LojaInfoViewModel
import java.sql.Date
import java.sql.Timestamp
import java.text.DateFormat
import java.text.SimpleDateFormat
import android.net.Uri

import android.content.Intent





class LojaInfoFragment : Fragment() {

    lateinit var binding: FragmentLojaInfoBinding

    private val viewModel: LojaInfoViewModel by lazy {
        ViewModelProvider(this).get(LojaInfoViewModel::class.java)
    }

    val args by navArgs<LojaInfoFragmentArgs>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLojaInfoBinding.inflate(inflater)
        val loja = viewModel.getLoja(args.idLoja)
        binding.loja = loja
        binding.abertura.text = args.abertura
        binding.fecho.text = args.fecho

        //ADAPTER CATEGORIAS

        val adapterCategorias = CategoriasAdapter()
        binding.scrollableCategorias.adapter = adapterCategorias
        viewModel.listaCategoria.value = loja?.categorias?.filter { it.voto!! >=1 }
        adapterCategorias.submitList(viewModel.listaCategoria.value)


        //ADAPTER COMENTARIOS
        val adapterComentarios = ComentariosAdapter()
        binding.comentariosFeed.adapter = adapterComentarios
        viewModel.listaComentarios.value = loja?.comentarios
        adapterComentarios.submitList(viewModel.listaComentarios.value)
        viewModel.listaComentarios.observe(viewLifecycleOwner, { it ->
            if (it.size == 0) binding.comentariosCard.visibility = View.GONE
            else {
                binding.comentariosCard.visibility = View.VISIBLE
            }
            adapterComentarios.submitList(it)
            adapterComentarios.notifyDataSetChanged()
        })

        binding.direcoesCard.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?saddr="+args.myX+","+args.myY+"&daddr="+args.lojaX+","+args.lojaY+"")
            )
            startActivity(intent)
        }

        binding.votar.setOnClickListener {
            loja?.id?.let { lojaId ->
                val action = NavGraphDirections.actionGlobalVoteFragment().setIdLoja(
                    lojaId
                )
                (activity as MainActivity).findNavController(R.id.host_fragment)
                    .navigate(action)
            }

        }



        binding.favorito.setOnClickListener {
            if (loja != null) {
                loja.fav = !loja.fav
                viewModel.fav(loja.id, loja.fav)
                if (loja.fav) {
                    binding.favorito.background = ContextCompat.getDrawable(
                        (activity as MainActivity),
                        R.drawable.ic_baseline_bookmark_24
                    )
                } else {
                    binding.favorito.background = ContextCompat.getDrawable(
                        (activity as MainActivity),
                        R.drawable.ic_baseline_bookmark_border_24
                    )
                }
            }
        }

        binding.logoComment.setOnClickListener {
            if (!binding.escreverComentario.text.isNullOrBlank()) {
                var comentario: Comentario? = null
                loja?.nome?.let { nome ->
                    comentario = Comentario(
                        loja.id,
                        nome,
                        getUser()?.username,
                        binding.escreverComentario.text.toString(),
                        Timestamp(System.currentTimeMillis())
                    )
                    viewModel.criarComentario(comentario!!)
                    hideKeyboard(activity as MainActivity)
                }

            }
        }

        return binding.root
    }
}