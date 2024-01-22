package com.my.my.hw2.hw2

import android.app.Activity
import android.graphics.BitmapFactory
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.my.my.hw2.hw2.databinding.FragmentItemViewBlankBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ItemViewBlankFragment : Fragment() {

    companion object {
        fun newInstance() = ItemViewBlankFragment()
    }

    private var titleString: String = ""

    //
    lateinit var _root: View;
    lateinit var _binding: FragmentItemViewBlankBinding
    lateinit var _thisViewModel: ItemViewBlankViewModel

    private lateinit var _rootViewModel: ViewModelBlankViewModel

    private var _selItem: ItemData? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_item_view_blank, container, false
        )
        _root = _binding.root

        _binding.webView.setOnClickListener {
            if (_selItem != null)
                _rootViewModel._selWebView.value = _selItem!!.url
        }
        return _root // inflater.inflate(R.layout.fragment_item_view_blank, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        _thisViewModel = ViewModelProvider(this).get(ItemViewBlankViewModel::class.java)
        //
        // TODO: Use the ViewModel

        //
        _binding.thisViewModel = _thisViewModel
        //
        _thisViewModel.images.observe(viewLifecycleOwner, Observer {

        })
        setViewModel(this._rootViewModel);

        //
    }

    //
    //
    fun setViewModel(viewModel: ViewModelBlankViewModel) {
        if (viewModel == null)
            return;
        this._rootViewModel = viewModel
        _binding.viewModel = viewModel


        // 設定觀查
        viewModel._selItemData.observe(viewLifecycleOwner, Observer {
            // updating data in displayMsg
            _selItem = it
            if (it != null) {
                viewModel.getFragmentManager().beginTransaction()
                    .show(this)
                    .commit()
                _thisViewModel.id.set(it.id);
                _thisViewModel.name.set(it.name);
                _thisViewModel.address.set(it.address);
                _thisViewModel.introduction.set(it.introduction);
                _thisViewModel.images.value = it.images;

                _binding.llImageRoot.removeAllViews()

                onLoadImage(_selItem )

                titleString = getString( R.string.title_view ) + " : "  + it.name;
                viewModel.titleString.set(titleString);
            } else {

                viewModel.getFragmentManager().beginTransaction()
                    .hide(this)
                    .commit()
            }

        })

        // web view 的觀查者
        viewModel._selWebView.observe(viewLifecycleOwner, Observer {
            if (it.length == 0) {
                viewModel.titleString.set(titleString);
                viewModel.getFragmentManager().beginTransaction()
                    .show(this)
                    .commit()
            } else {
                viewModel.getFragmentManager().beginTransaction()
                    .hide(this)
                    .commit()
            }

        });
    }

    //
    var _runItem : ItemData? = null
    // 抓圖
    fun onLoadImage(item: ItemData? ) {
        if (item != _selItem)
            return;

        if( item == _runItem )
            return ;
        _runItem = item ;

        var images: ArrayList<String> = item!!.images
        var index = 0;
        while (index < images.size) {
            if (index >= images.size)
                return;

            // 讀檔
            val html: String = item.images.get(index);
            index = index + 1;
            //    webView.loadUrl(html)
            //   return
            val job = GlobalScope.launch {
                try {
                    val intent = java.net.URL(html).openStream();
                    val bitmap = BitmapFactory.decodeStream(intent);

                    val activity: Activity = context as Activity
                    // 發送完成
                    activity.runOnUiThread {
                        if (item == _selItem) {

                            val imageView = ImageView(context)
                            imageView.setImageBitmap(bitmap)
                            _binding.llImageRoot.addView(imageView)

                        }
                    };
                } catch (e: Exception) {
                    Log.e("Error", e.message!!);
                    e.printStackTrace();
                }

            }
        }

    }

}