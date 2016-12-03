package duribon.dlug.org.duribonduribon.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import duribon.dlug.org.duribonduribon.Fragments.MapFragment;
import duribon.dlug.org.duribonduribon.R;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by neonkid on 11/5/16.
 *
 * 내부 지도를 구현한 Activity,,
 */
public class InteriorMapActivity extends AppCompatActivity {
    @InjectView(R.id.collapsingToolbarLayout)
    CollapsingToolbarLayout collapsingToolbarLayout;    // 내부 지도 레이아웃..

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interiormap);
        ButterKnife.inject(this);

<<<<<<< Updated upstream
=======
        MapFragment.room_flag = false;  // 원상 복귀,,

        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

>>>>>>> Stashed changes
        collapsingToolbarLayout.setTitle(getString(R.string.Interior_Map));
        PhotoViewAttacher attacher;
        ImageView entrance = (ImageView)findViewById(R.id.entrance);
        attacher = new PhotoViewAttacher(entrance);
        /*if()
        {
        //검색 값에 따라서 보여주는 층수를 다르게 해야함
        }
        else //1층인 경우
        {
            //버튼을 없애야함.
        }
        */

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

    public void nextFloor(View v){
        Intent intent = new Intent(this, DestinationMapActivity.class);
        startActivity(intent);
    }
    /*
    public class OpenCV {
        static {
            System.loadLibrary("hello");
        }
        public native String getHelloNDKString();
    }*/
}
