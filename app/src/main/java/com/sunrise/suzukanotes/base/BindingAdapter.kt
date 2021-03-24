package com.sunrise.suzukanotes.common

import android.widget.ImageView
import androidx.databinding.BindingAdapter

/**
 *@author: JiangYu
 *Date: 2021/3/24
 *Time: 21:49
 *Email: jiangyu@haogroup.com
 */
@BindingAdapter("imageLevel")
fun imageLevel(view:ImageView,level:Int){
    view.setImageLevel(level)
}