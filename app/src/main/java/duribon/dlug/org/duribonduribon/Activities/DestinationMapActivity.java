package duribon.dlug.org.duribonduribon.Activities;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import duribon.dlug.org.duribonduribon.R;

/**
 * Created by wnsud on 2016-12-01.
 */

public class DestinationMapActivity extends AppCompatActivity {
    @InjectView(R.id.dest_content)
    CoordinatorLayout coordinatorLayout;    // 내부 지도 레이아웃..

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destinationmap);
        ButterKnife.inject(this);

        /*PhotoViewAttacher attacher;
        ImageView destination = (ImageView)findViewById(R.id.destination);
        attacher = new PhotoViewAttacher(destination);*/
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
    }
}
