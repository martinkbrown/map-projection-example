package co.martinbrown.example.toppoint;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

public class MapProjectionExample extends MapActivity {

    MapView map;
    MapController mc;
    LocationManager lm;
    GeoPoint geoPoint;
    Drawable marker;

    class MyOverlay extends ItemizedOverlay<OverlayItem> {

        ArrayList<OverlayItem> items = new ArrayList<OverlayItem>();

        public MyOverlay(Drawable drawable) {
            super(drawable);

            boundCenterBottom(drawable);

            items.add(new OverlayItem(geoPoint, "Hello", "Welcome to the Mobile Lab!"));
            //items.add(me);

            populate();
        }

        @Override
        protected OverlayItem createItem(int index) {

            return items.get(index);
        }

        @Override
        protected boolean onTap(int i) {

            OverlayItem item=getItem(i);
            GeoPoint geo=item.getPoint();

            Point pt = map.getProjection().toPixels(geo, null);

            String message = String.format("Lat: %f | Lon: %f\nX: %d | Y %d",
                    geo.getLatitudeE6()/1000000.0,
                    geo.getLongitudeE6()/1000000.0,
                    pt.x, pt.y);

            Toast.makeText(MapProjectionExample.this,
                    message,
                    Toast.LENGTH_LONG).show();

            return(true);
        }

        @Override
        public int size() {
            return items.size();
        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        map = (MapView) findViewById(R.id.mapView);
        mc = map.getController();

        map.setBuiltInZoomControls(true);

        geoPoint = new GeoPoint((int) (30.446142 * 1E6), (int) (-84.299673 * 1E6));
        mc.setCenter(geoPoint);

        marker = getResources().getDrawable(R.drawable.ic_launcher);
        marker.setBounds(0, 0, marker.getIntrinsicWidth(), marker.getIntrinsicHeight());

        mc.setZoom(17);

        map.getOverlays().add(new MyOverlay(marker));


    }

    @Override
    protected boolean isRouteDisplayed() {
        return false;
    }
}