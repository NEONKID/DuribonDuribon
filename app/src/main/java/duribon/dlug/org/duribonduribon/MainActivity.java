package duribon.dlug.org.duribonduribon;

import android.os.Build;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Window;

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
}