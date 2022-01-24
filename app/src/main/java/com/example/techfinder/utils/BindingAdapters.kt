package com.example.techfinder.utils


import android.location.Location
import android.text.format.DateUtils
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.techfinder.R
import com.example.techfinder.objects.Categoria
import com.example.techfinder.objects.Horario
import com.example.techfinder.objects.TIPOVOTO
import com.example.techfinder.utils.Extensions.Companion.dayOfWeek
import com.example.techfinder.utils.Extensions.Companion.isLojaOpen
import java.sql.Time
import java.sql.Timestamp
import java.util.*
import kotlin.math.floor


@BindingAdapter(value = ["aberturaPreview", "fechoPreview"], requireAll = true)
fun TextView.statusLojaPreview(aberturaPreview: Time, fechoPreview: Time) {
    if (!isLojaOpen(aberturaPreview, fechoPreview)) {
        this.text = "Fechado"
        this.setTextColor(ContextCompat.getColor(context, R.color.fechado))
    } else {
        this.text = "Aberto"
        this.setTextColor(ContextCompat.getColor(context, R.color.loja_aberta))
    }
}

@BindingAdapter("statusLoja")
fun TextView.statusLoja(horarios: MutableList<Horario>) {
    val day = dayOfWeek()
    val horario =
        horarios.stream().filter { horario -> horario.dia == day }.findFirst().orElse(null)
    if (horario?.horarioAbertura != null && horario.horarioFecho != null) {
        if (!isLojaOpen(horario.horarioAbertura!!, horario.horarioFecho!!)) {
            this.text = "Fechado"
            this.setTextColor(ContextCompat.getColor(context, R.color.fechado))

        } else {
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
fun ImageView.setProfilePicture(url: String?) {
    Glide.with(this).load(url).placeholder(R.drawable.ic_pessoa).error(R.drawable.ic_pessoa)
        .into(this)
}

@BindingAdapter("setProfilePictureStrangeUser")
fun ImageView.setProfilePictureStrangeUser(username: String) {
    val pfpUrl = DbAPI.getPfp(username)
    Glide.with(this).load(pfpUrl).placeholder(R.drawable.ic_pessoa).error(R.drawable.ic_pessoa)
        .into(this)
}

@BindingAdapter("favourite")
fun ImageButton.setFavouriteButton(favourite: Boolean) {
    if (favourite) {
        this.background = ContextCompat.getDrawable(context, R.drawable.ic_baseline_bookmark_24)
    } else {
        this.background =
            ContextCompat.getDrawable(context, R.drawable.ic_baseline_bookmark_border_24)
    }
}


@BindingAdapter("categoria")
fun TextView.setCategoryColor(categoria: String) {
    when (categoria) {
        "Computadores" -> this.setTextColor(ContextCompat.getColor(context, R.color.blue_pastel))
        "Televisões" -> this.setTextColor(ContextCompat.getColor(context, R.color.red_pastel))
        "Componentes" -> this.setTextColor(
            ContextCompat.getColor(
                context,
                R.color.light_blue_pastel
            )
        )
        "Eletrodomesticos" -> this.setTextColor(
            ContextCompat.getColor(
                context,
                R.color.purple_pastel
            )
        )
        "Portáteis" -> this.setTextColor(ContextCompat.getColor(context, R.color.rose_pastel))
        "Reparações" -> this.setTextColor(ContextCompat.getColor(context, R.color.green_pastel))
        "Telemóveis" -> this.setTextColor(
            ContextCompat.getColor(
                context,
                R.color.light_purple_pastel
            )
        )
        "Colunas" -> this.setTextColor(ContextCompat.getColor(context, R.color.orange_pastel))
    }

}

@BindingAdapter(value = ["categoria", "tipoBotao"], requireAll = true)
fun ImageButton.drawVoteIcons(categoria: Categoria, tipoBotao: Boolean) {
    when (categoria.tipoVoto) {
        TIPOVOTO.UPVOTE -> {
            if (tipoBotao) {
                this.setBackgroundResource(R.drawable.ic_upvote) //COLOCAR LIKE
            } else {
                this.setBackgroundResource(R.drawable.ic_downvote_outline) //TIRAR DISLIKE
            }
        }
        TIPOVOTO.NOVOTE -> {
            if (tipoBotao) {
                this.setBackgroundResource(R.drawable.ic_upvote_outline) //TIRAR LIKE
            } else {
                this.setBackgroundResource(R.drawable.ic_downvote_outline) //TIRAR DISLIKE
            } //POR LIKE
        }
        TIPOVOTO.DOWNVOTE -> {
            if (tipoBotao) {
                this.setBackgroundResource(R.drawable.ic_upvote_outline) //TIRAR LIKE
            } else {
                this.setBackgroundResource(R.drawable.ic_downvote) //COLOCAR DISLIKE
            }
        }
    }
}

@BindingAdapter(value = ["coordUser", "coordXLoja", "coordYLoja"], requireAll = true)
fun TextView.setDistancia(coordUser: Location, coordXLoja: Float, coordYLoja: Float) {
    val results = FloatArray(1)
    Location.distanceBetween(
        coordUser.latitude,
        coordUser.longitude,
        coordXLoja.toDouble(),
        coordYLoja.toDouble(),
        results
    )
    Log.i("BindingAdapters", results[0].toString())
    if (results[0] < 1000F) this.text = (floor(results[0]).toString().plus(" M"))
    else
        this.text = (floor(results[0] / 1000)).toString().plus(" Km")

}