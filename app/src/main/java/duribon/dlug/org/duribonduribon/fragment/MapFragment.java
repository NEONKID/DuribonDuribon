package duribon.dlug.org.duribonduribon.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.skp.Tmap.TMapData;
import com.skp.Tmap.TMapMarkerItem;
import com.skp.Tmap.TMapPOIItem;
import com.skp.Tmap.TMapPoint;
import com.skp.Tmap.TMapPolyLine;
import com.skp.Tmap.TMapView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import duribon.dlug.org.duribonduribon.InteriorMapActivity;
import duribon.dlug.org.duribonduribon.R;

/**
 * Created by neonkid on 10/31/16.
 */

public class MapFragment extends Fragment implements View.OnClickListener {
    private TMapView tMapView;
    private SearchView mSearchView;
    private MenuItem searchmenuItem;
    private Map<String, String> map = new HashMap<>();
    private TMapPoint src, dst;
    private LocationManager mLocationManager;
    private String mProvider = LocationManager.NETWORK_PROVIDER;

    View view;

    @InjectView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;

    @Override
    public void onStart() {
        super.onStart();
        if(ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location location = mLocationManager.getLastKnownLocation(mProvider);
        if(location != null) {
            mListener.onLocationChanged(location);
        }
        mLocationManager.requestSingleUpdate(mProvider, mListener, null);
        map.put("융합기술대학", getString(R.string.Second_Science));
        map.put("보건과학대학", getString(R.string.Graduate_School));
    }

    @Override
    public void onStop() {
        super.onStop();
        if(ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mLocationManager.removeUpdates(mListener);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLocationManager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        try {
            view = inflater.inflate(R.layout.fragment_map, container, false);
            ButterKnife.inject(this, view);

            tMapView = (TMapView)view.findViewById(R.id.tMapView);
            tMapView.setOnApiKeyListener(new TMapView.OnApiKeyListenerCallback() {
                @Override
                public void SKPMapApikeySucceed() {
                    tMapView.setMapType(TMapView.MAPTYPE_STANDARD);
                    tMapView.setTrafficInfo(true);

                    tMapView.setSightVisible(true);
                    tMapView.setTMapLogoPosition(TMapView.TMapLogoPositon.POSITION_BOTTOMLEFT);
                    tMapView.setIconVisibility(true);
                }

                @Override
                public void SKPMapApikeyFailed(String s) {
                    // API를 불러오지 못했을 경우,,
                }
            });
            tMapView.setSKPMapApiKey(getString(R.string.sk_maps_key));

            // tMapView.setCenterPoint(127.168095, 36.836609);
            // tMapView.setLocationPoint(127.168095, 36.836609);
            tMapView.setZoomLevel(17);
            // src = new TMapPoint(36.836609, 127.168095);

        } catch (InflateException ex) {

        }
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.search, menu);
        searchmenuItem = menu.findItem(R.id.map_search);
        mSearchView = (SearchView) searchmenuItem.getActionView();
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                searchPOI(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) { return false; }

        });
        inflater.inflate(R.menu.location, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.location_search:
                Snackbar.make(coordinatorLayout, "현재 준비 중입니다.", Snackbar.LENGTH_LONG).setAction("OK", null).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /*
       지명 정보 오기 매핑,,
       아직 완벽하지 않습니다. 여러분들의 수정을 기다립니다.
     */
    private String Searchcheck(String query) {
        String result = getString(R.string.Dankook_University);

        if(query.charAt(0) == getString(R.string.Dankook_University).charAt(0)) {
            result = "";
        }

        if(map.containsKey(query)) {
            result += map.get(query);
            /*Snackbar.make(coordinatorLayout, "현재 바뀐 위치를 표시합니다.", Snackbar.LENGTH_LONG)
                    .setAction("OK", null).show(); */
        } else {
            result += query;
        }

        return result;
    }

    private void moveMap(double lat, double lng) {
        tMapView.setCenterPoint(lng, lat);
    }

    private void setMyLocation(double lat, double lng) {
        tMapView.setLocationPoint(lng, lat);
    }

    private void searchPOI(String query) {
        query = Searchcheck(query);
        TMapData data = new TMapData();
        if(!TextUtils.isEmpty(query)) {
            data.findAllPOI(query, new TMapData.FindAllPOIListenerCallback() {
                @Override
                public void onFindAllPOI(final ArrayList<TMapPOIItem> arrayList) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tMapView.removeAllMarkerItem();
                            for (TMapPOIItem poi : arrayList) {
                                addMarker(poi);
                            }
                            if (arrayList.size() > 0) {
                                TMapPOIItem poi = arrayList.get(0);
                                moveMap(poi.getPOIPoint().getLatitude(), poi.getPOIPoint().getLongitude());
                                dst = new TMapPoint(poi.getPOIPoint().getLatitude(), poi.getPOIPoint().getLongitude());
                                searchRoute(src, dst);
                            }
                        }
                    });
                }
            });
        }
    }

    private void searchRoute(TMapPoint start, TMapPoint end) {
        TMapData data = new TMapData();
        if(start == null && end == null) {
            return;
        }
        data.findPathDataWithType(TMapData.TMapPathType.CAR_PATH, start, end, new TMapData.FindPathDataListenerCallback() {
            @Override
            public void onFindPathData(final TMapPolyLine path) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        path.setLineWidth(5);
                        path.setLineColor(Color.RED);
                        tMapView.addTMapPath(path);
                        Bitmap startImage = ((BitmapDrawable)ContextCompat.getDrawable(getActivity(), R.drawable.start_blue)).getBitmap();
                        Bitmap endImage = ((BitmapDrawable)ContextCompat.getDrawable(getActivity(), R.drawable.end_green)).getBitmap();
                        tMapView.setTMapPathIcon(startImage, endImage);
                        Snackbar.make(coordinatorLayout, "거리: " + Math.round(path.getDistance()) + "m", Snackbar.LENGTH_LONG).setAction("OK", null).show();
                        dst = null;
                    }
                });
            }
        });
    }

    private void addMarker(TMapPOIItem poi) {
        TMapMarkerItem item = new TMapMarkerItem();
        item.setTMapPoint(poi.getPOIPoint());

        Bitmap icon = ((BitmapDrawable) ContextCompat.getDrawable(getActivity(), R.drawable.pushpin)).getBitmap();
        item.setIcon(icon);
        item.setPosition(0.5f, 1);
        item.setCalloutTitle(poi.getPOIName());
        item.setCalloutSubTitle(poi.getPOIContent());
        item.setName(poi.getPOIName());
        item.setCanShowCallout(true);

        tMapView.addMarkerItem(poi.getPOIID(), item);
    }

    @OnClick(R.id.location_me)
    public void locationPin() {
        Snackbar.make(coordinatorLayout, "목적지에 정확히 도착했습니까? ", Snackbar.LENGTH_LONG)
                .setAction("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(), InteriorMapActivity.class);
                        startActivity(intent);
                    }
                })
                .setActionTextColor(getResources().getColor(R.color.color_primary))
                .show();
    }

    @Override
    public void onClick(View v) {}

    LocationListener mListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            moveMap(location.getLatitude(), location.getLongitude());
            setMyLocation(location.getLatitude(), location.getLongitude());
            src = new TMapPoint(location.getLatitude(), location.getLongitude());
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {}

        @Override
        public void onProviderEnabled(String s) {}

        @Override
        public void onProviderDisabled(String s) {}
    };
}
