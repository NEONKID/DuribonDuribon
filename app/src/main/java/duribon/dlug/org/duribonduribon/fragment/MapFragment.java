package duribon.dlug.org.duribonduribon.fragment;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import net.daum.mf.map.api.MapReverseGeoCoder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
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
    private GoogleMap mMap;
    private SearchView mSearchView;
    private MenuItem searchmenuItem;
    private Map<String, String> map = new HashMap<String, String>();
    private String ChAddress;

    View view;

    /*
        단국대학교 대운동장
     */
    static final LatLng DKU = new LatLng(36.836609, 127.168095);

    @InjectView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;

    public static MapFragment newInstance() {
        MapFragment fragment = new MapFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
            대체할 문자열을 영어와 한국어 등으로
            그룹화 해야할 듯 함,,
         */
        map.put("산학협력관", getString(R.string.Dental_College));
        map.put("융합기술대학", getString(R.string.Second_Science));
        map.put("보건과학대학", getString(R.string.Graduate_School));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        try {
            view = inflater.inflate(R.layout.fragment_map, container, false);
            ButterKnife.inject(this, view);

            mMap = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map)).getMap();
            mMap.addMarker(new MarkerOptions().position(DKU).title("기본 위치")).showInfoWindow();

            // Factory: 클래스를 만들어서 돌려줌,,
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(DKU, 10));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);

            setHasOptionsMenu(true);
        } catch (InflateException ex) {

        }
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.search, menu);
        searchmenuItem = menu.findItem(R.id.map_search);
        mSearchView = (SearchView) searchmenuItem.getActionView();
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            /*
                결과를 도출하기 위해 GeoCoding을 사용,,
             */
            @Override
            public boolean onQueryTextSubmit(String query) {
                LatLng dst = findGoLocation(query);
                try {
                    mMap.addMarker(new MarkerOptions().position(dst).title(ChAddress)).showInfoWindow();
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(dst, 10));
                    mMap.animateCamera(CameraUpdateFactory.zoomTo(17), 2000, null);
                    mMap.addPolyline(new PolylineOptions().add(DKU, dst).width(5).color(Color.RED));
                } catch(Exception ex) {
                    Snackbar.make(coordinatorLayout, "검색어가 올바르지 않습니다.", Snackbar.LENGTH_LONG)
                            .setAction("OK", null).show();
                    ex.printStackTrace();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }

        });
        inflater.inflate(R.menu.location, menu);
    }

    /*
        지명 정보 오기 매핑,, (치과대학 제외)
        아직 완벽하지 않습니다. 여러분들의 수정을 기다립니다.
     */
    private String Searchcheck(String query) {
        String result = getString(R.string.Dankook_University);

        if(query.charAt(0) == getString(R.string.Dankook_University).charAt(0)) {
            result = "";
        }

        if(map.containsKey(query)) {
            ChAddress = query;
            result += map.get(query);
            Snackbar.make(coordinatorLayout, "현재 바뀐 위치를 표시합니다.", Snackbar.LENGTH_LONG)
                    .setAction("OK", null).show();
        } else {
            ChAddress = query;
            result += query;
        }

        return result;
    }

    private LatLng findGoLocation(String address) {
        Geocoder geocoder = new Geocoder(getActivity().getApplicationContext());
        Address addr;
        LatLng latLng = null;
        address = Searchcheck(address);
        try {
            List<Address> listAddress = geocoder.getFromLocationName(address, 10);
            if(listAddress.size() > 0) {
                addr = listAddress.get(0);
                double lat = addr.getLatitude();
                double lng = addr.getLongitude();
                latLng = new LatLng(lat, lng);
                Log.i("[COMPUTE]", "Result: "+ lat + lng);
            }
        } catch(IOException ex) {
            ex.printStackTrace();
        }
        return latLng;
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
}
