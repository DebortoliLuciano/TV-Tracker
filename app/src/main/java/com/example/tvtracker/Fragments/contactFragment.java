package com.example.tvtracker.Fragments;


import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.tvtracker.R;

/**
 * @author Saad Amjad
 * @date 2020/03/30
 */
public class contactFragment extends Fragment {

    //declare variables like email address and stuff here
    private String phoneNumber = "5555555555";
    private String emailAddress = "w0730752@myscc.ca"; //replace any of these if needed
    private String id = "2Q0gx4r1e3s"; //for the youtube video

    public contactFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact, container, false);

        //declare all the buttons
        Button callButton = view.findViewById(R.id.contactPhone);
        Button emailButton = view.findViewById(R.id.contactEmail);
        Button messageButton = view.findViewById(R.id.contactMessage);
        Button socialMediaButton = view.findViewById(R.id.contactSocialMedia);

        //call intent
        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber, null));
                startActivity(intent);
            }
        });

        //email intent
        emailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_EMAIL, emailAddress);
                intent.putExtra(Intent.EXTRA_SUBJECT,
                        "Question");
                intent.putExtra(Intent.EXTRA_TEXT, "Do you have X show?");
                startActivity(intent);
            }
        });

        //text intent
        messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("smsto:" + phoneNumber);
                Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
                intent.putExtra("sms_body", "I believe that there is a mistake concerning X show.");
                startActivity(intent);
            }
        });

        //links to a video i made cause we all don't have social media apparently
        socialMediaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
                Intent webIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://www.youtube.com/watch?v=" + id));
                try {
                     startActivity(appIntent);
                } catch (ActivityNotFoundException ex) {
                    startActivity(webIntent);
                }
            }
        });

        return view;
    }

}
