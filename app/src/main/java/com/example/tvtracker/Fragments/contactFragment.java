package com.example.tvtracker.Fragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tvtracker.R;
import static com.example.tvtracker.MainActivity.fab;

/**
 * A simple {@link Fragment} subclass.
 */
public class contactFragment extends Fragment {

    public contactFragment() {
        // Required empty public constructor
    }

    public static String[] luciEmail = {"luciano.debortoli31@stclairconnect.ca"};
    public static String[] ethanEmail = {"Ethan.Furtaw35@stclairconnect.ca"};
    public static String[] saadEmail = {"luciano.debortoli31@stclairconnect.ca"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       final View view = inflater.inflate(R.layout.fragment_contact, container, false);

        fab.hide();
        fab.setImageResource(R.drawable.ic_person_black_24dp);
        fab.show();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        Button twitter = view.findViewById(R.id.twitter);
        ImageButton luciPic = view.findViewById(R.id.luci);
        ImageButton ethanPic = view.findViewById(R.id.ethan);
        ImageButton saadPic = view.findViewById(R.id.saad);

        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://twitter.com/tvtracker4"));
                startActivity(intent);
            }
        });

        luciPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_EMAIL, luciEmail);
                intent.putExtra(Intent.EXTRA_SUBJECT, "");
                intent.putExtra(Intent.EXTRA_TEXT,"");
                startActivity(intent);
            }
        });

        ethanPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_EMAIL, ethanEmail);
                intent.putExtra(Intent.EXTRA_SUBJECT, "");
                intent.putExtra(Intent.EXTRA_TEXT,"");
                startActivity(intent);
            }

        });

        saadPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_EMAIL, saadEmail);
                intent.putExtra(Intent.EXTRA_SUBJECT, "");
                intent.putExtra(Intent.EXTRA_TEXT,"");
                startActivity(intent);
            }
        });

        return view;
    }

}
