package com.example.meme

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var currentImageurl:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
       loadMeme()
    }

   private fun loadMeme(){
        progressbar.visibility=View.VISIBLE
       currentImageurl = "https://meme-api.herokuapp.com/gimme"
       //https://api.unsplash.com/

// Request a string response from the provided URL.
        val jsonObjectRequest =JsonObjectRequest(
            Request.Method.GET, currentImageurl,null,
            { response ->
               val url=response.getString("url")
                Glide.with(this).load(url).listener(object:RequestListener<Drawable>{
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressbar.visibility=View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressbar.visibility=View.GONE
                        return false
                    }
                }).into(memeImageView)
            },
            {
                Toast.makeText(this, "Soemthing went wrong", Toast.LENGTH_SHORT).show()
            })

// Add the request to the RequestQueue.
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }
    fun nextMeme(view: View) {
        loadMeme()
    }
    fun shareMeme(view: View) {
    val intent=Intent(Intent.ACTION_SEND)
        intent.type="text/plain"
    intent.putExtra(Intent.EXTRA_TEXT,"Check Out The Cool Meme $currentImageurl")
        val chooser=Intent.createChooser(intent,"Share this Meme Using.......")
        startActivity(chooser)

    }
}