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

import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ProgressBackground(state: TimerState, progress: Int) {

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {

        val transitionData = updateProgressTransitionData(
            state = state,
            progress = progress,
            maxHeight = maxHeight,
            maxWidth = maxWidth
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = transitionData.padding),
            contentAlignment = Alignment.BottomCenter
        ) {

            Column(
                modifier = Modifier
                    .width(transitionData.width)
                    .height(transitionData.height)
                    .background(
                        color = MaterialTheme.colors.primary,
                        shape = RoundedCornerShape(
                            CornerSize(transitionData.cornerRadius),
                            CornerSize(transitionData.cornerRadius),
                            CornerSize(transitionData.cornerRadius),
                            CornerSize(transitionData.cornerRadius)
                        )
                    ),
            ) {}
        }
    }
}

// Holds the animation values.
private class ProgressTransitionData(
    padding: State<Dp>,
    height: State<Dp>,
    width: State<Dp>,
    cornerRadius: State<Dp>
) {
    val padding by padding
    val height by height
    val width by width
    val cornerRadius by cornerRadius
}

@Composable
private fun updateProgressTransitionData(
    state: TimerState,
    progress: Int,
    maxHeight: Dp,
    maxWidth: Dp
): ProgressTransitionData {
    val transition = updateTransition(state)
    val padding = transition.animateDp {
        0.dp
    }
    val height = transition.animateDp(
        transitionSpec = {

            if (TimerState.Running isTransitioningTo TimerState.Stop) {
                keyframes {
                    durationMillis = 100
                    248.dp at 0
                }
            } else {
                keyframes {
                    durationMillis = 150
                }
            }
        }
    ) { timerState ->
        when (timerState) {
            TimerState.Stop -> {
                0.dp
            }
            else -> {
                maxHeight * progress / 100
            }
        }
    }

    val width = transition.animateDp(
        transitionSpec = {
            if (TimerState.Running isTransitioningTo TimerState.Stop) {
                keyframes {
                    durationMillis = 100
                    248.dp at 0
                }
            } else {
                keyframes {
                    durationMillis = 150
                }
            }
        }
    ) { timerState ->
        if (timerState == TimerState.Stop) {
            0.dp
        } else {
            maxHeight // to keep ripple circular form
        }
    }

    val cornerRadius = transition.animateDp(
        transitionSpec = {
            when {
                TimerState.Running isTransitioningTo TimerState.Stop -> {
                    keyframes {
                        durationMillis = 100
                        124.dp at 0
                    }
                }
                TimerState.Stop isTransitioningTo TimerState.Running -> {
                    keyframes {
                        durationMillis = 150
                        maxWidth / 2 at 20
                    }
                }
                else -> {
                    keyframes {
                        durationMillis = 0
                        0.dp at 0
                    }
                }
            }
        }
    ) { timerState ->
        0.dp
    }

    return remember(transition) {
        ProgressTransitionData(
            padding = padding,
            height = height,
            width = width,
            cornerRadius = cornerRadius
        )
    }
}

@Preview(widthDp = 360, heightDp = 480)
@Composable
fun ProgressBackgroundPreview() {
    ProgressBackground(state = TimerState.Stop, progress = 0)
}
