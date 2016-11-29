package duribon.dlug.org.duribonduribon.Fragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by neonkid on 11/21/16.
 */

public class TabPagerAdapter extends FragmentStatePagerAdapter {
    private int TAB_COUNT;

    public TabPagerAdapter(FragmentManager fm, int tab_count) {
        super(fm);
        this.TAB_COUNT = tab_count;
    }

    // 탭 안에 들어가는 내용,,
    @Override
    public Fragment getItem(int position) {
        switch(position) {
            case 0:
                return new MapFragment();   // 외부 지도
            case 1:
                return new TimetableFragment(); // 시간표
        }
        return null;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return TAB_COUNT;
    }
}
