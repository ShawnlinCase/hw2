package com.my.my.hw2.hw2


import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.webkit.WebView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream


class ItemDataView(context: Context) : LinearLayout(context) {

    private var _item: ItemData? = null
    private lateinit var text: TextView;
    private lateinit var image: ImageView;
    private lateinit var webView: WebView;

    init {

        var ret = LayoutInflater
            .from(context)
            .inflate(R.layout.item_data_layout, null)
        this.addView(ret)
        text = ret.findViewById(R.id.text)
        image = ret.findViewById(R.id.image)
        //  webView = ret.findViewById(R.id.web_view)
    }


    fun setItem(item: ItemData) {
        if (_item == item)
            return;

        _item = item;

        text.text = item.getTitle();
        if (item.bitmap != null) {
            image.setImageBitmap(item.bitmap)
            return;
        }
        image.setImageBitmap(null)
        // 沒有圖檔
        if (item.images.size == 0)
            return;

        var saveFile = "";
        // 讀檔
        val html: String = item.images.get(0);
        // 設定檔案
        try {
            val fileSplit = html.split("/")
            val len = fileSplit.size
            saveFile = context.filesDir.absolutePath + File.pathSeparatorChar + fileSplit[len - 2] + fileSplit[len - 1]
            val intent = FileInputStream( saveFile )
            val bitmap = BitmapFactory.decodeStream(intent);

            if( bitmap != null )
            {
                item.bitmap = bitmap ;
                image.setImageBitmap(item.bitmap)
                intent.close();
                return;
            }
            intent.close();
        } catch (e: Exception) {
//            e.printStackTrace()
        }


        //    webView.loadUrl(html)
        //   return
        val job = GlobalScope.launch {
            try {
                val intent = java.net.URL(html).openStream();
                val bitmap = BitmapFactory.decodeStream(intent);
                item.bitmap = bitmap
                val activity: Activity = context as Activity
                // 發送完成
                activity.runOnUiThread {
                    if (_item == item)
                        image.setImageBitmap(bitmap)
                };
                intent.close()
                try {
                    val saveImgOut = FileOutputStream(saveFile)

                    bitmap.compress(Bitmap.CompressFormat.JPEG, 80, saveImgOut)

                    saveImgOut.flush()
                    saveImgOut.close()
                } catch (e: Exception) {
                }
            } catch (e: Exception) {
                Log.e("Error", e.message!!);
                e.printStackTrace();
            }

        }

    }

}