package com.example.techfinder.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.techfinder.R
import com.example.techfinder.activities.MainActivity
import com.example.techfinder.databinding.FragmentPerfilBinding
import com.example.techfinder.utils.DbAPI
import com.example.techfinder.utils.Extensions.Companion.getUser
import com.example.techfinder.utils.Extensions.Companion.setUser
import com.example.techfinder.viewModels.PerfilViewModel
import pl.aprilapps.easyphotopicker.DefaultCallback
import pl.aprilapps.easyphotopicker.EasyImage
import pl.aprilapps.easyphotopicker.MediaFile
import pl.aprilapps.easyphotopicker.MediaSource


class PerfilFragment : Fragment() {

    lateinit var binding: FragmentPerfilBinding

    private val viewModel: PerfilViewModel by lazy {
        ViewModelProvider(this).get(PerfilViewModel::class.java)
    }

    var imgUri: String? = null
        set(value) {
            field = value
            Glide.with(this).load(field).placeholder(R.drawable.ic_pessoa)
                .error(R.drawable.ic_pessoa).into(binding.fotoPerfil)

        }


    lateinit var easyImage: EasyImage

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPerfilBinding.inflate(inflater)
        binding.user = getUser()

        imgUri = getUser()?.pfpUrl;

        easyImage = EasyImage.Builder(requireContext())
            .allowMultiple(false)
            .build()

        binding.fotoPerfil.setOnClickListener {
            Log.i("PerfilFragment", "User pfp clicked")
            easyImage.openChooser(this);
        }


        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        easyImage.handleActivityResult(
            requestCode,
            resultCode,
            data,
            (activity as MainActivity),
            object : DefaultCallback() {
                override fun onMediaFilesPicked(imageFiles: Array<MediaFile>, source: MediaSource) {
                    val newUser = getUser()
                    if (newUser != null) {
                        newUser.pfpUrl = viewModel.alterarPfp(imageFiles[0].file.absolutePath)
                        setUser(newUser)
                    }
                    imgUri = imageFiles[0].file.absolutePath

                }

                override fun onImagePickerError(error: Throwable, source: MediaSource) {
                    //Some error handling
                    error.printStackTrace()
                }

                override fun onCanceled(source: MediaSource) {
                    //Not necessary to remove any files manually anymore
                }
            })
    }
}
