package com.imagedownloader.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.cvapp.base.ApiResponseListener
import com.cvapp.base.BaseFragment
import com.cvapp.extenstions.hideKeyboard
import com.cvapp.extenstions.isUrlValid
import com.imagedownloader.R
import com.imagedownloader.databinding.FragmentHomeBinding
import org.jetbrains.anko.toast


class HomeFragment : BaseFragment(), ApiResponseListener {

    private var binding: FragmentHomeBinding? = null
    private val viewModel by lazy { ViewModelProvider(this).get(HomeViewModel::class.java) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getCallbacks(viewModel, this)
        initViews()
    }


    private fun initViews() {
        binding?.etSearch?.setOnEditorActionListener(OnEditorActionListener { arg0, arg1, arg2 ->
            if (arg1 == EditorInfo.IME_ACTION_GO) {
                // search pressed and perform your functionality.
                checkValidation()
            }
            false
        })

    }

    private fun checkValidation() {
        val webUrl = binding?.etSearch?.text.toString()
        if (isUrlValid(webUrl ?: ""))
        //    viewModel.extractImagesFromWeb("https://www.javatpoint.com")
            binding?.webVw?.loadUrl(webUrl);
        else{
            context?.toast("Invalid Url")
        }

    }

    override fun <T> onResponse(it: T) {
        if (it is String) {
            context?.toast(it as String)
        }
    }

    override fun onError(it: Throwable) {
        context?.toast(it.message.toString())
    }

}


