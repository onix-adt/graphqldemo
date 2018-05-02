package com.onix.demographql.ui.view

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.res.AssetManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Toast
import com.onix.demographql.R
import com.onix.demographql.ui.adapter.ItemAdapter
import com.onix.demographql.data.pojo.FeedEntry
import com.onix.demographql.data.pojo.RepositoryFragment
import com.onix.demographql.data.pojo.ChildInfo
import com.onix.demographql.data.pojo.ParentInfo
import com.onix.demographql.ui.view_model.MainActivityVM
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    lateinit var progress: ProgressBar
    private lateinit var mainAViewModel: MainActivityVM
    private var adapter: ItemAdapter? = null
    private lateinit var assetManager: AssetManager

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        assetManager = this.assets

        mainAViewModel = ViewModelProviders.of(this).get(MainActivityVM::class.java)
        mainAViewModel.liveData.observe(this, Observer { onResponse(it) })
        mainAViewModel.errorData.observe(this, Observer { onResponseError(it) })
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.listItems)
        progress = findViewById(R.id.progress)
        mainAViewModel.createAndCallApi(assetManager)
    }


    private fun onResponse(response: ArrayList<FeedEntry>?) {
        progress.visibility = View.GONE
        if (response?.get(0)?.repository == null) {
            Toast.makeText(recyclerView.context, "Response error ", Toast.LENGTH_LONG).show()
        } else {
            recyclerView.layoutManager = LinearLayoutManager(recyclerView.context, LinearLayout.VERTICAL, false)
            adapter = ItemAdapter(generateItems(response))
            recyclerView.adapter = adapter
            val animator = recyclerView.itemAnimator
            if (animator is DefaultItemAnimator) {
                animator.supportsChangeAnimations = false
            }
        }
    }

    private fun onResponseError(exception: Throwable?) {
        progress.visibility = View.GONE
        Toast.makeText(recyclerView.context, exception?.message, Toast.LENGTH_LONG).show()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        adapter?.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        adapter?.onRestoreInstanceState(savedInstanceState)
    }

    private fun generateItems(listItems: ArrayList<FeedEntry>): List<ParentInfo> {
        val result = ArrayList<ParentInfo>()
        for (i in 0 until listItems.size) {
            val parentInfo = ParentInfo(listItems[i].repository?.fragments?.repositoryFragment?.owner?.login!!,
                    listItems[i].repository?.fragments?.repositoryFragment?.fullName!!,
                    listItems[i].repository?.fragments?.repositoryFragment?.owner?.avatarUrl!!,
                    generateChildItems(listItems[i].repository?.fragments?.repositoryFragment!!))
            result.add(parentInfo)
        }
        return result
    }

    private fun generateChildItems(repositoryData: RepositoryFragment): List<ChildInfo> {
        val childInfo = ChildInfo(repositoryData.stargazersCount, repositoryData.openIssuesCount, repositoryData.htmlUrl)
        return Arrays.asList(childInfo)
    }
}
