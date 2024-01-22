package com.my.my.hw2.hw2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.my.my.hw2.hw2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: ViewModelBlankViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 表標題
        if( getSupportActionBar() != null )
            getSupportActionBar()!!.hide()

        viewModel = ViewModelProvider(this).get(ViewModelBlankViewModel::class.java)
        viewModel.setFragmentManager( supportFragmentManager );

       // setContentView(R.layout.activity_main)
        val _binding = DataBindingUtil.setContentView<ActivityMainBinding>( this , R.layout.activity_main)!!
        _binding.viewModel = viewModel ;


        val manager = supportFragmentManager
        val f1  = manager.findFragmentById( R.id.titleFragment )
        val ff1 = f1 as ViewModelBlankFragment
        ff1.setViewModel( viewModel )

        val f2  = manager.findFragmentById( R.id.itemViewFragment )
        val ff2 = f2 as ItemViewBlankFragment
        ff2.setViewModel( viewModel )

        val f3  = manager.findFragmentById( R.id.webViewFragment )
        val ff3 = f3 as WebViewBlankFragment
        ff3.setViewModel( viewModel )


    }

    override fun onBackPressed() {
        if( viewModel._selWebView.value!!.length > 0 )
        {
            viewModel._selWebView.value = ""
            return ;
        }else if( viewModel._selItemData.value != null )
        {
            viewModel._selItemData.value = null ;
            return ;
        }
        super.onBackPressed()
    }
}