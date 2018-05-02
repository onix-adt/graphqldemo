package com.onix.demographql.ui.adapter

import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.net.Uri
import android.view.View
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder
import kotlinx.android.synthetic.main.item_list_child.view.*


class ChildViewHolder(itemView: View) : ChildViewHolder(itemView) {

    fun setValues(stars: String, issues: String, url: String) {
        itemView.firstText.text = stars
        itemView.secondText.text = issues
        itemView.thirdText.text = url
        
        itemView.thirdText.setOnClickListener {
            val browserIntent = Intent(ACTION_VIEW, Uri.parse(url))
            browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            itemView.thirdText.context.applicationContext.startActivity(browserIntent)
        }
    }
}