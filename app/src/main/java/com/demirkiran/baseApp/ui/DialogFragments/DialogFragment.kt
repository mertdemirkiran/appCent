package com.demirkiran.baseApp.ui.DialogFragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.bumptech.glide.Glide
import com.demirkiran.baseApp.R
import com.demirkiran.baseApp.data.Picture
import com.demirkiran.baseApp.databinding.FragmentDialogBinding



class DialogPictureFragment : DialogFragment() {
    companion object {
        fun newInstance(picture: Picture): DialogPictureFragment {
            val args = Bundle()
            args.putParcelable("Nesne", picture)
            val fragment = DialogPictureFragment()
            fragment.arguments = args
            return fragment
        }
    }
    private var _binding: FragmentDialogBinding? = null
    // This property is only valid between onCreateView and
// onDestroyView.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    fun show(manager: FragmentManager) {
        super.show(manager, javaClass.simpleName)
    }


    private fun clicks() {
   /*     dataBinding.btnNegative.setOnClickListener { dismiss() }
        dataBinding.btnPositive.setOnClickListener { viewModel.forgotPasswordCall() }*/
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dialog, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        clicks()
    }
    fun loadUI(){
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
       var binding = FragmentDialogBinding
            .inflate(LayoutInflater.from(context))
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setView(binding.root)
        var pic = requireArguments().getParcelable<Picture>("Nesne")!!
        binding.tvCaptureDate.text=pic.earth_date
        binding.tvVehicleName.text=pic.rover.name
        binding.tvCameraName.text=pic.camera.full_name
        binding.tvMissionStatu.text=pic.rover.status
        binding.tvLaunchDate.text=pic.rover.launch_date
        binding.tvLandingDate.text=pic.rover.landing_date
        Glide.with(this).load(pic.img_src).into(binding.imageView);

        binding.btnPositive.setOnClickListener {
            dismiss()
        }
        return builder.create()
    }

}