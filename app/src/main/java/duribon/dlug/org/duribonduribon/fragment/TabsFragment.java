package duribon.dlug.org.duribonduribon.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import duribon.dlug.org.duribonduribon.MainActivity;
import duribon.dlug.org.duribonduribon.R;

/**
 * Created by neonkid on 10/31/16.
 */

public class TabsFragment extends Fragment {
    @InjectView(R.id.tabLayout)
    TabLayout tabLayout;
    @InjectView(R.id.viewpager)
    ViewPager viewPager;

    private final int TAB_COUNT = 2;

    public static TabsFragment newInstance() {
        TabsFragment fragment = new TabsFragment();
        return fragment;
    }

    public TabsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_tabs, container, false);
        ButterKnife.inject(this, rootview);
        return rootview;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        TabPagerAdapter adapter = new TabPagerAdapter(getFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabsFromPagerAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    private class TabPagerAdapter extends FragmentPagerAdapter {
        public TabPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        // 탭 안에 들어가는 내용,,
        @Override
        public Fragment getItem(int position) {
            switch(position) {
                case 0:
                    return new MapFragment();
                case 1:
                    return new TimetableFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return TAB_COUNT;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch(position) {
                case 0:
                    return getString(R.string.tab_Map);
                case 1:
                    return getString(R.string.tab_timetable);
            }
            return "TAB " + position;
        }
    }

    /*
        임시로 기본 화면을 띄우기 위한 클래스,,
        절대 건드리지 말 것.
     */
    public static class CurrentFragment extends Fragment {
        @InjectView(R.id.textView)
        TextView textView;

        private static final String ARGS_KEY = "idx";

        public static CurrentFragment CurrentFragment(int position) {
            CurrentFragment fragment = new CurrentFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(ARGS_KEY, position);
            fragment.setArguments(bundle);
            return fragment;
        }

        public CurrentFragment() {

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_tab_fragment, container, false);
            ButterKnife.inject(this, rootView);
            return rootView;
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            int idx = getArguments().getInt(ARGS_KEY, -1);
            textView.setText("Page Position" + idx);
        }

        @Override
        public void onDestroyView() {
            super.onDestroyView();
            ButterKnife.reset(this);
        }
    }
}
