package com.example.dictionary

import android.os.Bundle
import android.widget.EditText
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.InputStream
import java.util.HashMap
import com.example.dictionary.R

import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var searchEditText: EditText
    private lateinit var searchButton: Button
    private lateinit var resultTextView: TextView
    private lateinit var clearButton: Button

    private val dictionary = HashMap<String, String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        searchEditText = findViewById(R.id.searchEditText)
        searchButton = findViewById(R.id.searchButton)
        resultTextView = findViewById(R.id.resultTextView)
        clearButton = findViewById(R.id.clearButton)

        loadDICTIONARY() // Load dictionary from file

        searchButton.setOnClickListener {
            searchDictionary()
        }

        clearButton.setOnClickListener {
            clearAll()
        }
    }

    private fun loadDICTIONARY() {
        val inputStream: InputStream = resources.openRawResource(R.raw.dictionary)
        val reader = BufferedReader(InputStreamReader(inputStream))
        var line: String?
        try {
            while (reader.readLine().also { line = it } != null) {
                val parts = line!!.split(",")
                dictionary[parts[0]] = parts[1]
            }
            reader.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun searchDictionary() {
        val query = searchEditText.text.toString().trim()
        val translation = dictionary[query]
        if (translation != null) {
            resultTextView.text = translation
        } else {
            resultTextView.text = "The word was not found"
        }
    }

    private fun clearAll() {
        searchEditText.text.clear()
        resultTextView.text = ""
    }
}