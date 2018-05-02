package com.onix.demographql.tools

import android.content.Context
import android.widget.ImageView
import com.squareup.picasso.Picasso
import java.io.File

class Images {
    companion object {

        fun showOrError(context: Context, urlImage: String, drawable: Int, imageView: ImageView) {
            Picasso.with(context).load(urlImage).error(drawable).into(imageView)
        }

        fun show(context: Context, urlImage: String, imageView: ImageView) {
            Picasso.with(context).load(urlImage).into(imageView)
        }

        fun show(context: Context, file: File, imageView: ImageView) {
            Picasso.with(context).load(file).into(imageView)
        }

        fun show(context: Context, resourceId: Int, imageView: ImageView) {
            Picasso.with(context).load(resourceId).into(imageView)
        }
    }
}