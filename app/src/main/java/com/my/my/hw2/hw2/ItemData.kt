package com.my.my.hw2.hw2

import android.graphics.Bitmap
import org.json.JSONArray
import org.json.JSONObject

class ItemData(itemData: JSONObject) {

    var bitmap: Bitmap? = null

    val id: String = itemData.get("id").toString()
    val name : String = itemData.get("name").toString()
    val json_images: JSONArray = itemData.getJSONArray("images")
    val images: ArrayList<String> = ArrayList<String>()
    val address : String = itemData.get("address").toString()
    val introduction : String = itemData.get("introduction").toString()
    val url : String = itemData.get("url").toString()


    init {
        for (i in 0 until json_images.length()) {
            try {
                val json: JSONObject = json_images.getJSONObject(i)
                images.add(json.getString("src"))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getTitle() : String
    {
        return "id: ${id}\n${name}\n${address}\nimage count: ${images.size}"
    }

}