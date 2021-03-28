package com.sunrise.suzukanotes.common

import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.sunrise.suzukanotes.R
import per.goweii.anylayer.AnyLayer

/**
 *@author: JiangYu
 *Date: 2021/3/26
 *Time: 21:28
 *Email: jiangyu@haogroup.com
 */
object Dialogs {

    @JvmStatic
    fun showNewDBVersionDialog(
        mContext: Context,
        isForce: Boolean = false,
        callback: DialogCallback? = null
    ) {
        AnyLayer.dialog(mContext).apply {
            contentView(R.layout.normal_dialog)
            gravity(Gravity.CENTER)
            cancelableOnTouchOutside(false)
            bindData {
                getView<TextView>(R.id.dialog_title).text =
                    mContext.getString(R.string.new_db_version_dialog_title)
                if (isForce) {
                    getView<TextView>(R.id.message).text =
                        mContext.getString(R.string.new_db_version_dialog_message_force)
                    getView<Button>(R.id.dialog_negative).visibility = View.GONE
                    getView<Button>(R.id.dialog_positive).apply {
                        text = mContext.getString(R.string.dialog_positive_button_1)
                        setOnClickListener {
                            dismiss()
                            callback?.positiveCallback(it)
                        }
                    }
                } else {
                    getView<TextView>(R.id.message).text =
                        mContext.getString(R.string.new_db_version_dialog_message_check)
                    getView<Button>(R.id.dialog_negative).apply {
                        text = mContext.getString(R.string.dialog_negative_button_1)
                        setOnClickListener {
                            dismiss()
                            callback?.negativeCallback(it)
                        }
                    }
                    getView<Button>(R.id.dialog_positive).apply {
                        text = mContext.getString(R.string.dialog_positive_button_1)
                        setOnClickListener {
                            dismiss()
                            callback?.positiveCallback(it)
                        }
                    }
                }
            }
        }.show()
    }

    interface DialogCallback {
        fun positiveCallback(v: View)
        fun negativeCallback(v: View)
    }
}