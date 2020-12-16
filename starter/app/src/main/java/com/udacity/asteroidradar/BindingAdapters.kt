package com.udacity.asteroidradar

import android.view.View
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.adapter.AsteroidAdapter

@BindingAdapter("statusIcon")
fun bindAsteroidStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.ic_status_potentially_hazardous)
    } else {
        imageView.setImageResource(R.drawable.ic_status_normal)
    }
}

@BindingAdapter("asteroidStatusImage")
fun bindDetailsStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.asteroid_hazardous)
    } else {
        imageView.setImageResource(R.drawable.asteroid_safe)
    }
}

@BindingAdapter("astronomicalUnitText")
fun bindTextViewToAstronomicalUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.astronomical_unit_format), number)
}

@BindingAdapter("kmUnitText")
fun bindTextViewToKmUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_unit_format), number)
}

@BindingAdapter("velocityText")
fun bindTextViewToDisplayVelocity(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_s_unit_format), number)
}

@BindingAdapter("asteroidList")
fun bindAsteroidList(recyclerView: RecyclerView, asteroidList: List<Asteroid>?) {
    val adapter = recyclerView.adapter as AsteroidAdapter
    adapter.submitList(asteroidList)
}

@BindingAdapter("imageUrl")
fun bindImageOfTheDay(imageView: ImageView, url: String?) {
    url?.let {
        Picasso.with(imageView.context)
            .load(it)
            .error(R.drawable.ic_connection_error)
            .placeholder(R.drawable.loading_animation)
            .into(imageView)
    }
}

@BindingAdapter("status")
fun bindProgressBarStatus(progressBar: ProgressBar, status: Status?) {
    when (status) {
        Status.DONE -> {
            progressBar.visibility = View.GONE
        }
        Status.ERROR -> {
            progressBar.visibility = View.GONE
        }
        Status.LOADING -> {
            progressBar.visibility = View.VISIBLE
        }
    }
}

@BindingAdapter("setAsFavorite")
fun bindIsFavorite(button: Button, isFavorite: Boolean) {
    val context = button.context
    if (isFavorite) {
        button.text = context.getString(R.string.remove_favorite)
    } else {
        button.text = context.getString(R.string.set_favorite)
    }
}


@BindingAdapter("fetchingStatus")
fun bindAsteroidList(recyclerView: RecyclerView, status: Status?) {
    when (status) {
        Status.DONE -> {
            recyclerView.visibility = View.VISIBLE
        }
        Status.LOADING -> {
            recyclerView.visibility = View.INVISIBLE
        }
        Status.ERROR -> {
            recyclerView.visibility = View.VISIBLE
        }
    }
}

@BindingAdapter("asteroidDescription")
fun bindAsteroidDescription(ConstraintLayout: ConstraintLayout, asteroid: Asteroid?) {
    val context = ConstraintLayout.context
    asteroid?.let {
        val isHazardous = if (asteroid.isPotentiallyHazardous) {
            context.getString(R.string.hazardous)
        } else {
            context.getString(R.string.non_hazardous)
        }
        ConstraintLayout.contentDescription = String.format(
            context.getString(R.string.asteroid_description),
            it.codename,
            it.closeApproachDate,
            isHazardous
        )
    }
}

@BindingAdapter("asteroidDetailDescription")
fun bindAsteroidDetailDescription(linearLayout: LinearLayout, asteroid: Asteroid?) {
    val context = linearLayout.context
    asteroid?.let {
        val isHazardous = if (asteroid.isPotentiallyHazardous) {
            context.getString(R.string.hazardous)
        } else {
            context.getString(R.string.non_hazardous)
        }
        linearLayout.contentDescription = String.format(
            context.getString(R.string.asteroid_detailed_description),
            it.codename,
            it.closeApproachDate,
            isHazardous,
            it.absoluteMagnitude,
            it.estimatedDiameter,
            it.relativeVelocity,
            it.distanceFromEarth
        )
    }
}

@BindingAdapter("asteroidHazardDescription")
fun bindHazardImageDescription(imageView: ImageView, isHazardous: Boolean) {
    val context = imageView.context
    if (isHazardous) {
        imageView.contentDescription = context.getString(R.string.hazard_image)
    } else {
        imageView.contentDescription = context.getString(R.string.non_hazard_image)
    }
}