package com.my.my.hw2.hw2

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ItemViewBlankViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    public val id = ObservableField<String>("0");
    public val name = ObservableField<String>("0");
    public val address = ObservableField<String>("0");
    public val introduction = ObservableField<String>("0");

    val images = MutableLiveData(  ArrayList<String>())
}