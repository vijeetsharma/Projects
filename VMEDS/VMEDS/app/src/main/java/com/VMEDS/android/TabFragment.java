package com.VMEDS.android;

import android.app.Notification;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.VMEDS.android.utils.Global_Typeface;

public class TabFragment extends Fragment {

    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    public static int int_items = 4;
    int page = 0;
    private int[] tabIcons = {
            R.drawable.uhome,
            R.drawable.user,
            R.drawable.offer,
            R.drawable.notification
    };
    private boolean userclicked;
    private Notification.Builder paint;
    private Context context;
    private Global_Typeface global_typeface;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /**
         *Inflate tab_layout and setup Views.
         */
        View v = inflater.inflate(R.layout.tab_layout, container, false);
        viewPager = (ViewPager) v.findViewById(R.id.viewpager);
        global_typeface = new Global_Typeface(getActivity());
        setupViewPager(viewPager);
        viewPager.setOffscreenPageLimit(4);
        tabLayout = (TabLayout) v.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getSelectedTabPosition();
        viewPager.setCurrentItem(((VMEDS) getActivity().getApplicationContext()).getFragmentIndex());
        setupTabIcons();
        changeTabsFont();
        CustomViewPager customViewPager = (CustomViewPager) v.findViewById(R.id.viewpager);
        customViewPager.setPagingEnabled(false);

        return v;

    }

    private void changeTabsFont() {

        ViewGroup vg = (ViewGroup) tabLayout.getChildAt(0);
        int tabsCount = vg.getChildCount();
        for (int j = 0; j < tabsCount; j++) {
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);
            int tabChildsCount = vgTab.getChildCount();
            for (int i = 0; i < tabChildsCount; i++) {
                View tabViewChild = vgTab.getChildAt(i);
                if (tabViewChild instanceof TextView) {
                    ((TextView) tabViewChild).setTypeface(global_typeface.TypeFace_Roboto_Bold());
                }
            }
        }
    }

    private void setupTabIcons() {
        for (int j = 0; j < 4; j++) {
            tabLayout.getTabAt(j).setIcon(tabIcons[j]);

            if (((VMEDS) getActivity().getApplicationContext()).getFragmentIndex() != j)
                tabLayout.getTabAt(j).getIcon().setColorFilter(Color.parseColor("#ffffff"), PorterDuff.Mode.SRC_IN);
            else
                tabLayout.getTabAt(j).getIcon().setColorFilter(Color.parseColor("#fef300"), PorterDuff.Mode.SRC_IN);

        }
        tabLayout.getTabAt(((VMEDS) getActivity().getApplicationContext()).getFragmentIndex()).select();

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(Color.parseColor("#fef300"), PorterDuff.Mode.SRC_IN);
                viewPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(Color.parseColor("#ffffff"), PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        changeTabsFont();

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getFragmentManager());
        adapter.addFrag(new HomeFragment(), "Home");
        adapter.addFrag(new UserProfileFragment(), "Profile");
        adapter.addFrag(new Fragment4(), "Offers");
        adapter.addFrag(new Fragment3(), "Notification");

        viewPager.setAdapter(adapter);
    }

@Override
    public void onResume() {
        super.onResume();
        Log.e("Resume", "R");
        setupTabIcons();
        viewPager.setCurrentItem(((VMEDS) getActivity().getApplicationContext()).getFragmentIndex());
    }
}