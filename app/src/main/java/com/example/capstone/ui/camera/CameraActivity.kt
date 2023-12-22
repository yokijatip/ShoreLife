package com.example.capstone.ui.camera

import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.example.capstone.R
import com.example.capstone.databinding.ActivityCameraBinding
import com.example.capstone.util.Tools
import java.util.concurrent.ExecutorService

class CameraActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCameraBinding

    //    Camera X
    private var imageCapture: ImageCapture? = null
    private var currentImage: Uri? = null
    private lateinit var cameraExecutor: ExecutorService
    private var cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)

        binding.apply {
            changeCamera.setOnClickListener {
                cameraSelector =
                    if (cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA) CameraSelector.DEFAULT_FRONT_CAMERA
                    else CameraSelector.DEFAULT_BACK_CAMERA
                startCamera()
                Toast.makeText(this@CameraActivity, "Click", Toast.LENGTH_LONG).show()
            }
            imageCapture.setOnClickListener {
                takePhoto()
            }
        }


    }

    public override fun onResume() {
        super.onResume()
        startCamera()
    }

    //    Function Start Camera
    private fun startCamera() {

        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.viewFinder.surfaceProvider)
                }
            imageCapture = ImageCapture.Builder().build()

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    this,
                    cameraSelector,
                    preview,
                    imageCapture

                )
            } catch (exc: Exception) {
                Toast.makeText(this@CameraActivity, "Failed Open Camera X", Toast.LENGTH_LONG).show()
                Log.e(TAG, "startCamera: ${exc.message}")
            }
        }, ContextCompat.getMainExecutor(this))
    }

    private fun takePhoto() {
        val imageCapture = imageCapture ?: return
        val photoFile = Tools().createCustomTempFile(application)
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    Toast.makeText(this@CameraActivity, "Image Captured", Toast.LENGTH_LONG).show()
                    val intent = Intent()
                    intent.putExtra(EXTRA_CAMERAX_IMAGE, outputFileResults.savedUri.toString())
                    setResult(CAMERAX_RESULT, intent)
                    finish()
                }

                override fun onError(exception: ImageCaptureException) {
                    Toast.makeText(this@CameraActivity, "Failed Image Captured", Toast.LENGTH_LONG).show()
                    Log.e(TAG, "onError: ${exception.message}")
                }

            }
        )
    }

    companion object {
        private const val TAG = "CameraActivity"

        //        Keperluan untuk menyimpan photo dan melihat photo dan juga mengirim photo ke activity yang di tuju
        const val EXTRA_CAMERAX_IMAGE = "CameraX Image"
        const val CAMERAX_RESULT = 200
    }
}