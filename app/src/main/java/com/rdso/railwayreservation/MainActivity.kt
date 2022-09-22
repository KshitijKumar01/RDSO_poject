package com.rdso.railwayreservation

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.*
import android.graphics.pdf.PdfDocument
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.ParcelFileDescriptor
import android.provider.DocumentsContract
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import com.rdso.railwayreservation.databinding.ActivityMainBinding
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private var document : PdfDocument? = null
    private val pdfHeight : Int = 1080
    private val pdfWidth : Int = 720
    private var bmp : Bitmap? = null
    private var scaledBitmap : Bitmap? = null


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        val userId = intent.getStringExtra("user_id")
        val emailId = intent.getStringExtra("email")
        bmp = BitmapFactory.decodeResource(resources, R.drawable.inside_pdf_image)
        scaledBitmap = Bitmap.createScaledBitmap(bmp!!, 100, 100, false)

        binding.btnSearchTrain.setOnClickListener{
            val intent = Intent(this, ShowTrains::class.java)
            startActivity(intent)
        }

        val wholeDetails = intent.getParcelableExtra<Train>("wholeTrainDetails")
        if(wholeDetails != null){
            binding.tvSelectedTrain.visibility = View.VISIBLE
            binding.tvSelectedTrain.text = "Train Name : ${wholeDetails.trainName}"

            binding.tvSelectedTrainNumber.visibility = View.VISIBLE
            binding.tvSelectedTrainNumber.text = "Train Number : ${Constants.trainObj?.trainNo}"

            binding.tvSelectedTrainDeparture.visibility = View.VISIBLE
            binding.tvSelectedTrainDeparture.text = "Departure Time : ${Constants.trainObj?.departureTime}"

            binding.tvSelectedTrainArrival.visibility = View.VISIBLE
            binding.tvSelectedTrainArrival.text = "Arrival Time : ${Constants.trainObj?.arrivalTime}"

            binding.tvSelectedTrainDepartureDate.visibility = View.VISIBLE
            binding.tvSelectedTrainDepartureDate.text = "Departure Date : ${Constants.trainObj?.departureDate}"

            binding.tvSelectedTrainArrivalDate.visibility = View.VISIBLE
            binding.tvSelectedTrainArrivalDate.text = "Arrival Date : ${Constants.trainObj?.arrivalDate}"

            binding.btnBookTicket.visibility = View.VISIBLE

        }

        binding.btnBookTicket.setOnClickListener{
            generatePdf()
        }

    }

    private fun checkPermissions() : Boolean{
        val permission1 : Int = ContextCompat.checkSelfPermission(applicationContext, WRITE_EXTERNAL_STORAGE)
        val permission2 : Int = ContextCompat.checkSelfPermission(applicationContext, READ_EXTERNAL_STORAGE)
        return permission1 == PackageManager.PERMISSION_GRANTED && permission2 == PackageManager.PERMISSION_GRANTED
    }

    private fun generatePdf(){
        document = PdfDocument()
        val pageInfo : PdfDocument.PageInfo = PdfDocument.PageInfo.Builder(pdfWidth, pdfHeight, 1).create()
        val page : PdfDocument.Page = document!!.startPage(pageInfo)

        val canvas : Canvas = page.canvas
        val paintText : Paint = Paint()
        paintText.typeface = Typeface.create(Typeface.DEFAULT_BOLD, Typeface.NORMAL)
        paintText.textSize = 25F
        paintText.color = ContextCompat.getColor(this, R.color.black)
        paintText.textAlign = Paint.Align.CENTER
        canvas.drawText(("Railway Reservation Project RDSO"), 396F, 70F, paintText)
        canvas.drawBitmap(scaledBitmap!!, 25F, 15F, paintText)

        paintText.typeface = Typeface.create(Typeface.DEFAULT_BOLD, Typeface.NORMAL)
        paintText.textSize = 25F
        paintText.color = ContextCompat.getColor(this, R.color.black)
        paintText.textAlign = Paint.Align.CENTER
        canvas.drawText(("Guidelines"), 396F, 550F, paintText)

        val wholeDetails = intent.getParcelableExtra<Train>("wholeTrainDetails")

        paintText.typeface = Typeface.defaultFromStyle(Typeface.NORMAL)
        paintText.color = ContextCompat.getColor(this, R.color.dark_gray)
        paintText.textSize = 20F
        paintText.textAlign = Paint.Align.LEFT

        canvas.drawText("Passenger Name : ${binding.firstName.text} ${binding.lastName.text}", 50F, 170F, paintText)
        canvas.drawText(binding.tvSelectedTrain.text.toString(), 50F, 200F, paintText)
        canvas.drawText(binding.tvSelectedTrainNumber.text.toString(), 50F, 230F, paintText)
        canvas.drawText(binding.tvSelectedTrainDeparture.text.toString(), 50F, 260F, paintText)
        canvas.drawText(binding.tvSelectedTrainDepartureDate.text.toString(), 50F, 290F, paintText)
        canvas.drawText(binding.tvSelectedTrainArrival.text.toString(), 50F, 320F, paintText)
        canvas.drawText(binding.tvSelectedTrainArrivalDate.text.toString(), 50F, 350F, paintText)
        canvas.drawText( "From : ${wholeDetails!!.startStation}", 50F, 380F, paintText)
        canvas.drawText("To : ${wholeDetails.endStation}", 50F, 410F, paintText)
        canvas.drawText("Contact : ${binding.etContact.text}", 50F, 440F, paintText)
        canvas.drawText("Gender : ${binding.etGender.text}", 50F, 470F, paintText)
        canvas.drawText("Address : ${binding.etAddress.text}", 50F, 500F, paintText)

        canvas.drawBitmap(scaledBitmap!!, 25F, 15F, paintText)





        val x : Int = 40
        var y : Int = 600
        val line : String = getString(R.string.pdf_information)
        val lines = line.lines()
        lines.forEach{

            canvas.drawText(it, x.toFloat(), y.toFloat(), paintText)
            y += (paintText.descent() - paintText.ascent()).toInt()
        }

        document!!.finishPage(page)
        createFile()

    }

    private fun createFile(){
        val intent : Intent = Intent(Intent.ACTION_CREATE_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "application/pdf"
        intent.putExtra(Intent.EXTRA_TITLE, "ticket.pdf")
        startActivityForResult(intent, 1)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 1 && resultCode == Activity.RESULT_OK){
            var uri : Uri? = null
            if(data != null){
                uri = data.data
                if(document != null){
                    var pdf : ParcelFileDescriptor? = null
                    try{
                        pdf = contentResolver.openFileDescriptor(uri!!, "w")
                        val fileOutputStream : FileOutputStream = FileOutputStream(pdf?.fileDescriptor)
                        document?.writeTo(fileOutputStream)
                        document?.close()
                        Toast.makeText(this, "PDF saved successfully", Toast.LENGTH_SHORT).show()
                    }catch (e : IOException){
                        try{
                            DocumentsContract.deleteDocument(contentResolver, uri!!)
                        }catch (ex : FileNotFoundException){
                            ex.printStackTrace()
                        }
                        e.printStackTrace()
                    }
                }
            }
        }
    }
}