package com.andriod.querymoviecontentprovider

import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.andriod.querymoviecontentprovider.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonExecute.setOnClickListener {
            val uri =
                Uri.parse("content://com.andriod.movies.provider/videos/${binding.editTextId.text}")
            val cursor = contentResolver.query(uri,
                null, null, null, null)

            cursor?.let { cursor ->
                val sb = StringBuilder()
                for (i in 0..cursor.count) {
                    if (cursor.moveToPosition(i)) {
                        sb.append("${cursor.toText()}\n")
                    }
                }

                binding.textViewResults.text = sb.toString()
            }

            cursor?.close()
        }
    }


    private fun Cursor.toText(): String {
        val key = getString(getColumnIndex(KEY))
        val link = if (getString(getColumnIndex(SITE)) == "YouTube") {
            "https://www.youtube.com/watch?v=$key"
        } else {
            key
        }
        return "${getString(getColumnIndex(NAME))}: $link"
    }

    companion object {
        private const val KEY = "videoId"
        private const val NAME = "name"
        private const val SITE = "site"
    }
}
