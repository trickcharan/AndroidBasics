package com.example.techsession

import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat


class GameActivity : AppCompatActivity() {
    private lateinit var buttons: Array<Button>
    private lateinit var buttonRestart: Button
    private lateinit var textView: TextView

    companion object {
        const val PLAYER_O = 0
        const val PLAYER_X = 1
    }

    var activePlayer = PLAYER_O
    private var isGameActive = true
    private var filledPositions = intArrayOf(-1, -1, -1, -1, -1, -1, -1, -1, -1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        buttons = Array(9) { i ->
            val button = Button(this)
            button.tag = i
            button
        }
        textView = findViewById(R.id.UserTurnTxt)
        buttons[0] = findViewById(R.id.Button0)
        buttons[1] = findViewById(R.id.Button1)
        buttons[2] = findViewById(R.id.Button2)
        buttons[3] = findViewById(R.id.Button3)
        buttons[4] = findViewById(R.id.Button4)
        buttons[5] = findViewById(R.id.Button5)
        buttons[6] = findViewById(R.id.Button6)
        buttons[7] = findViewById(R.id.Button7)
        buttons[8] = findViewById(R.id.Button8)
        buttonRestart = findViewById(R.id.RestartGameBtn)
        buttons.forEach { button ->
            button.setOnClickListener {
                onButtonClick(button)
            }
        }
        buttonRestart.setOnClickListener {
            restartGame()
        }
    }

    private fun onButtonClick(view: View) {
        val clickedBtn:Button = view as Button

        //if Game is not Active we will do nothing
        if (!isGameActive) return

        //Getting Tag of the clicked button

        //Getting Tag of the clicked button
        val clickedTag = view.getTag().toString().toInt()


        //if the user clicks the Already clicked button we will do nothing
        if (filledPositions[clickedTag] != -1) {
            //Do nothing
            return
        }

        //we will set update the value in array with the Constant value of user
        filledPositions[clickedTag] = activePlayer


        //if the Active player is O we will set the button text to O and change color and we will set next user as X
        if (activePlayer == PLAYER_O) {
            clickedBtn.text = "o"
            clickedBtn.setBackgroundColor(Color.RED)
            activePlayer = PLAYER_X
            textView.text = "Player X -Turn"
        } else {
            clickedBtn.text = "x"
            activePlayer = PLAYER_O
            clickedBtn.setBackgroundColor(Color.GREEN)
            textView.text = "Player O -Turn"
        }

        //check for winner
        if(!checkWinner())
            checkDraw()

    }

    private fun checkDraw() {
        var count = 0

        //we will check all the values in the array and increase count if it is not equal to default value
        for (i in 0..8) {
            if (filledPositions[i] != -1) {
                count++
            }
        }
        //if all the values in array are not equal to default value  we will show as the game is draw
        if (count == 9) {
            //Check for whether any player won the game or not before showing draw
            if (checkWinner()) return
            showWinner("Game is Draw")
            isGameActive = false //setting the game to inactive
        }
    }

    private fun checkWinner(): Boolean {

        //Assigning all the possible ways to win in an Array
        val winningPosition = arrayOf(
            intArrayOf(0, 1, 2),
            intArrayOf(3, 4, 5),
            intArrayOf(6, 7, 8),
            intArrayOf(0, 3, 6),
            intArrayOf(1, 4, 7),
            intArrayOf(2, 5, 8),
            intArrayOf(0, 4, 8),
            intArrayOf(2, 4, 6)
        )

        //Getting all the winning positions
        for (i in 1 until winningPosition.size) {
            val value0 = winningPosition[i][0]
            val value1 = winningPosition[i][1]
            val value2 = winningPosition[i][2]

            //checking whether the value is equal to default value or not
            if (filledPositions[value0] != -1) {
                if (filledPositions[value0] == filledPositions[value1] && filledPositions[value1] == filledPositions[value2]) {

                    //Making the game inActive
                    isGameActive = false

                    //Deciding winner
                    if (filledPositions[value0] == PLAYER_O) {
                        showWinner("Winner is Player O")
                    } else {
                        showWinner("Winner is Player X")
                    }
                    //Returning true to prevent showing the result form draw
                    return true
                }
            }
        }
        return false
    }

    private fun showWinner(winner: String) {
        //Creating a Alert Dialog to show the winner and for Restarting the Game
        AlertDialog.Builder(this).apply {
            setTitle(winner)
            setCancelable(false)
            setPositiveButton("Restart Game"
            ) { p0, p1 -> restartGame() }
            show()
        }

    }


    private fun restartGame() {

        //Setting the default player to Player O
        activePlayer = PLAYER_O

        //Setting the all the values to default
        filledPositions = intArrayOf(-1, -1, -1, -1, -1, -1, -1, -1, -1)


        buttons.forEach {
            //Setting all the Text on the buttons to empty
            it.text = ""

            //setting the default color for buttons
            it.setBackgroundColor(ContextCompat.getColor(this,R.color.button_color))
        }

        textView.text = "Player O Turn"

        //making the game active again
        isGameActive = true
    }
}