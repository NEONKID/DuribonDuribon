package duribon.dlug.org.duribonduribon.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import duribon.dlug.org.duribonduribon.R;

/**
 * Created by neonkid on 10/31/16.
 */

public class MapFragment extends Fragment {
    private GoogleMap mMap;
    View view;

    static final LatLng DKU = new LatLng(36.836609, 127.168095);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        try {
            view = inflater.inflate(R.layout.activity_map, container, false);

            /* Bundle data = getArguments();
            String schoolname = data.getString("Dankook University"); */

            mMap = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map)).getMap();

            mMap.addMarker(new MarkerOptions().position(DKU).title("단국대학교 대운동장"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(DKU, 15));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(17), 2000, null);

        } catch(InflateException ex) {

        }
        return view;
    }
}
