package com.example.squarecounter

import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.squarecounter.ui.theme.BoardView
import com.example.squarecounter.ui.theme.Othello
import com.example.squarecounter.ui.theme.SquareCounterTheme
import kotlin.text.clear

class MainActivity : ComponentActivity() {
    private lateinit var gameBoard: Othello
    private lateinit var gameView: BoardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        enableEdgeToEdge()
        gameBoard = Othello()
        gameView = findViewById<BoardView>(R.id.boardView)
        gameView.board = gameBoard
    }
    fun newGame(view: View){
        gameView.resetView()
        gameView.invalidate()
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SquareCounterTheme {
        Greeting("Android")
    }
}