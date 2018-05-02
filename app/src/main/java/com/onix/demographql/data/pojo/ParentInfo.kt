package com.onix.demographql.data.pojo

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup


class ParentInfo(name: String, secondText: String, avatarUrl: String, items: List<ChildInfo>)
    : ExpandableGroup<ChildInfo>(name, secondText, avatarUrl, items)