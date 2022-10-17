package com.example.win6

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageButton
import androidx.core.content.edit
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.win6.model.NBAModel
import com.example.win6.model.Response
import com.google.gson.Gson
import com.onesignal.OneSignal
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    val ONESIGNAL_APP_ID = "714b9f14-381d-4fc4-a93c-28d480557381"

    val countriesArray = ArrayList<String>()
    val countriesSet = LinkedHashSet<String>()
    var inhibit_spinner = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)
        OneSignal.initWithContext(this)
        OneSignal.setAppId(ONESIGNAL_APP_ID)
        val currentDate: String = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        Log.i("date", currentDate)
        val sharedCountry = sharedCountry()?.getString("country", "")
        Log.i("sharedCountry", sharedCountry.toString())
        if (sharedCountry!!.isNotEmpty()) {
            response(currentDate, sharedCountry.toString())
        } else {
            response(currentDate, "China")
        }
        refreshList(currentDate)
        spinnerSettings(currentDate)
    }


    fun response(currentDate: String, currentCountry: String) {
        countriesSet.clear()
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://v1.basketball.api-sports.io/games?date=${currentDate}")
            .get()
            .addHeader("X-RapidAPI-Key", "a3b8c8b2e1acf3bb9c0b0699d44371ea")
            .addHeader("X-RapidAPI-Host", "api-nba-v1.p.rapidapi.com")
            .build()

        CoroutineScope(Dispatchers.IO).launch {
            val response = client.newCall(request).execute()
            val gson = Gson()
            val responseBody = response.body!!.string()
            val jsonArray = JSONObject(responseBody)
            Log.i("JSON", jsonArray.toString())
            val entity = gson.fromJson(responseBody, NBAModel::class.java)
            val list = ArrayList<Response>()
            list.clear()
            for (i in entity.response) {
                if (currentCountry == i.country.name) {
                    list.add(i)
                }
                countriesSet.add(i.country.name)
            }
            countriesArray.addAll(countriesSet)

            launch(Dispatchers.Main) {
                val currentDate: String =
                    SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
                spinnerSettings(currentDate)
                Log.i("countriesArrayMAin", countriesArray.toString())
                val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
                val recyclerViewState = recyclerView.layoutManager?.onSaveInstanceState()
                val adapter = MainAdapter(list)
                adapter.notifyDataSetChanged()
                recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
                recyclerView.adapter = adapter
                recyclerView.layoutManager?.onRestoreInstanceState(recyclerViewState)
            }

        }
    }

    private fun spinnerSettings(currentDate: String) {
        Log.i("countriesArray", countriesArray.toString())
        val sharedCountry = sharedCountry()?.getString("country", "")
        val spinner = findViewById<Spinner>(R.id.spinner)
        val arrayAdapter = ArrayAdapter(
            this@MainActivity,
            R.layout.spinner_item,
            countriesArray
        )
        spinner.isSelected = false
        spinner.setSelection(Adapter.NO_SELECTION, false)
        spinner.adapter = arrayAdapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (inhibit_spinner) {
                    for (i in countriesArray.indices) {
                        if (sharedCountry == countriesArray[i]) {
                            spinner.setSelection(i)
                        }
                    }
                    inhibit_spinner = false
                } else {
                    val sharedPrefCountry =
                        getSharedPreferences("countryPref", Context.MODE_PRIVATE)
                    sharedPrefCountry.edit {
                        putString("country", countriesArray[p2])
                    }

                    countriesSet.clear()
                    val client = OkHttpClient()
                    val request = Request.Builder()
                        .url("https://v1.basketball.api-sports.io/games?date=${currentDate}")
                        .get()
                        .addHeader("X-RapidAPI-Key", "a3b8c8b2e1acf3bb9c0b0699d44371ea")
                        .addHeader("X-RapidAPI-Host", "api-nba-v1.p.rapidapi.com")
                        .build()

                    CoroutineScope(Dispatchers.IO).launch {
                        val response = client.newCall(request).execute()
                        val gson = Gson()
                        val responseBody = response.body!!.string()
                        val jsonArray = JSONObject(responseBody)
                        Log.i("JSON", jsonArray.toString())
                        val entity = gson.fromJson(responseBody, NBAModel::class.java)
                        val list = ArrayList<Response>()
                        list.clear()
                        for (i in entity.response) {
                            if (countriesArray[p2] == i.country.name) {
                                list.add(i)
                            }
                            countriesSet.add(i.country.name)
                        }
                        launch(Dispatchers.Main) {
                            countriesArray.addAll(countriesSet)
                            Log.i("countriesArrayMAin", countriesArray.toString())
                            val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
                            val recyclerViewState = recyclerView.layoutManager?.onSaveInstanceState()
                            val adapter = MainAdapter(list)
                            adapter.notifyDataSetChanged()
                            recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
                            recyclerView.adapter = adapter
                            recyclerView.layoutManager?.onRestoreInstanceState(recyclerViewState)
                        }

                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }
    }

    private fun sharedCountry(): SharedPreferences? {
        val sharedPrefCountry = getSharedPreferences("countryPref", Context.MODE_PRIVATE)
        return sharedPrefCountry
    }

    fun refreshList(currentDate: String) {
        val country = sharedCountry()?.getString("country", "")
        findViewById<AppCompatImageButton>(R.id.refresh_button).setOnClickListener {
            findViewById<RecyclerView>(R.id.recycler_view).layoutManager?.onSaveInstanceState()
            if (country != null) {
                inhibit_spinner = true
                response(currentDate, country)
            }
        }
    }


}