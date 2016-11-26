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
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
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
    private Map<String, String> map = new HashMap<>();
    private TMapPoint src, dst;
    private LocationManager mLocationManager;

    View view;

    @InjectView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;

    @InjectView(R.id.location_me)
    FloatingActionButton location_me;

    private LocationListener mListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            if(location.getLatitude() < 36.832311  && location.getLongitude() < 127.165038) {
                moveMap(36.836609, 127.168095);
                setMyLocation(36.836609, 127.168095);
            } else {
                moveMap(location.getLatitude(), location.getLongitude());
                setMyLocation(location.getLatitude(), location.getLongitude());
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle bundle) { Log.d("[Location Service]", "onStatusChanged, provider:" + provider + ", status:" + status + ", Bundle:" + bundle); }

        @Override
        public void onProviderEnabled(String provider) { Log.d("[Location Service]", "onProviderEnabled, provider:" + provider); }

        @Override
        public void onProviderDisabled(String provider) { Log.d("[Location Service]", "onProviderDisabled, provider:" + provider); }
    };

    public MapFragment() {}

    @Override
    public void onStart() {
        super.onStart();
        onLocationManager();
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
        setMapData();
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

                    moveMap(36.836609, 127.168095);
                    setMyLocation(36.836609, 127.168095);
                }

                @Override
                public void SKPMapApikeyFailed(String s) {
                    // API를 불러오지 못했을 경우,,
                }
            });
            tMapView.setSKPMapApiKey(getString(R.string.sk_maps_key));
            tMapView.setZoomLevel(17);

        } catch (InflateException ex) {

        }
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        final SearchView mSearchView;
        final MenuItem searchmenuItem;
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
                onLocationManager();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private String Searchcheck(String query) {
        String result = getString(R.string.Dankook_University);

        if(query.startsWith(getString(R.string.Dankook_University).substring(0,1))) {
            result = "";
        }

        if(map.containsKey(query)) {
            result += map.get(query);
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
        src = new TMapPoint(lat, lng);
    }

    public void searchPOI(String query) {
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
                            if(arrayList.size() > 0) {
                                TMapPOIItem poi = arrayList.get(0);
                                moveMap(poi.getPOIPoint().getLatitude(), poi.getPOIPoint().getLongitude());
                                if(poi.getPOIPoint().getLatitude() > 36.832311 && poi.getPOIPoint().getLongitude() < 127.165038) {
                                    Snackbar.make(coordinatorLayout, "해당 위치는 천안 캠퍼스가 아닙니다.", Snackbar.LENGTH_LONG).show();
                                    if(location_me.getVisibility() != View.INVISIBLE) {
                                        location_me.hide();
                                    }
                                    return;
                                }
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
                        Snackbar.make(coordinatorLayout, "거리: " + Math.round(path.getDistance()) + "m", Snackbar.LENGTH_LONG).show();
                        dst = null;
                        if(location_me.getVisibility() == View.INVISIBLE || location_me.getVisibility() == View.GONE) {
                            location_me.show();
                        }
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

    /*
       지명 정보 중복 매핑,,
       사용자가 검색할만한 단어를 중심으로 데이터화,,
     */
    private void setMapData() {
        map.put("융합기술대학", getString(R.string.Second_Science));
        map.put("공학관", getString(R.string.Second_Science));
        map.put("보건대학", getString(R.string.Graduate_School));
        map.put("보건간호관", getString(R.string.Graduate_School));
        map.put("간호대학", getString(R.string.Graduate_School));
        map.put("생자대", getString(R.string.Life_Resource));
        map.put("외국어대학", getString(R.string.Humanities_college));
        map.put("외대", getString(R.string.Humanities_college));
        map.put("인문대", getString(R.string.Humanities_college));
        map.put("법대", getString(R.string.law_college));
        map.put("사회과학관", getString(R.string.law_college));
        map.put("도서관", getString(R.string.Library));
        map.put("약대", getString(R.string.Medical_college));
        map.put("약학대학", getString(R.string.Medical_college));
        map.put("학생회관", getString(R.string.Post_Office));
        map.put("우체국", getString(R.string.Post_Office));
        map.put("스포츠과학대학", getString(R.string.sports_college));
    }

    @Override
    public void onClick(View v) {}

    private void onLocationManager() {
        final String mProvider = LocationManager.NETWORK_PROVIDER;
        final String gProvider = LocationManager.GPS_PROVIDER;
        if(ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location mLocation = mLocationManager.getLastKnownLocation(mProvider);
        Location gLocation = mLocationManager.getLastKnownLocation(gProvider);
        if(mLocation != null) {
            mListener.onLocationChanged(mLocation);
            mLocationManager.requestLocationUpdates(gProvider, 100, 1, mListener);
            if(gLocation != null) {
                mListener.onLocationChanged(gLocation);
                mLocationManager.requestLocationUpdates(mProvider, 100, 1, mListener);
            }
        } else {
            Snackbar.make(coordinatorLayout, "현재 위치를 확인할 수 없습니다.", Snackbar.LENGTH_LONG).show();
        }
    }
}
