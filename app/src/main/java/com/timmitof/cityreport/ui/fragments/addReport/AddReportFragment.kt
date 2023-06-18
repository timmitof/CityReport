package com.timmitof.cityreport.ui.fragments.addReport

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.google.android.gms.location.*
import com.timmitof.cityreport.core.base.BaseFragment
import com.timmitof.cityreport.core.utils.Status
import com.timmitof.cityreport.core.utils.bitmapToBase64
import com.timmitof.cityreport.databinding.FragmentAddReportBinding
import com.timmitof.cityreport.models.Coordinate
import dagger.hilt.android.AndroidEntryPoint

private const val CAMERA_REQUEST_CODE = 200
private const val CAMERA_PERMISSION_REQUEST_CODE = 100
private const val LOCATION_PERMISSION_REQUEST_CODE = 101

@AndroidEntryPoint
class AddReportFragment : BaseFragment<FragmentAddReportBinding>(FragmentAddReportBinding::inflate) {

    val viewModel: AddReportViewModel by viewModels()
    var image: String? = null
    var location: Location? = null
    private lateinit var mFusedLocationClient: FusedLocationProviderClient

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        binding.imageReport.setOnClickListener {
            if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.CAMERA), CAMERA_PERMISSION_REQUEST_CODE)
            } else {
                openCamera()
            }
        }

        binding.sendButton.setOnClickListener {
            getLocation()
            if (
                !image.isNullOrBlank() &&
                !binding.titleInput.text.isNullOrBlank()
            ) {
                viewModel.sendComplaint(
                    coordinate = Coordinate(latitude = location?.latitude ?: 0.0, longitude = location?.longitude ?: 0.0, complaintId = null),
                    description = binding.descriptionInput.text?.toString() ?: "",
                    name = binding.titleInput.text?.toString() ?: "",
                    image = image!!
                )
            } else {
                Toast.makeText(requireContext(), "Заполните все поля", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun bindData() {
        super.bindData()
        viewModel.reportSend.observe(viewLifecycleOwner) {
            when(it.status) {
                Status.SUCCESSFUL -> {
                    Toast.makeText(requireContext(), "Успешно", Toast.LENGTH_SHORT).show()
                    reset()
                }
                Status.FAILURE -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                Status.LOADING -> {
                }
            }
        }
    }

    private fun reset() {
        binding.imageReport.setImageURI(null)
        binding.titleInput.text = null
        binding.descriptionInput.text = null
    }

    override fun onResume() {
        super.onResume()
        reset()
        getLocation()
    }

    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, CAMERA_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera()
            }
        } else if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocation()
            } else {
                Toast.makeText(requireContext(), "Разрешение на локацию не получено", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            Glide.with(this).load(imageBitmap).centerCrop().into(binding.imageReport)
            val image = bitmapToBase64(imageBitmap)
            this.image = image
        }
    }

    @SuppressLint("MissingPermission", "SetTextI18n")
    private fun getLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.lastLocation.addOnCompleteListener(requireActivity()) { task ->
                    location = task.result
                }
            } else {
                Toast.makeText(requireContext(), "Please turn on location", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermissionsLocation()
        }
    }

    private fun requestPermissionsLocation() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            LOCATION_PERMISSION_REQUEST_CODE
        )
    }

    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager = requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }
}