package com.onix.demographql.ui.adapter

import android.view.View
import com.onix.demographql.R
import com.onix.demographql.tools.Images
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder
import kotlinx.android.synthetic.main.item_list.view.*

class ParentViewHolder(itemView: View) : GroupViewHolder(itemView) {

    fun setGenreTitle(parent: ExpandableGroup<*>) {
        itemView.name.text = parent.name
        itemView.secondView.text = parent.secondText
        Images.showOrError(itemView.context, parent.avatarUrl, R.drawable.ic_default_avatar, itemView.avatarView)
    }
}