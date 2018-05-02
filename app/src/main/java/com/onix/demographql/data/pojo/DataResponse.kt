package com.onix.demographql.data.pojo

import com.google.gson.annotations.SerializedName


data class ResponseData(
        @SerializedName("data") var data: Data
)

data class Data(
        @SerializedName("feedEntries") var feedEntries: ArrayList<FeedEntry>
)

data class FeedEntry(
        @SerializedName("repository") var repository: Repository?
)

data class Repository(
        @SerializedName("fragments") var fragments: Fragments
)

data class Fragments(
        @SerializedName("repositoryFragment") var repositoryFragment: RepositoryFragment
)

data class RepositoryFragment(
        @SerializedName("description") var description: String,
        @SerializedName("full_name") var fullName: String,
        @SerializedName("html_url") var htmlUrl: String,
        @SerializedName("open_issues_count") var openIssuesCount: Int,
        @SerializedName("owner") var owner: Owner,
        @SerializedName("stargazers_count") var stargazersCount: Int
)

data class Owner(
        @SerializedName("avatar_url") var avatarUrl: String,
        @SerializedName("html_url") var htmlUrl: String,
        @SerializedName("login") var login: String
)