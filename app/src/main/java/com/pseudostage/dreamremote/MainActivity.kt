/*
 * Dream Remote - Dream Remote for Android. Influence your dreams.
 * Copyright (C) 2026  PSEUDOSTAGE
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.pseudostage.dreamremote

import android.os.Bundle
import android.view.SoundEffectConstants
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pseudostage.dreamremote.ui.theme.DreamRemoteTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DreamRemoteTheme {
                var showHelp by remember { mutableStateOf(false) }
                var showPleh by remember { mutableStateOf(false) }
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    if (showHelp) {
                        DreamRemoteHelp(
                            modifier = Modifier.padding(innerPadding),
                            onReturn = { showHelp = false }
                        )
                    }
                    else if (showPleh) {
                        DreamRemotePleh(
                            modifier = Modifier.padding(innerPadding),
                            onReturn = { showPleh = false }
                    )
                    }else {
                        DreamRemote(
                            modifier = Modifier.padding(innerPadding),
                            onHelp = { showHelp = true },
                            onPleh = { showPleh = true }
                        )
                    }
                }
            }
        }
    }
}
@Composable
fun DreamRemote(modifier: Modifier = Modifier, onHelp: () -> Unit = {}, onPleh: () -> Unit = {}) {
    var dreamNum by remember { mutableStateOf(generateDreamTestNum()) }
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val haptics = LocalHapticFeedback.current
    val view = LocalView.current
    val scope = rememberCoroutineScope()
    Column(
        modifier = modifier.fillMaxSize()
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.weight(0.1f))
        Text(text = dreamNum)
        Button(
            onClick = { onPleh() },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = Color.Transparent,
            ),
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_help_outline_24),
                contentDescription = "Help",
            )
        }
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
        Spacer(Modifier.weight(0.8f))
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
                containerColor = Color(0xFF808080),
                contentColor = Color(0xEB252525)
            )) { Text("NEW TEST") }
        Spacer(Modifier.weight(0.1f))
        Text(text = "IN CASE OF NON-MATCH:", color = Color(0xFF313131))
        Text(text = "OUTSIDE OF SHARED REALITY", color = Color(0xFF505050))
        Button(
            onClick = { onHelp() },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = Color(0x22505050),
            ),
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_help_outline_24),
                contentDescription = "Help",
            )
        }
        Text(text = dreamNum)
    }
}

@Composable
fun DreamRemoteHelp(modifier: Modifier = Modifier, onReturn: () -> Unit = {}) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val haptics = LocalHapticFeedback.current
    val view = LocalView.current
    Column(
        modifier = modifier.fillMaxSize()
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            view.playSoundEffect(SoundEffectConstants.CLICK)
            haptics.performHapticFeedback(HapticFeedbackType.LongPress) },
            interactionSource = interactionSource,
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isPressed) Color(0xEBFFC7CA) else Color(0xFFDC143C),
            )) { Text("        \n       \n       \n") }
        Text(text = "THE PRINCIPLE OF \nCORRESPONDENCE",
            color = Color(0xFFDC143C),
            fontSize = 30.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth())
        Text(text = "WITHIN THE SHARED REALITY OF WAKING LIFE " +
                "THE NUMBERS ABOVE AND BELOW THE BUTTON " +
                "WILL ALWAYS MIRROR ONE ANOTHER ", color = Color(0xFF505050),textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth())
        Text(text = "IF AN OBSERVER NOTES A DISCREPANCY " +
                "THEY MUST NOT BE CONSIDERED A CURRENT " +
                "INHABITANT OF THE SHARED REALITY", color = Color(0xFF313131),textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth())
        Text(text = "WHETHER WITHIN THE REALMS OF DREAMS " +
                "OR DELUSIONS THE OBSERVER MUST " +
                "RECONCILE THE TRUTH FOR THEMSELVES", color = Color(0xFF505050),textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth())
        Text(text = "THE OBSERVER MUST BECOME ROUTINELY FAMILIAR " +
                "WITH THE BUTTON WITHIN SHARED REALITY " +
                "IN ORDER TO BRING IT WITH THEM ACROSS THE BOUNDARIES BETWEEN ", color = Color(0xFF313131),textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth())
        Button(onClick = {
            view.playSoundEffect(SoundEffectConstants.CLICK)
            haptics.performHapticFeedback(HapticFeedbackType.LongPress)
            onReturn()
        },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF808080),
                contentColor = Color(0xEB252525)
            )) { Text("RETURN") }
        Text(text = " ", color = Color(0xFF505050))
    }
}

@Composable
fun DreamRemotePleh(modifier: Modifier = Modifier, onReturn: () -> Unit = {}) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val haptics = LocalHapticFeedback.current
    val view = LocalView.current
    Column(
        modifier = modifier.fillMaxSize()
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            view.playSoundEffect(SoundEffectConstants.CLICK)
            haptics.performHapticFeedback(HapticFeedbackType.LongPress) },
            interactionSource = interactionSource,
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isPressed) Color(0xEBFFC7CA) else Color(0xFFDC143C),
            )) { Text("        \n       \n       \n") }
        Text(text = "THE WINDOW TO REQUIRED KNOWLEDGE",
            color = Color(0xFFDC143C),
            fontSize = 30.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth())
        Text(text = "WINDOWS ALLOW INFORMATION TO PASS THROUGH BOUNDARIES, \n ALLOW " +
                "THIS WINDOW TO SHOW YOU THE SIGHTS AND SOUNDS,\n SYMBOLS AND WORDS, THAT YOU SEEK IN THIS MOMENT ",
            color = Color(0xFF505050),
            textAlign = TextAlign.Center,
            fontSize = 10.sp,
            lineHeight = 10.sp,
            modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.weight(0.1f))
        Button(onClick = {
            view.playSoundEffect(SoundEffectConstants.CLICK)
            haptics.performHapticFeedback(HapticFeedbackType.LongPress)
            onReturn()
        },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF808080),
                contentColor = Color(0xEB252525)
            )) { Text("RETURN") }
        Text(text = " ", color = Color(0xFF505050))
    }
}

fun generateDreamTestNum(): String {
    val random24DigitString = (1..24)
        .map { (0..9).random() }
        .joinToString("")
    return random24DigitString
}