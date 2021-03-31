package com.sunrise.suzukanotes.base

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

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