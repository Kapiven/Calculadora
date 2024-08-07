package uvg.edu.gt.calculadora

//Para utilizar la calculadora debe de ingresar una expresión en formato postfix y la calculadora hará la operación :)

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import calculate
import infixToPostfix

class MainActivity : AppCompatActivity() {

    private lateinit var editText: EditText
    private var lastNumeric: Boolean = false
    private var stateError: Boolean = false
    private var lastDot: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editText = findViewById(R.id.editText)

        val buttons = listOf(
            R.id.button0, R.id.button1, R.id.button2, R.id.button3, R.id.button4,
            R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9,
            R.id.buttonAdd, R.id.buttonSubtract, R.id.buttonMultiply, R.id.buttonDivide,
            R.id.buttonDot, R.id.buttonEquals, R.id.buttonClear
        )

        for (id in buttons) {
            findViewById<Button>(id).setOnClickListener { onButtonClick(it as Button) }
        }
    }

    private fun onButtonClick(button: Button) {
        when (button.id) {
            R.id.buttonClear -> {
                editText.text.clear()
                lastNumeric = false
                stateError = false
                lastDot = false
            }
            R.id.buttonEquals -> {
                onEqual()
            }
            else -> {
                editText.append(button.text)
                lastNumeric = true
            }
        }
    }

    private fun onEqual() {
        if (lastNumeric && !stateError) {
            val txt = editText.text.toString()
            try {
                val postfix = infixToPostfix(txt)
                val result = calculate(postfix)
                editText.setText(result.toString())
                lastDot = true
            } catch (ex: Exception) {
                editText.setText("Syntax Error")
                stateError = true
                lastNumeric = false
            }
        }
    }
}
