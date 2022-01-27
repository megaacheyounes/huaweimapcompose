package com.megaache.mapkitcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.huawei.hms.maps.CameraUpdateFactory
import com.huawei.hms.maps.HuaweiMap
import com.huawei.hms.maps.model.LatLng
import com.huawei.hms.maps.model.MarkerOptions
import com.megaache.mapkitcompose.ui.components.MyMap
import kotlin.random.Random


class MainActivity : ComponentActivity() {

    lateinit var map: HuaweiMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {

                Column(
                    modifier = Modifier
                        .padding(32.dp)
                        .fillMaxHeight()
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Huawei Map in Jetpack Compose",
                        style = MaterialTheme.typography.h6
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    MyMap(
                        modifier = Modifier
                            .height(300.dp)
                            .clip(RoundedCornerShape(16.dp))
                    ) { huaweiMap ->
                        map = huaweiMap
                        huaweiMap.setOnMapLongClickListener { latLng ->
                            addMarker(latLng)
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {
                            changeCameraPosition()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text("change camera position")
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Long click anywhere in the map to insert marker",
                        style = MaterialTheme.typography.subtitle2,
                        modifier = Modifier.alpha(0.5f)
                    )
                }

            }
        }
    }


    /**
     * add marker to the map at location [latlng]
     * @param latLng: location of the marker
     */
    fun addMarker(latLng: LatLng) {
        map.addMarker(MarkerOptions().position(latLng))
    }
//
    /**
     * move the camera around randomly
     */
    fun changeCameraPosition() {
        val range = -500..500

        map.animateCamera(
            CameraUpdateFactory.scrollBy(
                range.random().toFloat(),
                range.random().toFloat()
            )
        )
    }
}