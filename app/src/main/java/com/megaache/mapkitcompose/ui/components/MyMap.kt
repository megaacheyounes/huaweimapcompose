package com.megaache.mapkitcompose.ui.components

import android.os.Bundle
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleObserver
import com.huawei.hms.maps.HuaweiMap
import com.huawei.hms.maps.MapView
import com.huawei.hms.maps.model.LatLng
import java.lang.IllegalStateException


@Composable
fun MyMap(
    initialLocation: LatLng = LatLng(0.0, 0.0),
    modifier: Modifier = Modifier,
    onMapReady: (HuaweiMap: HuaweiMap) -> Unit
) {

    val context = LocalContext.current

    val mapView = remember {
        MapView(context)
    }

    val lifecycle = LocalLifecycleOwner.current.lifecycle
    lifecycle.addObserver(rememberMapLifecycle(mapView))

    AndroidView(
        factory = {
            mapView.apply {
                mapView.getMapAsync(onMapReady)
            }
        },
        modifier = modifier
    )

}

@Composable
fun rememberMapLifecycle(
    mapView: MapView
): LifecycleObserver {
    return remember {
        LifecycleEventObserver { source, event ->

            mapView.apply {
                when (event) {
                    Lifecycle.Event.ON_CREATE -> onCreate(Bundle())
                    Lifecycle.Event.ON_START -> onStart()
                    Lifecycle.Event.ON_RESUME -> onResume()
                    Lifecycle.Event.ON_PAUSE -> onPause()
                    Lifecycle.Event.ON_STOP -> onStop()
                    Lifecycle.Event.ON_DESTROY -> onDestroy()
                    Lifecycle.Event.ON_ANY -> throw IllegalStateException()
                }
            }
        }
    }

}