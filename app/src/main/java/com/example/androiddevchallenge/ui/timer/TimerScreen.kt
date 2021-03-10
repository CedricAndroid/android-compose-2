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
package com.example.androiddevchallenge.ui.timer

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun TimerScreen(
    time: Int,
    onTimeChange: (Int) -> Unit,
    state: TimerState,
    onStateChange: (TimerState) -> Unit,
    progress: Int
) {

    ProgressBackground(state, progress)

    Column {
        TimerControl(
            time = time,
            onTimeChange = onTimeChange,
            isSelectionMode = state == TimerState.Stop,
            modifier = Modifier.weight(2f)
        )
        QuickTimeSelection(onTimeChange = onTimeChange, state = state, onStateChange = onStateChange, modifier = Modifier.weight(1.5f))
    }

    PlayButton(state, onStateChange)
    StopButton(state, onStateChange)
}

enum class TimerState {
    Stop, Running, Paused
}