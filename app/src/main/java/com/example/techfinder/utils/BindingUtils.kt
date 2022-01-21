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
fun MaterialCardView.statusLoja(abertura:Time, fecho: Time) {
    var atual = Time.valueOf(LocalDate.now().toString())
    if(atual.before(abertura) || atual.after(fecho))
        this.setBackgroundColor(ContextCompat.getColor(context,R.color.loja_aberta))
    else
        this.setBackgroundColor(ContextCompat.getColor(context,R.color.light_gray))
}

@BindingAdapter("timeAgo")
fun TextView.timeAgo(date: Timestamp) {
    val calendar = Calendar.getInstance()
    calendar.time = date

    this.text = DateUtils.getRelativeTimeSpanString(calendar.timeInMillis)
}