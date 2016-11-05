package duribon.dlug.org.duribonduribon.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import duribon.dlug.org.duribonduribon.MainActivity;
import duribon.dlug.org.duribonduribon.R;

/**
 * Created by neonkid on 10/31/16.
 */

public class TimetableFragment extends Fragment {
    public static TimetableFragment newInstance() {
        TimetableFragment fragment = new TimetableFragment();
        return fragment;
    }

    public TimetableFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceBundle) {
        View rootview = inflater.inflate(R.layout.fragment_timetable, container, false);
        ButterKnife.inject(this, rootview);

        setHasOptionsMenu(true);
        return rootview;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.timetable, menu);
    }
}
