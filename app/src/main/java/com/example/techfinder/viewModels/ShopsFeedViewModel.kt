package com.example.techfinder.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.techfinder.objects.LojaPreview
import java.util.*


class ShopsFeedViewModel : ViewModel() {

    var lojaLista = MutableLiveData<List<LojaPreview>>()

    fun getLojasPreview(){

    }


}