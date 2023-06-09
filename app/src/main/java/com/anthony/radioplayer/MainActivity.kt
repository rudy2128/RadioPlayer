package com.anthony.radioplayer

import android.content.ActivityNotFoundException
import android.content.Intent
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.widget.Button
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.Toast
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private lateinit var btnPlay:ImageButton
    private lateinit var btnPause:ImageButton
    private lateinit var btnWa:ImageButton
    private lateinit var progressBar: ProgressBar
    private lateinit var mediaPlayer:MediaPlayer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnPlay = findViewById(R.id.btnPlay)
        btnPause = findViewById(R.id.btnPause)
        btnWa = findViewById(R.id.btn_wa)
        progressBar = findViewById(R.id.progressbar)
        btnPause.visibility = View.INVISIBLE

        btnWa.setOnClickListener {

            sendMessage()
        }


        btnPlay.setOnClickListener {
            playing()
        }

        btnPause.setOnClickListener {
            pause()
        }

    }

    private fun sendMessage() {
        val number = "6287837800900"
        val url =   "https://api.whatsapp.com/send?phone=${number}&text=Hallo%20apa%20kabar?%20"

        val i = Intent(Intent.ACTION_VIEW).apply {
            this.data = Uri.parse(url)
            this.`package` = "com.whatsapp"
        }
        try {
            startActivity(i)
        } catch (ex : ActivityNotFoundException){
           ex.message
        }

    }


    private fun pause() {
        if (mediaPlayer.isPlaying){
            mediaPlayer.stop()
            mediaPlayer.reset()
            mediaPlayer.release()
            btnPause.visibility = View.INVISIBLE
            btnPlay.visibility = View.VISIBLE
            Toast.makeText(applicationContext,"Audio has been  stop..", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(applicationContext,"Audio not playing..", Toast.LENGTH_SHORT).show()

        }
    }

    private fun playing() {
        Toast.makeText(applicationContext,"Mohon tunggu sebentar !!",Toast.LENGTH_SHORT).show();
        btnPlay.visibility = View.INVISIBLE
        val radioUrl = "http://radio.b3on.net:8080/gombong"
//        val radioUrl = "http://classical.radionightingale.org"
        mediaPlayer = MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setLegacyStreamType(AudioManager.STREAM_MUSIC)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )

            try {
                setDataSource(radioUrl)
                prepare()
                start()
                btnPlay.visibility = View.INVISIBLE
                btnPause.visibility = View.VISIBLE
                progressBar.visibility = View.GONE

            }catch (e:IllegalArgumentException){
                e.printStackTrace()

            }catch (e:IllegalStateException){
                e.printStackTrace()
            }catch (e:IOException){
                e.printStackTrace()
            }


        }

    }


}