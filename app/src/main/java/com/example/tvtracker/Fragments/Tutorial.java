package com.example.tvtracker.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tvtracker.R;

/**
 * @author Saad Amjad
 * @date 2020/04/04
 */

public class Tutorial extends Fragment {

    public Tutorial() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tutorial, container, false);

            final ViewPager pager = view.findViewById(R.id.vp);
            final CustomViewPagerAdapter adapter =
                    new CustomViewPagerAdapter(getChildFragmentManager());
            pager.setPageTransformer(true, new DepthPageTransformer());
            pager.setAdapter(adapter);

           return view;
        }

        public class CustomViewPagerAdapter extends FragmentPagerAdapter {

            public CustomViewPagerAdapter(FragmentManager fm) {
                super(fm);
            }

            @Override
            public Fragment getItem(int position) {
                switch (position){
                    case 0: return com.example.tvtracker.VpAdapter.newInstance("Home", "Home/Search\n\nHome is basically our search screen.\nYou can enter in the search bar and relevant results will be returned.\nYou can press the star icon to mark shows that you wan to watch.");
                    case 1: return com.example.tvtracker.VpAdapter.newInstance("Watch list", "Watch List\n\nShows starred will end up here in convenient list form.\nClicking on a show will bring up the details page.\nYou will find extended descriptions and buttons to remove or mark as watched.");
                    case 2: return com.example.tvtracker.VpAdapter.newInstance("Watched", "Watched List\n\nButtons marked as watched will end up here.\n Additionally these shows will be flagged in search as watched.");
                    case 3: return com.example.tvtracker.VpAdapter.newInstance("Contact", "Contact/About Us\n\nHere you will find the various ways to contact us.\nIncluding: Phone, Email, SMS/text/message.\nAlso contains a link to our Youtube.");
                    default: return com.example.tvtracker.VpAdapter.newInstance("Placeholder", "Can't believe you've done this");
                }
            }

            @Override
            public int getCount() {
                return 4;
            }
        }

    public class DepthPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.90f;
        private static final float MIN_ALPHA = 0.60f;

        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();
            int pageHeight = view.getHeight();

            if (position < -1) {
                view.setAlpha(0f);

            } else if (position <= 1) { // [-1,1]
                float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                float vertMargin = pageHeight * (1 - scaleFactor) / 4;
                float horzMargin = pageWidth * (1 - scaleFactor) / 4;
                if (position < 0) {
                    view.setTranslationX(horzMargin - vertMargin / 4);
                } else {
                    view.setTranslationX(-horzMargin + vertMargin / 4);
                }

                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

                view.setAlpha(MIN_ALPHA +
                        (scaleFactor - MIN_SCALE) /
                                (1 - MIN_SCALE) * (1 - MIN_ALPHA));

            } else {
                view.setAlpha(0f);
            }
        }
    }
}
