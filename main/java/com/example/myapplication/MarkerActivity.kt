package com.example.myapplication

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.PersistableBundle
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class MarkerActivity : AppCompatActivity(){

    var path:String?=""
    var pos:String?=""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_marker)
        path = intent.getStringExtra("path")
        pos = intent.getStringExtra("pos")

        if(path!=null && path!="")
        {
            val imageView: ImageView = findViewById(R.id.imageView)
            val uriPhoto = Uri.parse(path)
            imageView.setImageURI(uriPhoto)
        }
        /*val b = AlertDialog.Builder(this)
        b.setMessage("Help")
        b.create().show()*/
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun getPermissions() {
        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),1)
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun createImageFile(): File? {
        val imageFileName = "PNG_" +
                SimpleDateFormat("yyyyMMdd_HHmmss").format(Date()) +
                "_"
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val image = File.createTempFile(imageFileName, ".png", storageDir)

        path = image.absolutePath
        return image
    }


    @RequiresApi(Build.VERSION_CODES.M)
    fun makeOnClick(view: View)
    {
        getPermissions()
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (true/*takePictureIntent.resolveActivity(packageManager) != null*/) {

            val photoFile: File? = createImageFile()

            if (photoFile != null) {
                val photoURI: Uri = FileProvider.getUriForFile(
                        this,
                        "com.example.myapplication",
                        photoFile
                )
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                startActivityForResult(takePictureIntent, 1)

            }

        }

    }

    fun saveOnClick(view: View)
    {

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode ==1)
        {
            val imageView: ImageView = findViewById(R.id.imageView)
            val uriPhoto = Uri.parse(path)
            imageView.setImageURI(uriPhoto)
        }
    }
}