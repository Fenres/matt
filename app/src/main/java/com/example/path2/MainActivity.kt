package com.example.path2

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.path2.R
import java.text.DecimalFormat
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var exampleContainer: LinearLayout
    private lateinit var exampleText: TextView
    private lateinit var answerInput: EditText
    private lateinit var startButton: Button
    private lateinit var checkButton: Button
    private lateinit var statisticsText: TextView

    private var correctAnswers = 0
    private var wrongAnswers = 0
    private var correctResult = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        exampleContainer = findViewById(R.id.exampleContainer)
        exampleText = findViewById(R.id.exampleText)
        answerInput = findViewById(R.id.answerInput)
        startButton = findViewById(R.id.startButton)
        checkButton = findViewById(R.id.checkButton)
        statisticsText = findViewById(R.id.statisticsText)


        startButton.setOnClickListener { generateExample() }
        checkButton.setOnClickListener { checkAnswer() }
    }

    private fun generateExample() {
        exampleContainer.setBackgroundColor(Color.WHITE)
        answerInput.text.clear()
        answerInput.isEnabled = true

        val num1: Int
        val num2: Int
        val operator: Char

        when (Random.nextInt(4)) {
            0 -> { // Сложение
                num1 = Random.nextInt(10, 100)
                num2 = Random.nextInt(10, 100)
                operator = '+'
                correctResult = num1 + num2
            }
            1 -> { // Вычитание
                num1 = Random.nextInt(10, 100)
                num2 = Random.nextInt(10, num1 + 1)
                operator = '-'
                correctResult = num1 - num2
            }
            2 -> { // Умножение
                num1 = Random.nextInt(10, 100)
                num2 = Random.nextInt(10, 100)
                operator = '*'
                correctResult = num1 * num2
            }
            else -> { // Деление
                num2 = Random.nextInt(2, 50)
                val quotient = Random.nextInt(2, 50)
                num1 = num2 * quotient
                operator = '/'
                correctResult = quotient
            }
        }

        exampleText.text = "$num1 $operator $num2 ="
        startButton.isEnabled = false
        checkButton.isEnabled = true
    }

    private fun checkAnswer() {
        val userAnswer = answerInput.text.toString().toIntOrNull()
        if (userAnswer == null) {
            answerInput.error = "Введите целое число"
            return
        }

        answerInput.isEnabled = false
        checkButton.isEnabled = false
        startButton.isEnabled = true

        if (userAnswer == correctResult) {
            exampleContainer.setBackgroundColor(Color.GREEN)
            correctAnswers++
        } else {
            exampleContainer.setBackgroundColor(Color.RED)
            wrongAnswers++
        }

        updateStatistics()
    }

    private fun updateStatistics() {
        val total = correctAnswers + wrongAnswers
        val percentage = if (total > 0) {
            (correctAnswers.toDouble() / total * 100)
        } else 0.0

        val df = DecimalFormat("0.00")
        statisticsText.text = "Всего примеров: $total\n" +"Правильно: $correctAnswers\n" +
                "Неправильно: $wrongAnswers\n" +
                "Процент: ${df.format(percentage)}%"

    }
}