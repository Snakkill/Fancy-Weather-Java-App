package com.example.locationexample;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.android.gms.maps.model.TileProvider;
import com.google.android.gms.maps.model.UrlTileProvider;

import java.net.MalformedURLException;
import java.net.URL;

public class overlay {
    public void onMapReady(GoogleMap googleMap) {
        TileProvider tileProvider = new UrlTileProvider(256, 256) {        @Override
        public URL getTileUrl(int x, int y, int zoom) {            /* Define the URL pattern for the tile images */
            String s = String.format("https://tile.openweathermap.org/map/temp_new/%d/%d/%d.png?appid=Your-API-Key (Links to an external site.)", zoom, x, y);
            if (!checkTileExists(x, y, zoom)) {
                return null;
            }            try {
                return new URL(s);
            } catch (MalformedURLException e) {
                throw new AssertionError(e);
            }
        }        /*
         * Check that the tile server supports the requested x, y and zoom.
         * Complete this stub according to the tile range you support.
         * If you support a limited range of tiles at different zoom levels, then you
         * need to define the supported x, y range at each zoom level.
         */
            private boolean checkTileExists(int x, int y, int zoom) {
                int minZoom = 12;
                int maxZoom = 16;            return (zoom >= minZoom && zoom <= maxZoom);
            }
        };
       // WeatherData w = MainActivity.getWeatherInstance();
        //Double lat = Double.parseDouble(w.getLat());
        //Double lon = Double.parseDouble(w.getLon());
        LatLng loc = new LatLng(-122,37);
        googleMap.addMarker(new MarkerOptions().position(loc).title("You are here!"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
        googleMap.moveCamera(CameraUpdateFactory.zoomTo(12));
        TileOverlay tileOverlay = googleMap.addTileOverlay(new TileOverlayOptions()
                .tileProvider(tileProvider));
    }
}
