package com.example.matchball.appsetting.language

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.matchball.R
import com.example.matchball.databinding.ActivityChangeLanguageBinding
import com.example.matchball.home.MainActivity
import java.util.*
import kotlin.system.exitProcess

@Suppress("DEPRECATION")
class ChangeLanguageActivity : AppCompatActivity() {

    private lateinit var changeLanguageBinding: ActivityChangeLanguageBinding
//    lateinit var spinner: Spinner
    lateinit var locale: Locale
    private var currentLanguage = "en"
    private var currentLang: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        changeLanguageBinding = ActivityChangeLanguageBinding.inflate(layoutInflater)
        setContentView(changeLanguageBinding.root)

        initEvents()

        val list = ArrayList<String>()
        list.add("Select Language")
        list.add("English")
        list.add("Tiếng Việt")

        val adapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, list)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        changeLanguageBinding.spinner.adapter = adapter

        changeLanguageBinding.spinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                when (position) {
                    0 -> {
                    }
                    1 -> setLocale("en")
                    2 -> setLocale("vi")
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun initEvents() {
        back()
    }

    private fun back() {
        changeLanguageBinding.back.setOnClickListener {
            finish()
        }
    }

    private fun setLocale(localeName: String) {
        if (localeName != currentLanguage) {
            locale = Locale(localeName)
            val res = resources
            val dm = res.displayMetrics
            val conf = res.configuration
            conf.locale = locale
            res.updateConfiguration(conf, dm)
            val refresh = Intent(this, MainActivity::class.java
            )
            refresh.putExtra(currentLang, localeName)
            startActivity(refresh)
        } else {
            Toast.makeText(this, "Language already selected)!", Toast.LENGTH_SHORT).show();
        }
    }

    override fun onBackPressed() {
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_HOME)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
        finish()
        exitProcess(0)
    }
}

