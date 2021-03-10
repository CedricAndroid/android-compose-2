/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.Box
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import com.example.androiddevchallenge.ui.home.LandingScreen
import com.example.androiddevchallenge.ui.theme.MyTheme
import com.example.androiddevchallenge.ui.timer.TimerScreen
import com.example.androiddevchallenge.ui.timer.TimerViewModel

class MainActivity : AppCompatActivity() {
    private val countDownViewModel by viewModels<TimerViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTheme(darkTheme = true) {
                mainScreen(timerViewModel = countDownViewModel)
            }
        }
    }
}

enum class SplashState { Shown, Completed }

@Composable
fun mainScreen(timerViewModel: TimerViewModel) {
    Scaffold {

        val transitionState = remember { MutableTransitionState(SplashState.Shown) }
        val transition = updateTransition(transitionState)
        val splashAlpha by transition.animateFloat(
            transitionSpec = {
                tween(durationMillis = 100)
            }
        ) {
            if (it == SplashState.Shown) 1f else 0f
        }
        val contentAlpha by transition.animateFloat(
            transitionSpec = {
                tween(durationMillis = 300)
            }
        ) {
            if (it == SplashState.Shown) 0f else 1f
        }

        Box {
            LandingScreen(
                modifier = Modifier.alpha(splashAlpha),
                onTimeout = {
                    transitionState.targetState = SplashState.Completed
                }
            )

            Surface(modifier = Modifier.alpha(contentAlpha)) {
                TimerScreen(
                    timerViewModel.time,
                    timerViewModel::onTimeChanged,
                    timerViewModel.state,
                    timerViewModel::onStateChanged,
                    timerViewModel.progress
                )
            }
        }
    }
}
