package duribon.dlug.org.duribonduribon.Activities;

import android.os.Build;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Window;

import com.facebook.stetho.Stetho;

import java.util.Calendar;

import butterknife.ButterKnife;
import butterknife.InjectView;
import duribon.dlug.org.duribonduribon.R;
import duribon.dlug.org.duribonduribon.Fragments.MapFragment;
import duribon.dlug.org.duribonduribon.Fragments.TabsFragment;
import duribon.dlug.org.duribonduribon.Fragments.TimetableFragment;

/**
 * Created by neonkid on 11/5/16.
 *
 * Application의 메인 Activity..
 */

public class MainActivity extends AppCompatActivity implements TimetableFragment.CustomSearchPOI {
    @InjectView(R.id.main_tool_bar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Stetho.initializeWithDefaults(this);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        /*
            Device 해상도와, Device의 현재 날짜를 구하고,
            Marshmallow OS 와 Lollipop OS 별 상단바 색깔
            변경 코드
         */
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

    // Toolbar Title 변경..
    public void setActionBarTitle(String title) {
        toolbar.setTitle(title);
    }

    // 계절별로 색깔을 변경,,
    private int setSeasonColor(int month) {
        if(month > 2 && month < 6) { return R.color.color_spring; }
        else if(month > 5 && month < 9) { return R.color.color_summer; }
        else if(month > 8 && month < 11) { return R.color.color_fall; }
        else { return R.color.color_winter; }
    }

    /*
        TimetableFragment Interface 함수.
        시간표에서 외부 지도를 불러와 경로 탐색.
     */
    @Override
    public void requestSearch(String query) {
        String FRAGMENT_TAG = "LOCATION_TAG";
        MapFragment mapFragment = MapFragment.newInstance();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.main_frame, mapFragment, FRAGMENT_TAG);
        try {
            mapFragment.searchPOI(query);
        } catch(IllegalStateException ex) {
            ex.printStackTrace();
        }
        fragmentTransaction.addToBackStack(null);   // BackStack을 통해 해당 Fragment를 스택에서 빼옴..
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        fragmentTransaction.commit();
    }
}