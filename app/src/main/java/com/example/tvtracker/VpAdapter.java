package com.example.tvtracker;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

/**
 * @author Saad Amjad
 * @date 2020/04/03
 */
public class VpAdapter extends Fragment {

    private static final String Param = "placeholderText";
    private static final String Param2 = "placeholderText";


    private String mParam;
    private String mParam2;


    public VpAdapter() {
        // Required empty public constructor
    }

    public static VpAdapter newInstance(String placeHolder, int img) {
        VpAdapter fragment = new VpAdapter();
        Bundle args = new Bundle();
        args.putString(Param2, placeHolder);
        args.putInt(Param, img);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam = getArguments().getString(Param);
            mParam2 = getArguments().getString(Param2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.vp_adapter, container, false);

        TextView TextV = view.findViewById(R.id.vpHeader);
        TextView textV2 = view.findViewById(R.id.vpBody);
        if (mParam2 != null) {
            TextV.setText(mParam);
            textV2.setText(mParam2);
        }

        return view;
    }

}
