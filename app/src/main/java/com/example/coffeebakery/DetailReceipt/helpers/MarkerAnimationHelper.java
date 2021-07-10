package com.example.coffeebakery.DetailReceipt.helpers;


import android.os.Handler;
import android.os.SystemClock;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import com.example.coffeebakery.DetailReceipt.interfaces.LatLngInterpolator;

public class MarkerAnimationHelper {

    public static void animateMarkerToGB(final Marker marker, final LatLng finalPosition, final LatLngInterpolator latLngInterpolator) {
        final LatLng startPosition = marker.getPosition();
        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        final Interpolator interpolator = new AccelerateDecelerateInterpolator();
        final float durationInMs = 2000;

        handler.post(new Runnable() {
            long elapsed;
            float t;
            float v;

            @Override
            public void run() {
                //Tính  tiến trình bằng interpolator(nội suy)
                elapsed = SystemClock.uptimeMillis() - start;
                t = elapsed / durationInMs;
                v = interpolator.getInterpolation(t);

                marker.setPosition(latLngInterpolator.interpolate(v, startPosition, finalPosition));

                // Lặp lại cho đến khi quá trình hoàn tất.
                if (t < 1) {
                    // Đăng lại sau 16ms
                    handler.postDelayed(this, 16);
                }
            }
        });
    }
}
