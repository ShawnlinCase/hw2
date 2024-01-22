package com.my.my.hw2.hw2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [WebViewBlankFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WebViewBlankFragment : Fragment() {


    private lateinit var _webView: WebView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_web_view_blank, container, false)
        _webView = root.findViewById(R.id.web_view)
        return root;
    }

    companion object {
    }


    //
    fun setViewModel(viewModel: ViewModelBlankViewModel) {
        // web view 的觀查者
        viewModel._selWebView.observe(viewLifecycleOwner, Observer {
            if (it.length != 0) {
                viewModel.getFragmentManager().beginTransaction()
                    .show(this)
                    .commit()
                // 載入網頁
                val webSettings: WebSettings = _webView.getSettings()
                webSettings.javaScriptEnabled = true
                _webView.setWebViewClient(WebViewClient())
                _webView.loadUrl(it)

                viewModel.titleString.set( getString( R.string.title_web ) + " : "  + it  );
            } else {
                viewModel.getFragmentManager().beginTransaction()
                    .hide(this)
                    .commit()
            }

        });
    }
}