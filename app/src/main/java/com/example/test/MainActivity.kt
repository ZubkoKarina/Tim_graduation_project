package com.example.test

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.animation.ValueAnimator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.animation.LinearInterpolator
import android.view.animation.Animation
import com.example.test.R.*
import com.example.test.math_actions.*
import com.itextpdf.text.*
import com.itextpdf.text.pdf.PdfPTable
import com.itextpdf.text.pdf.PdfWriter
import java.io.FileOutputStream
import com.google.android.material.bottomsheet.BottomSheetDialog


class MainActivity : AppCompatActivity() {
    private lateinit var btnGenerate: Button
    private var selectedOperation: String? = null
    private var numberOfProblems: Int? = null
    private var numberOfVariants: Int? = null
    private lateinit var metalCard: TextView
    private val textToDisplay = "Додаток для генерування PDF файлу з математичними виразами"
    private var textIndex = 0
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(layout.activity_main)
        metalCard = findViewById(id.metal_card)
        val numverOfVariants = findViewById<EditText>(R.id.numberOfVariants)
        val numberOfProblemsEditText = findViewById<EditText>(R.id.numberOfProblemsEditText)
        btnGenerate = findViewById(id.btnGenerate)


        /*val showBottomSheetDialogButton: Button = findViewById(id.showBottomSheetDialogButton)
        showBottomSheetDialogButton.setOnClickListener {
            //showBottomSheetDialog()
        }*/


        val shine = findViewById<View>(id.shine)
        val animation = AnimationUtils.loadAnimation(this, anim.shine_animation)
        shine.startAnimation(animation)


        // Загрузите и запустите анимацию
        //val shineAnimation = AnimationUtils.loadAnimation(this, R.anim.shine_animation)
        //metalCard.startAnimation(shineAnimation)

        // Начните эффект печатания текста
        startTextTypingEffect()

        // Удалите второй блок onCreate и вставьте содержимое сюда
        // (код ниже уже вставлен из второго блока onCreate)
        //karina
        //val myTextView: TextView = findViewById(R.id.title)
        //myTextView.visibility = View.GONE
        /*myTextView.setOnClickListener {
            val animation = AnimationUtils.loadAnimation(this, R.anim.textview_animation)
            myTextView.startAnimation(animation)
        }*/
        //karina end
        //val btnGenerate = findViewById<Button>(R.id.btnGenerate)
        //val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)
        //val linearForRadioGroup2 = findViewById<LinearLayout>(R.id.linearForRadioGroup2)
        //linearForRadioGroup2.visibility = View.GONE
        //linearForRadioGroup2.isEnabled = false
        btnGenerate.isEnabled = false

        val numberOfProblemsTextWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                numberOfProblems = s.toString().toIntOrNull()
                checkGenerateButtonState(btnGenerate, numberOfProblemsEditText, numverOfVariants)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Do nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Do nothing
            }
        }

        val numberOfVariantsTextWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                numberOfVariants = s.toString().toIntOrNull()
                checkGenerateButtonState(btnGenerate, numberOfProblemsEditText, numverOfVariants)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Do nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Do nothing
            }
        }

        numberOfProblemsEditText.addTextChangedListener(numberOfProblemsTextWatcher)
        numverOfVariants.addTextChangedListener(numberOfVariantsTextWatcher)

        /*radioGroup.setOnCheckedChangeListener { _, checkedId ->
            val radioButton = findViewById<RadioButton>(checkedId)
            selectedOperation = radioButton.text.toString()
            checkGenerateButtonState(btnGenerate, numberOfProblemsEditText, numberOfVariantsEditText)
        }*/

        btnGenerate.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    0
                )
            } else {
                generatePdf()
            }
        }
        startRotationAnimation()
    }
    private fun startRotationAnimation() {
        val metalCard1 = findViewById<TextView>(R.id.metal_card1)
        val rotateUp = AnimationUtils.loadAnimation(this, R.anim.rotate_10_up)
        val rotateDown = AnimationUtils.loadAnimation(this, R.anim.rotate_10_down)



        rotateDown.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}
            override fun onAnimationRepeat(animation: Animation) {}
            override fun onAnimationEnd(animation: Animation) {
                metalCard1.startAnimation(rotateUp)
            }
        })
        rotateUp.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}
            override fun onAnimationRepeat(animation: Animation) {}
            override fun onAnimationEnd(animation: Animation) {
                metalCard1.startAnimation(rotateDown)
            }
        })

        metalCard1.startAnimation(rotateUp)
    }




    /*private fun showBottomSheetDialog() {
        val bottomSheetDialog = BottomSheetDialog(this)
        val bottomSheetView = layoutInflater.inflate(layout.bottom_sheet_dialog, null)
        bottomSheetDialog.setContentView(bottomSheetView)
        bottomSheetDialog.show()



        /*chooseOperationButton.setOnClickListener {
            val checkedId = radioGroup.checkedRadioButtonId
            val radioButton = bottomSheetView.findViewById<RadioButton>(checkedId)
            selectedOperation = radioButton.text.toString()
            //checkGenerateButtonState(btnGenerate, numberOfProblemsEditText, numverOfVariants)
            bottomSheetDialog.dismiss()
        }*/
    }*/

    private fun startTextTypingEffect() {
        val handler = Handler(Looper.getMainLooper())
        val delay: Long = 100 // Задержка в миллисекундах между выводом каждого символа
        handler.postDelayed(object : Runnable {
            override fun run() {
                if (textIndex <= textToDisplay.length) {
                    metalCard.text = textToDisplay.subSequence(0, textIndex)
                    textIndex++
                    handler.postDelayed(this, delay)
                }
            }
        }, delay)
    }


    private fun generatePdf() {
        try {
            for (variant in 1..numberOfVariants!!) {
                val document = Document()
                val fileName = "Приклади. Варіант $variant.pdf"
                val filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                    .toString() + "/" + fileName
                PdfWriter.getInstance(document, FileOutputStream(filePath))
                document.open()
                val bigBoldFont = Font(Font.FontFamily.UNDEFINED, 24f, Font.BOLD)
                val bigBoldFontTask = Font(Font.FontFamily.UNDEFINED, 18f, Font.BOLD)

                document.add(Chunk("Task for variant $variant:\n\n", bigBoldFont))

                val answersDocument = Document()
                val answersFileName = "Відповіді. Варіант $variant.pdf"
                val answersFilePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                    .toString() + "/" + answersFileName
                PdfWriter.getInstance(answersDocument, FileOutputStream(answersFilePath))
                answersDocument.open()
                answersDocument.add(Chunk("Answers for variant $variant:\n\n", bigBoldFont))

                val table = PdfPTable(3)
                table.widthPercentage = 100f
                table.spacingBefore = 12f



                val answersTable = PdfPTable(3)
                answersTable.widthPercentage = 100f
                answersTable.spacingBefore = 12f

                if (selectedOperation == "Додавання") {
                    val dodavannya = Dodavannya()
                    for (i in 1..numberOfProblems!!) {
                        val example = dodavannya.generateExample()
                        table.addCell(Phrase(Chunk("$i) $example", bigBoldFontTask)))
                        answersTable.addCell(Phrase(Chunk("$i) ${dodavannya.solveExample(example)}", bigBoldFontTask)))
                    }
                }

                if (selectedOperation == "Віднімання") {
                    val vidnimannya = Vidnimannya()
                    for (i in 1..numberOfProblems!!) {
                        val example = vidnimannya.generateExample()
                        table.addCell(Phrase(Chunk("$i) $example", bigBoldFontTask)))
                        answersTable.addCell(Phrase(Chunk("$i) ${vidnimannya.solveExample(example)}", bigBoldFontTask)))
                    }
                }

                if (selectedOperation == "Множення") {
                    val mnojennya = Mnojennya()
                    for (i in 1..numberOfProblems!!) {
                        val example = mnojennya.generateExample()
                        table.addCell(Phrase(Chunk("$i) $example", bigBoldFontTask)))
                        answersTable.addCell(Phrase(Chunk("$i) ${mnojennya.solveExample(example)}", bigBoldFontTask)))
                    }
                }

                if (selectedOperation == "Ділення") {
                    val dilennya = Dilennya()
                    for (i in 1..numberOfProblems!!) {
                        val example = dilennya.generateExample()
                        table.addCell(Phrase(Chunk("$i) $example", bigBoldFontTask)))
                        answersTable.addCell(Phrase(Chunk("$i) ${dilennya.solveExample(example)}", bigBoldFontTask)))
                    }
                }
                if (selectedOperation == "Знаходження кореня") {
                    val rootFromNumber = RootFromNumber()
                    for (i in 1..numberOfProblems!!) {
                        val example = rootFromNumber.generateExample()

                        val examplePhrase = Phrase(Chunk("$i) √$example", bigBoldFontTask))
                        examplePhrase.setLeading(24f)
                        table.addCell(examplePhrase)

                        val answerPhrase = Phrase(Chunk("$i) ${rootFromNumber.solveExample(example)}", bigBoldFontTask))
                        answerPhrase.setLeading(24f)
                        answersTable.addCell(answerPhrase)
                    }
                }
                if (selectedOperation == "Прості логарифми") {
                    val simpleLog = SimpleLog()
                    for (i in 1..numberOfProblems!!) {
                        val example = simpleLog.generateExample()

                        val examplePhrase = Phrase(Chunk("$i) log${example.second}(${example.first})", bigBoldFontTask))
                        examplePhrase.setLeading(24f)
                        table.addCell(examplePhrase)

                        val answerPhrase = Phrase(Chunk("$i) ${simpleLog.solveExample(example)}", bigBoldFontTask))
                        answerPhrase.setLeading(24f)
                        answersTable.addCell(answerPhrase)
                    }
                }
                if (selectedOperation == "Степінь числа") {
                    val stepinChisla = StepinChisla()
                    for (i in 1..numberOfProblems!!) {
                        val example = stepinChisla.generateExample()

                        val examplePhrase = Phrase(Chunk("$i) ${example.first}^${example.second}", bigBoldFontTask))
                        examplePhrase.setLeading(24f)
                        table.addCell(examplePhrase)

                        val answerPhrase = Phrase(Chunk("$i) ${stepinChisla.solveExample(example)}", bigBoldFontTask))
                        answerPhrase.setLeading(24f)
                        answersTable.addCell(answerPhrase)
                    }
                }
                if (selectedOperation == "Факторіал числа") {
                    val factorialChisla = FactorialChisla()
                    for (i in 1..numberOfProblems!!) {
                        val example = factorialChisla.generateExample()
                        table.addCell(Phrase(Chunk("$i) $example!", bigBoldFontTask)))
                        answersTable.addCell(Phrase(Chunk("$i) ${factorialChisla.solveExample(example)}", bigBoldFontTask)))
                    }
                }


                document.add(table)
                answersDocument.add(answersTable)

                document.close()
                answersDocument.close()

                Toast.makeText(this, "PDF згенеровано", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            // Обробка помилок
        }
    }

    private fun checkGenerateButtonState(btnGenerate: Button, numberOfProblemsEditText: EditText, numberOfVariantsEditText: EditText) {
        btnGenerate.isEnabled = selectedOperation != null && numberOfProblems != null && numberOfProblemsEditText.text.isNotEmpty() && numberOfVariants != null && numberOfVariantsEditText.text.isNotEmpty()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 0) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                generatePdf()
            } else {
                Toast.makeText(this, "Дозвіл на запис не надано", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
