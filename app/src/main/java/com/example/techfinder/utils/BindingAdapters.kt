package com.example.techfinder.utils


import android.text.format.DateUtils
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.example.techfinder.R
import com.google.android.material.card.MaterialCardView
import java.sql.Time
import java.sql.Timestamp
import java.time.LocalDate
import java.util.*


@BindingAdapter(value = ["abertura","fecho"], requireAll = true)
fun TextView.statusLoja(abertura:Time, fecho: Time) {
    var atual = Time.valueOf(LocalDate.now().toString())
    if(atual.before(abertura) || atual.after(fecho)) {
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