/*
 * Petals APP
 * Copyright (C) 2021 Leonardo Colman Lopes
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package br.com.colman.petals.withdrawal.discomfort.view

import android.content.Context
import android.graphics.Color
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.viewinterop.AndroidView
import br.com.colman.petals.clock.QuitTimer
import br.com.colman.petals.withdrawal.discomfort.repository.DiscomfortRepository
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import com.jjoe64.graphview.series.PointsGraphSeries
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import org.joda.time.LocalDateTime.now
import org.joda.time.Seconds.secondsBetween


@Suppress("FunctionName")
class DiscomfortView(
  private val quitTimer: QuitTimer,
  private val repository: DiscomfortRepository,
) {

  @Composable
  fun Content() {
    val quitDate by quitTimer.quitDate.filterNotNull().collectAsState(now())
    val currentPercentage by repository.discomfort.map { it.discomfortStrength }.collectAsState(8.0)
    val quitDays = secondsBetween(quitDate, now()).seconds.toDouble().div(86400)

    AndroidView({ createGraph(it, currentPercentage, quitDays) }, update = {
      it.title = "Current  Withdrawal Discomfort: " + String.format("%.2f", currentPercentage)
      it.removeAllSeries()
      it.addSeries(discomfortSeries())
      it.addSeries(currentDiscomfortPoint(currentPercentage, quitDays))
      it.invalidate()
    })
  }

  private fun createGraph(context: Context, currentPercentage: Double, quitDay: Double) = GraphView(context).apply {
    addSeries(discomfortSeries())
    addSeries(currentDiscomfortPoint(currentPercentage, quitDay))

    viewport.apply {
      isYAxisBoundsManual = true
      setMaxY(10.0)
      setMinY(0.0)
      isXAxisBoundsManual = true
      setMaxX(27.0)
      setMinX(0.0)
    }

    gridLabelRenderer.apply {
      verticalAxisTitle = "Discomfort Strength"
      horizontalAxisTitle = "Days"
    }
  }

  private fun discomfortSeries(): LineGraphSeries<DataPoint> {
    val dataPoints = repository.discomfortDays.map {
      DataPoint(it.key.days.toDouble(), it.value)
    }

    return LineGraphSeries(dataPoints.toTypedArray()).apply {
      isDrawDataPoints = true
      dataPointsRadius = 8f
    }
  }


  private fun currentDiscomfortPoint(percentage: Double, day: Double) =
    PointsGraphSeries(arrayOf(DataPoint(day, percentage))).apply {
      size = 20f
      color = Color.parseColor("#059033")
      shape =  PointsGraphSeries.Shape.POINT
    }
}