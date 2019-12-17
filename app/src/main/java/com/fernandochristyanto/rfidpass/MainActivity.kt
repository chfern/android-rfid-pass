package com.fernandochristyanto.rfidpass

import android.nfc.NfcAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import android.content.Intent
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.nfc.Tag
import android.util.Log
import kotlin.experimental.and


class MainActivity : AppCompatActivity() {
    private lateinit var nfcAdapter: NfcAdapter
    private val TAG = MainActivity::class.java.canonicalName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        nfcAdapter = NfcAdapter.getDefaultAdapter(this)
        if (!nfcAdapter.isEnabled) {
            Toast.makeText(this, "NFC not Enabled!", Toast.LENGTH_LONG).show()
            finish()
        } else {
            Toast.makeText(this, "NFC is ready", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onResume() {
        super.onResume()

        val intent = intent
        val action = intent.action

        if (NfcAdapter.ACTION_TECH_DISCOVERED == action!!) {
            Toast.makeText(
                this,
                "onResume() - ACTION_TECH_DISCOVERED",
                Toast.LENGTH_SHORT
            ).show()

            val tag = intent.getParcelableExtra<Tag>(NfcAdapter.EXTRA_TAG)
            if (tag == null) {
                Log.d(TAG, "tag is null")
            } else {
                Log.d(TAG, "tag is NOT null")

                var tagInfo = tag.toString() + "\n"

                tagInfo += "\nTag Id: \n"
                val tagId = tag.id
                tagInfo += "length = " + tagId.size + "\n"
                for (i in tagId.indices) {
                    tagInfo += Integer.toHexString((tagId[i] and 0xFF.toByte()).toInt()) + " "
                }
                tagInfo += "\n"

                val techList = tag!!.getTechList()
                tagInfo += "\nTech List\n"
                tagInfo += "length = " + techList.size + "\n"
                for (i in techList.indices) {
                    tagInfo += techList[i] + "\n "
                }

                Log.d(TAG, tagInfo)
            }
        } else {
            Toast.makeText(
                this,
                "onResume() : " + action!!,
                Toast.LENGTH_SHORT
            ).show()
        }

    }
}