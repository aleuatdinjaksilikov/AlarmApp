package com.example.myalarmapp.ui.alarm.dialog

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.myalarmapp.R
import com.example.myalarmapp.databinding.LayoutDialogLabelBinding

class DialogAddLabel:DialogFragment(R.layout.layout_dialog_label) {
    private lateinit var binding: LayoutDialogLabelBinding
    private var onClickOkayListener:((String) -> Unit)? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = LayoutDialogLabelBinding.bind(view)

//        binding.btnOkay.setOnClickListener {
//            onClickOkayListener?.invoke(binding.etDialogLabel.text.toString())
//        }
//        binding.btnCancel.setOnClickListener {
//            this.dismiss()
//        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE,R.style.DialogThemeTransparent)
    }

    fun setOnCLickOkayListener(block: (String) -> Unit) {
        onClickOkayListener = block
    }

    override fun onStart() {
        super.onStart()
        if (dialog != null && dialog?.window != null) {
            dialog?.window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }
    }
}