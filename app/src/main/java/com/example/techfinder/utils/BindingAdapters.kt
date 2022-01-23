package com.example.techfinder.utils


import android.text.format.DateUtils
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.techfinder.R
import com.example.techfinder.objects.Horario
import java.sql.Time
import java.sql.Timestamp
import java.time.LocalTime
import java.util.*
import java.util.stream.Collectors


@BindingAdapter(value = ["aberturaPreview","fechoPreview"], requireAll = true)
fun TextView.statusLojaPreview(aberturaPreview:Time, fechoPreview: Time) {
    var atual = LocalTime.parse(Time(System.currentTimeMillis()).toString())
    Log.i("DEGUBMANOS",fechoPreview.toString())
    if(atual.isBefore(LocalTime.parse(aberturaPreview.toString())) ||
       atual.isAfter(LocalTime.parse(fechoPreview.toString()))) {
        this.text = "Fechado"
        this.setTextColor(ContextCompat.getColor(context,R.color.fechado))

    }
    else {
        this.text = "Aberto"
        this.setTextColor(ContextCompat.getColor(context, R.color.loja_aberta))
    }
}

@BindingAdapter("statusLoja")
fun TextView.statusLoja(horarios:MutableList<Horario>) {
    val calendar = Calendar.getInstance()
    val day = calendar[Calendar.DAY_OF_WEEK]
    val horario = horarios.stream().filter { horario -> horario.dia==day}.findFirst().orElse(null);
    if(horario!=null){
        var atual = LocalTime.parse(Time(System.currentTimeMillis()).toString())
        if(atual.isBefore(LocalTime.parse(horario.horarioAbertura.toString())) ||
            atual.isAfter(LocalTime.parse(horario.horarioFecho.toString()))) {
            this.text = "Fechado"
            this.setTextColor(ContextCompat.getColor(context,R.color.fechado))

        }
        else {
            this.text = "Aberto"
            this.setTextColor(ContextCompat.getColor(context, R.color.loja_aberta))
        }
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