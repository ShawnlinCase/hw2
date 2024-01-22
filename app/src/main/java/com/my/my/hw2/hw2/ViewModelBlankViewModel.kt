package com.my.my.hw2.hw2

import androidx.databinding.ObservableField
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import layout.TravelModel

class ViewModelBlankViewModel : ViewModel() , TravelModel.Callback{
    // 標題
    public val titleString = ObservableField<String>("0");

    // TODO: Implement the ViewModel
    private lateinit var _fragmentManager : FragmentManager
    fun setFragmentManager( fragmentManager : FragmentManager )
    {
        this._fragmentManager = fragmentManager ;
    }
    fun getFragmentManager(): FragmentManager
    {
        return this._fragmentManager
    }
    //
    val _travelModel : TravelModel = TravelModel();
    //

    val _items = MutableLiveData(  ArrayList<ItemData>())

    // 網頁的
    val _selWebView = MutableLiveData<String>( "" )
    // 設定選中的
    val _selItemData = MutableLiveData<ItemData?>( null )
    fun setItemData( item : ItemData? )
    {
        _selItemData.value = item
    }

    // test 用
    public val intData = ObservableField<String>("0");
    fun setIntData(){
        var value = intData.get();
        intData.set( intData.get()!!+1);
        intData.notifyChange()
    }

    // 位置
    public val lang = ObservableField<String>("0");

    // 取得
    fun getTraveData( lang: String )
    {
        this.lang.set( lang )
        _travelModel.Start( this , lang );
    }
    //
    override fun onCallback(items: ArrayList<ItemData>) {
        // TODO("Not yet implemented")
         intData.set( "" + items.size  );
        _items.value = items ;
    }

}