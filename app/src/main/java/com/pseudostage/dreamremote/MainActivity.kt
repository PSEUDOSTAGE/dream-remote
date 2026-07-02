package com.pseudostage.dreamremote

import android.os.Bundle
import android.view.SoundEffectConstants
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.LocalView
//import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pseudostage.dreamremote.ui.theme.DreamRemoteTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DreamRemoteTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
@Composable
fun Greeting(modifier: Modifier = Modifier) {
    var dreamNum by remember { mutableStateOf(generateDreamTestNum()) }
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val haptics = LocalHapticFeedback.current
    val view = LocalView.current
    val scope = rememberCoroutineScope()
    Column(
        modifier = modifier.fillMaxSize()
            .fillMaxSize() // Column now takes the whole area the Scaffold gave it
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.weight(0.1f))
        Text(text = dreamNum)
        Spacer(Modifier.weight(2f))
        Button(onClick = {
            view.playSoundEffect(SoundEffectConstants.CLICK)
            haptics.performHapticFeedback(HapticFeedbackType.LongPress) },
            interactionSource = interactionSource,
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isPressed) Color(0xEBFFC7CA) else Color(0xFFDC143C),
            )) { Text("        \n       \n       \n") }
        Spacer(Modifier.weight(0.5f))
        Text(text = "IF OUTSIDE OF SHARED REALITY", color = Color(0xFF505050))
        Text(text = "PRESS TO DO ANYTHING", color = Color(0xFF313131))
        Spacer(Modifier.weight(1f))
        Button(onClick = {
            view.playSoundEffect(SoundEffectConstants.CLICK)
            haptics.performHapticFeedback(HapticFeedbackType.LongPress)
            scope.launch {
                (1..8).forEach { _ ->
                    dreamNum = generateDreamTestNum()
                    delay(50.milliseconds)
                }
                delay(80.milliseconds)
                dreamNum = generateDreamTestNum()
                delay(100.milliseconds)
                dreamNum = generateDreamTestNum()
                delay(120.milliseconds)
                dreamNum = generateDreamTestNum()
                delay(200.milliseconds)
                dreamNum = generateDreamTestNum()
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF808080),   // the button background
                contentColor = Color(0xEB252525)      // the label/icon color
            )) { Text("NEW TEST") }
        Spacer(Modifier.weight(0.1f))
        Text(text = "IN CASE OF NON-MATCH:", color = Color(0xFF313131))
        Text(text = "OUTSIDE OF SHARED REALITY", color = Color(0xFF505050))
        Spacer(Modifier.weight(0.1f))
        Text(text = dreamNum)
    }
}

//@Composable
//@Preview(showBackground = true)
//fun GreetingPreview() {
//    DreamRemoteTheme {
//        Greeting(Modifier)
//    }
//}
fun generateDreamTestNum(): String {
    val random24DigitString = (1..24)
        .map { (0..9).random() }
        .joinToString("")
    return random24DigitString
}