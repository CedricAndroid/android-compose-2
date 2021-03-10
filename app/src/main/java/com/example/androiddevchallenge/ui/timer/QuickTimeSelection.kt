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

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.R

@Composable
fun QuickTimeSelection(
    modifier: Modifier = Modifier,
    state: TimerState,
    onTimeChange: (Int) -> Unit,
    onStateChange: (TimerState) -> Unit
) {
    val quickSelectionTimes = listTimers
    LazyRow(modifier = modifier) {
        items(quickSelectionTimes) { sec ->
            MinutesText(
                sec,
                Modifier
                    .padding(10.dp)
                    .clip(
                        shape = RoundedCornerShape(
                            CornerSize(12.dp),
                            CornerSize(12.dp),
                            CornerSize(12.dp),
                            CornerSize(12.dp)
                        )
                    )
                    .background(Color.Gray)

                    .clickable {
                        onTimeChange(sec.sec)
                        if (state != TimerState.Running) {
                            onStateChange(TimerState.Running)
                        }
                    }
                    .padding(vertical = 8.dp, horizontal = 12.dp)

            )
        }
    }
}

class TimerItem(val name: String, val sec: Int, val img: Int)

val listTimers = listOf<TimerItem>(
    TimerItem("I", 180, R.drawable.egg3),
    TimerItem("II", 240, R.drawable.egg4),
    TimerItem("III", 360, R.drawable.egg6),
    TimerItem("IV", 600, R.drawable.egg10),
    TimerItem("V", 900, R.drawable.egg15)
)

@Composable
fun MinutesText(timer: TimerItem, modifier: Modifier = Modifier) {
    val period = Period(60)
    val minutes = period.getValue(timer.sec / 60)
    val seconds = period.getValue(timer.sec % 60)

    Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            Image(
                painter = painterResource(timer.img),
                contentDescription = "Content description for visually impaired",
                modifier = Modifier.width(150.dp).height(150.dp),
                contentScale = ContentScale.FillWidth
            )

            Text("$minutes:$seconds", style = MaterialTheme.typography.h4)
        }
    }
}
