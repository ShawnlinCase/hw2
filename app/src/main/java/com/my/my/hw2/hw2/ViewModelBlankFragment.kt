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
import android.widget.*
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.my.my.hw2.hw2.databinding.FragmentViewModelBlankBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ViewModelBlankFragment : Fragment() {

    val TAG = "ViewModelBlankFragment"

    var titleString : String = "列表"

    companion object {
        fun newInstance() = ViewModelBlankFragment()
    }

    //
    lateinit var _root: View;
    private lateinit var viewModel: ViewModelBlankViewModel

    lateinit var _binding: FragmentViewModelBlankBinding

    lateinit var _adapter: ItemDataArrayAdapter // = ItemDataArrayAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // 設定 build 的方法
        // class 看 id ，去底線後頭字大寫
        // 如 id 是 fragment_view_model_blank
        //    =>    ViewModelBlankViewModel
        _binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_view_model_blank, container, false
        )
        _root = _binding.root
        //  _root = inflater.inflate(R.layout.fragment_view_model_blank, container, false)
        _adapter = ItemDataArrayAdapter(_root.context)
        return _root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProvider(this).get(ViewModelBlankViewModel::class.java)
        // TODO: Use the ViewModel
        /*
        val text = _root.findViewById<TextView>(R.id.tv_test)
        viewModel.intData.observe(viewLifecycleOwner, Observer {
            // updating data in displayMsg
            text.text = it
        })


*/

        setViewModel(viewModel);
    }

    fun setViewModel(viewModel: ViewModelBlankViewModel) {
        if (viewModel == null)
            return;
        this.viewModel = viewModel
        _binding.viewModel = viewModel


        val listView: ListView = _root.findViewById<ListView>(R.id.list_view)
        listView.adapter = _adapter
        listView.setOnItemClickListener { parent, view, position, id ->

            val item: ItemData = _adapter.getItem(position) as ItemData
            this.viewModel.setItemData(item)


        }

        // ListView
        viewModel._items.observe(viewLifecycleOwner, Observer {
            // updating data in displayMsg
            Log.d(TAG, "ok")
            _adapter.setAll(it);
        })

        val adapter_lang = ArrayAdapter.createFromResource(
            context as Activity,
            R.array.lang_name,
            android.R.layout.simple_spinner_dropdown_item
        )
        //
        val spinner: Spinner = _binding.spinner
        spinner.adapter = adapter_lang
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
                try {
                    val name: String = adapter_lang.getItem(pos)!!.toString()
                    val lang = name.split(" ")[0]
                    viewModel.getTraveData(lang)
                    titleString = getString( R.string.title_list )+  " : " + name
                    viewModel.titleString.set( titleString );
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
        // 設定觀查
        viewModel._selItemData.observe(viewLifecycleOwner, Observer {
            // updating data in displayMsg
            if( it == null )
            {
                this.viewModel.titleString.set( titleString );
                viewModel.getFragmentManager().beginTransaction()
                    .show( this )
                    .commit()

            }else
            {
                viewModel.getFragmentManager().beginTransaction()
                    .hide( this )
                    .commit()
            }

        })
    }


}