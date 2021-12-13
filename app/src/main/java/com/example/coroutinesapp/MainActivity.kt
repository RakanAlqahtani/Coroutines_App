package com.example.coroutinesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception
import java.net.URL

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        btnAdvice.setOnClickListener{
            requestAPI()

        }
    }

    private fun requestAPI() {

        CoroutineScope(IO).launch {

            val data = async { fetchData() }.await()
         if (data.isNotEmpty()){
             updateTvAdvice(data)
         }

        }

    }

    private suspend fun updateTvAdvice(result : String) {

        withContext(Main){

            var jsonArray = JSONObject(result)

//            var adviceArray = jsonArray.getJSONArray("slip")
            var advice = jsonArray.getJSONObject("slip").getString("advice")
            print("This is the Advice   $advice")
            tvAdvice.text = advice.toString()

        }
    }

    private fun fetchData(): String {
        var response = ""

        try {
            response = URL("https://api.adviceslip.com/advice").readText()
        }catch (e : Exception){
            Log.d("MAIN", "Isuue $e")
        }


        return response

    }
}