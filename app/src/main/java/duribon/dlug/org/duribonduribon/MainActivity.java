package duribon.dlug.org.duribonduribon;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.facebook.stetho.Stetho;

import java.util.Calendar;

import butterknife.ButterKnife;
import butterknife.InjectView;
import duribon.dlug.org.duribonduribon.fragment.TabsFragment;

public class MainActivity extends AppCompatActivity {
    @InjectView(R.id.main_tool_bar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Stetho.initializeWithDefaults(this);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        Calendar cal = Calendar.getInstance();
        final int month = cal.get(cal.MONTH) + 1;
        toolbar.setBackgroundResource(setActionBarColor(month));
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

    /*
        매 계절별로 Toolbar 색상 변경..
        색깔 의견 받습니다..

        --> 색깔 변경은 values/colors.xml 에서,,
    */
    private int setActionBarColor(int month) {
        if(month > 2 && month < 6) { return R.color.color_spring; }
        else if(month > 5 && month < 9) { return R.color.color_summer; }
        else if(month > 8 && month < 11) { return R.color.color_fall; }
        else { return R.color.color_winter; }
    }
}