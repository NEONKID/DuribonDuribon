package duribon.dlug.org.duribonduribon.Activities;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import duribon.dlug.org.duribonduribon.R;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by wnsud on 2016-12-01.
 */

public class DestinationMapActivity extends AppCompatActivity {
    @InjectView(R.id.collapsingToolbarLayout)
    CollapsingToolbarLayout collapsingToolbarLayout;    // 내부 지도 레이아웃..

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destinationmap);
        ButterKnife.inject(this);


        collapsingToolbarLayout.setTitle(getString(R.string.Interior_Map));
        PhotoViewAttacher attacher;
        ImageView destination = (ImageView)findViewById(R.id.destination);
        attacher = new PhotoViewAttacher(destination);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();   // Back 버튼을 누르면, Activity 종료..
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
