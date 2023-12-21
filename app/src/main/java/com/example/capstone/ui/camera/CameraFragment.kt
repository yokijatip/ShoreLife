package com.example.capstone.ui.camera

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.capstone.R
import com.example.capstone.databinding.FragmentCameraBinding
import com.example.capstone.ui.main.HomeFragment

class CameraFragment : Fragment() {

    private var _binding: FragmentCameraBinding? = null
    private val binding get() = _binding!!
    private lateinit var imageView: ImageView

    private var currentImageUri: Uri? = null

    private fun allPermissionsGranted() = ContextCompat.checkSelfPermission(
        this.requireContext(),
        REQUIRED_PERMISSION
    ) == PackageManager.PERMISSION_GRANTED

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(this@CameraFragment.requireContext(), "Permission request granted", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this@CameraFragment.requireContext(), "Permission request denied", Toast.LENGTH_LONG).show()
            }
        }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCameraBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imageView = view.findViewById(R.id.content_image)

        binding.btnBack.setOnClickListener {
            navigateBack()
        }

        binding.btnCamera.setOnClickListener {

        }

        binding.btnGallery.setOnClickListener {
            startGallery()
        }

    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherGallery =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri: Uri? ->
            if (uri != null) {
                currentImageUri = uri
                //  showImage()
                convertImageToBitmap()
            } else {
                Log.d("Image Uri", "Photo Picker : No Media Selected")
            }
        }

    private fun convertImageToBitmap() {
        currentImageUri?.let {
            val bitmap = BitmapFactory.decodeStream(requireActivity().contentResolver.openInputStream(it))
            imageView.setImageBitmap(bitmap)
//            outputGenerator(bitmap)
        }
    }

    private fun navigateBack() {
        requireActivity().supportFragmentManager.popBackStack()
    }

    companion object {
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
        fun instance(): CameraFragment {
            val fragment = CameraFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}