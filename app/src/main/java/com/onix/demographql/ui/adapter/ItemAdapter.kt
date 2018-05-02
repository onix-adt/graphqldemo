package com.onix.demographql.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.onix.demographql.R
import com.onix.demographql.data.pojo.ParentInfo
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup


class ItemAdapter(groups: List<ExpandableGroup<*>>) : ExpandableRecyclerViewAdapter<ParentViewHolder, ChildViewHolder>(groups) {

    override fun onCreateGroupViewHolder(parent: ViewGroup, viewType: Int): ParentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return ParentViewHolder(view)
    }

    override fun onCreateChildViewHolder(parent: ViewGroup, viewType: Int): ChildViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_child, parent, false)
        return ChildViewHolder(view)
    }

    override fun onBindChildViewHolder(holder: ChildViewHolder, flatPosition: Int, group: ExpandableGroup<*>, childIndex: Int) {
        val parent = (group as ParentInfo).items[childIndex]
        holder.setValues(parent.starsCount.toString(), parent.openIssuesCount.toString(), parent.url)
    }

    override fun onBindGroupViewHolder(holder: ParentViewHolder, flatPosition: Int, group: ExpandableGroup<*>) {
        holder.setGenreTitle(group)
    }
}