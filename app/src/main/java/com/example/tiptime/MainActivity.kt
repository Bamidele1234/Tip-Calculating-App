package com.example.tiptime

// Import the necessary libraries
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethod
import android.view.inputmethod.InputMethodManager
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.example.tiptime.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import java.text.NumberFormat
import kotlin.math.ceil

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        hideSystemBars() // Hide the system bars
        setContentView(binding.root)

        binding.calculateButton.setOnClickListener{calculateTip()}

       // binding.textInputEdit.setOnKeyListener { view, keyCode, _ -> handleKeyEvent(view, keyCode)}
    }

    private fun calculateTip(){
        // Get and store the user input
        val stringInTextField = binding.textInputEdit.text.toString()

        // Convert the text to a double or a null value if there's no input
        when (val cost = stringInTextField.toDoubleOrNull()) {
            // If the text field is empty
            null -> {
                binding.tipResult.text = ""
                showSnackbar()
                return
            }
            else -> {
                val selectedId = binding.tipOptions.checkedRadioButtonId

                // Compare and store the values of selectedId
                val tipPercentage = when (selectedId) {
                    R.id.option_twenty_percent -> 0.20
                    R.id.option_eighteen_percent -> 0.18
                    else -> 0.15
                }

                // Calculate the tip
                var tip = tipPercentage * cost

                // Round up the value if the switch is checked
                if (binding.roundUpSwitch.isChecked) {
                    tip = ceil(tip)
                }

                // Format the tip
                val formattedTip = NumberFormat.getCurrencyInstance().format(tip)

                // Update the test of tipResult
                binding.tipResult.text = getString(R.string.tip_amount, formattedTip)


            }
        }


    }

    // Show a notification when the text box is empty
    private fun showSnackbar(): Boolean {
        val empty = getString(R.string.denied)
        Snackbar.make(
            findViewById(R.id.constraintLayout),
            empty,
            Snackbar.LENGTH_SHORT
        ).show()
        return true
    }

    // Self explanatory function
    private fun hideSystemBars() {
        val windowInsetsController =
            ViewCompat.getWindowInsetsController(window.decorView) ?: return
        // Configure the behavior of the hidden system bars
        windowInsetsController.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        // Hide both the status bar and the navigation bar
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
    }

    // To hide the keyboard if the enter key is pressed
    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            // Hide the keyboard
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }
}