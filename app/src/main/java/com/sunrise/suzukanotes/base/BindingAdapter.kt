package com.sunrise.suzukanotes.base

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.sunrise.suzukanotes.R
import com.sunrise.suzukanotes.common.Static

/**
 *@author: JiangYu
 *Date: 2021/3/24
 *Time: 21:49
 *Email: jiangyu@haogroup.com
 */
@BindingAdapter("imageLevel")
fun imageLevel(view: ImageView, level: Int) {
    view.setImageLevel(level)
}

@BindingAdapter(value = ["imageUrl", "placeHolder", "errorHolder"], requireAll = false)
fun loadImage(view: ImageView, imageUrl: String?, placeHolder: Int?, errorHolder: Int?) {
    when {
        !imageUrl.isNullOrEmpty() && placeHolder != null && errorHolder != null ->
            Glide.with(view.context)
                .load(imageUrl)
                .placeholder(placeHolder)
                .error(errorHolder)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(view)
        !imageUrl.isNullOrEmpty() && placeHolder != null ->
            Glide.with(view.context)
                .load(imageUrl)
                .placeholder(placeHolder)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(view)
        !imageUrl.isNullOrEmpty() && errorHolder != null ->
            Glide.with(view.context)
                .load(imageUrl)
                .error(errorHolder)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(view)
        !imageUrl.isNullOrEmpty() ->
            Glide.with(view.context)
                .load(imageUrl)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(view)
    }
}

@BindingAdapter("runningStyle")
fun setRunningStyleText(view: TextView, runningStyle: Int) {
    when (runningStyle) {
        Static.RUNNING_STYLE_NIGE -> view.text =
            view.context.getString(R.string.text_running_style_nige)
        Static.RUNNING_STYLE_SENKO -> view.text =
            view.context.getString(R.string.text_running_style_senko)
        Static.RUNNING_STYLE_SASHI -> view.text =
            view.context.getString(R.string.text_running_style_sashi)
        Static.RUNNING_STYLE_OIKOMI -> view.text =
            view.context.getString(R.string.text_running_style_oikomi)
    }
}

@BindingAdapter("distance")
fun setDistanceStyleText(view: TextView, distance: Int) {
    when (distance) {
        Static.DISTANCE_SHORT -> view.text = view.context.getString(R.string.text_distance_short)
        Static.DISTANCE_MILE -> view.text = view.context.getString(R.string.text_distance_mile)
        Static.DISTANCE_MIDDLE -> view.text = view.context.getString(R.string.text_distance_middle)
        Static.DISTANCE_LONG -> view.text = view.context.getString(R.string.text_distance_long)
    }
}

@BindingAdapter("how2show")
fun setVisibility(view: View, block: () -> Boolean) {
    if (block()){
        view.visibility = View.VISIBLE
    }else{
        view.visibility = View.GONE
    }
}