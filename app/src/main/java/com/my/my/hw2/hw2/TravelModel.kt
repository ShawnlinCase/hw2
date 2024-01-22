package layout

import android.os.Handler
import android.os.Looper
import com.my.my.hw2.hw2.ItemData

import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException


class TravelModel {
    //
    var _items: ArrayList<ItemData> = ArrayList<ItemData>();

    val _handler = Handler(Looper.getMainLooper())


    //
    var _updateTime: Long = 0

    //
    fun sendrequest(sendTime: Long, page: Int, lang: String) {


        // api
        val url: String =
            "https://www.travel.taipei/open-api/${lang}/Attractions/All?page=${page}"
        //"http://e-traffic.taichung.gov.tw/DataAPI/api/AirPortFlyAPI/${sp_flyType_Arr[_flyType]}/${sp_airPortID_Arr[_airPortID]}"
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(url)
            .addHeader("Accept", "application/json")
            .build()
        client.newCall(request).enqueue(object : com.squareup.okhttp.Callback {
            override fun onFailure(request: Request, e: IOException) {

            }

            @Throws(IOException::class)
            override fun onResponse(response: com.squareup.okhttp.Response) {
                val resStr = response.body().string()
                updatLinearLayout(resStr, sendTime, page, lang)
            }
        })


    }

    //
    fun updatLinearLayout(resStr: String, sendTime: Long, page: Int, lang: String) {
        // 不一樣
        try {


            if (_updateTime != sendTime)
                return;
            val json = JSONObject(resStr)
            val jsonArray = json.getJSONArray("data")
            // 都沒有，結束
            if (jsonArray.length() == 0)
                return;
            //
            if (page == 1)// 第一頁，更新
            {
                _items = ArrayList<ItemData>();
                _handler.post {
                    val list: ArrayList<ItemData> = ArrayList<ItemData>()
                    list.addAll(_items);
                    _callback.onCallback(list)
                };
            }

            for (i in 0 until jsonArray.length()) {

                try {
                    var item: ItemData = ItemData(jsonArray.getJSONObject(i));
                    _items.add(item);
                } catch (e: Exception) {
                    e.printStackTrace();
                }

            }

            val list: ArrayList<ItemData> = ArrayList<ItemData>()
            list.addAll(_items);
            _handler.post {
                _callback.onCallback(list)
            };
            // 下一頁
            sendrequest(_updateTime, page + 1, lang)
        } catch (e: Exception) {

        }
    }

    val _updataRunnable: Runnable = Runnable {
        val list: ArrayList<ItemData> = ArrayList<ItemData>()
        list.addAll(_items);
        _callback.onCallback(list)
    }

    // 重新開始
    //
    // callback
    interface Callback {
        fun onCallback(items: ArrayList<ItemData>)

    }

    lateinit var _callback: Callback
    fun Start(callback: Callback, lang: String) {
        _callback = callback
        _updateTime = System.currentTimeMillis()
        sendrequest(_updateTime, 1, lang)

    }
}
