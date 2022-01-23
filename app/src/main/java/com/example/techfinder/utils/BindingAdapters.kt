package com.example.techfinder.utils


import android.text.format.DateUtils
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.techfinder.R
import com.google.android.material.card.MaterialCardView
import java.sql.Time
import java.sql.Timestamp
import java.time.LocalDate
import java.time.LocalTime
import java.util.*


@BindingAdapter(value = ["abertura","fecho"], requireAll = true)
fun TextView.statusLoja(abertura:Time, fecho: Time) {
    var atual = LocalTime.parse(Time(System.currentTimeMillis()).toString())
    Log.i("DEGUBMANOS",fecho.toString())
    if(atual.isBefore(LocalTime.parse(abertura.toString())) ||
       atual.isAfter(LocalTime.parse(fecho.toString()))) {
        this.text = "Fechado"
        this.setTextColor(ContextCompat.getColor(context,R.color.fechado))

    }
    else {
        this.text = "Aberto"
        this.setTextColor(ContextCompat.getColor(context, R.color.loja_aberta))
    }
}

@BindingAdapter("timeAgo")
fun TextView.timeAgo(date: Timestamp) {
    val calendar = Calendar.getInstance()
    calendar.time = date

    this.text = DateUtils.getRelativeTimeSpanString(calendar.timeInMillis)
}

@BindingAdapter("setProfilePicture")
fun ImageView.setProfilePicture(url : String?){
    Glide.with(this).load(url).placeholder(R.drawable.ic_pessoa).error(R.drawable.ic_pessoa).into(this)
}