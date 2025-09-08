package com.example.lab_week_02_b

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button // Import Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout

class ResultActivity : AppCompatActivity() {
    companion object {
        private const val COLOR_KEY = "COLOR_KEY"
        private const val ERROR_KEY = "ERROR_KEY"
    }

    private lateinit var backgroundScreen: ConstraintLayout
    private lateinit var resultMessage: TextView
    private lateinit var backButton: Button // Declare backButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        backgroundScreen = findViewById(R.id.background_screen)
        resultMessage = findViewById(R.id.color_code_result_message)
        backButton = findViewById(R.id.back_button) // Initialize backButton

        if (intent != null) {
            val colorCode = intent.getStringExtra(COLOR_KEY)
            try {
                // Ensure colorCode is not null and has a '#' prefix for Color.parseColor
                // If your input already includes '#', you can remove the "#" + part
                val parsedColor = Color.parseColor(if (colorCode?.startsWith("#") == true) colorCode else "#$colorCode")
                backgroundScreen.setBackgroundColor(parsedColor)
                resultMessage.text = getString(R.string.color_code_result_message,
                    colorCode?.uppercase())
            } catch (ex: IllegalArgumentException) {
                // Send an error back to MainActivity
                Intent().let { errorIntent ->
                    errorIntent.putExtra(ERROR_KEY, true)
                    setResult(Activity.RESULT_OK, errorIntent)
                    finish() // Finish ResultActivity if color is invalid
                }
                return // Exit onCreate early if there's an error
            } catch (ex: StringIndexOutOfBoundsException) { // Catch if colorCode is too short
                Intent().let { errorIntent ->
                    errorIntent.putExtra(ERROR_KEY, true) // You can use the same error key or a different one
                    setResult(Activity.RESULT_OK, errorIntent)
                    finish()
                }
                return
            }
        }

        backButton.setOnClickListener {
            // Simply finish this activity to go back to the previous one (MainActivity)
            finish()
        }
    }
}
