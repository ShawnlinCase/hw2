package com.my.my.hw2.hw2

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter

class ItemDataArrayAdapter( var context : Context) : BaseAdapter() {

    //
    val _items = ArrayList<ItemData>()
    //
    override fun getCount(): Int {
       return _items.size
    }

    override fun getItem(position: Int): Any {
        return _items[position]
    }

    override fun getItemId(position: Int): Long {
        return 0 ;
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        // TODO("Not yet implemented")
        var ret = convertView
        if( convertView == null )
            ret = ItemDataView(context)
        val itemDataView = ret as ItemDataView
        itemDataView.setItem( _items.get( position ) )
        /*
            ret = LayoutInflater
                    .from(context)
                .inflate(R.layout.item_data_layout, null)
*/
        return ret!!

    }

    fun setAll(it: ArrayList<ItemData>) {

        _items.clear()
        _items.addAll(it)
        this.notifyDataSetChanged()
    }
}