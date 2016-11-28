package duribon.dlug.org.duribonduribon;

import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Window;

import com.facebook.stetho.Stetho;

import java.util.Calendar;

import butterknife.ButterKnife;
import butterknife.InjectView;
import duribon.dlug.org.duribonduribon.fragment.CurrentFragment;
import duribon.dlug.org.duribonduribon.fragment.MapFragment;
import duribon.dlug.org.duribonduribon.fragment.TabsFragment;
import duribon.dlug.org.duribonduribon.fragment.TimetableFragment;

public class MainActivity extends AppCompatActivity implements TimetableFragment.CustomSearchPOI {
    @InjectView(R.id.main_tool_bar)
    Toolbar toolbar;
    private MapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Stetho.initializeWithDefaults(this);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        Window window = this.getWindow();
        Calendar cal = Calendar.getInstance();
        final int month = cal.get(cal.MONTH) + 1;
        toolbar.setBackgroundResource(setSeasonColor(month));

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.setStatusBarColor(this.getColor(setSeasonColor(month)));
        } else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP_MR1) {
            window.setStatusBarColor(this.getResources().getColor(setSeasonColor(month)));
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.main_frame, TabsFragment.newInstance()).commit();
        setSupportActionBar(toolbar);
    }

    public void setActionBarTitle(String title) {
        toolbar.setTitle(title);
    }

    private int setSeasonColor(int month) {
        if(month > 2 && month < 6) { return R.color.color_spring; }
        else if(month > 5 && month < 9) { return R.color.color_summer; }
        else if(month > 8 && month < 11) { return R.color.color_fall; }
        else { return R.color.color_winter; }
    }

    @Override
    public void requestSearch(String query) {
        String FRAGMENT_TAG = "LOCATION_TAG";
        mapFragment = MapFragment.newInstance();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.main_frame, mapFragment, FRAGMENT_TAG);
        try {
            mapFragment.searchPOI(query);
        } catch(IllegalStateException ex) {
            ex.printStackTrace();
        }
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        fragmentTransaction.commit();
    }
}