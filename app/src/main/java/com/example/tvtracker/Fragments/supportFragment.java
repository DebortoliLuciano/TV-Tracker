package com.example.tvtracker.Fragments;


import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.tvtracker.R;
import com.google.android.material.snackbar.Snackbar;

import static com.example.tvtracker.MainActivity.fab;



/**
 * A simple {@link Fragment} subclass.
 */
public class supportFragment extends Fragment {


    public supportFragment() {
        // Required empty public constructor
    }

    public static final String[] emailSupport = {"support@tvracker.ca"};
    public static final String phone = "123 456 7890";
    public static final String name = "TVTracker Support line";


    public static final int PERMISSION_SEND_SMS = 1;
    public static final int PERMISSION_CALL_PHONE=2;
    public static final int PERMISSION_WRITE_CONTACTS=3;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_support, container, false);
        fab.hide();
        fab.setImageResource(R.drawable.ic_contact_phone_black_24dp);
        fab.show();

        Button email = view.findViewById(R.id.email);
        Button text = view.findViewById(R.id.text);
        Button call = view.findViewById(R.id.call);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Snackbar.make(v, "Add us to your contacts!",Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_CONTACTS)!= PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.WRITE_CONTACTS)) {
                        final AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                                .setTitle("WRITE CONTACTS")
                                .setMessage("We need access to your contacts to be able to add a contact")
                                .create();
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                alertDialog.dismiss();
                                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_CONTACTS},
                                        PERMISSION_WRITE_CONTACTS);
                            }
                        });
                        alertDialog.show();
                    } else {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_CONTACTS},
                                PERMISSION_WRITE_CONTACTS);
                    }
                    Intent intent = new Intent(Intent.ACTION_INSERT);
                    intent.setType(ContactsContract.Contacts.CONTENT_TYPE);
                    intent.putExtra(ContactsContract.Intents.Insert.NAME, name);
                    intent.putExtra(ContactsContract.Intents.Insert.EMAIL, emailSupport);
                    intent.putExtra(ContactsContract.Intents.Insert.PHONE, phone);

                }else{
                    Toast.makeText(getContext(), "No software installed to complete task", Toast.LENGTH_SHORT).show();
                }
            }
        });

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_EMAIL, emailSupport);
                intent.putExtra(Intent.EXTRA_SUBJECT, "Support ticket");
                intent.putExtra(Intent.EXTRA_TEXT,"Issue");
                startActivity(intent);
            }
        });

        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED){
                    if(ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),Manifest.permission.SEND_SMS)){
                        final AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                                .setTitle("SEND SMS")
                                .setMessage("We require access to SMS to be able to send a SMS")
                                .create();
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                alertDialog.dismiss();
                                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.SEND_SMS}, PERMISSION_SEND_SMS);
                            }
                        });
                        alertDialog.show();
                    } else {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.SEND_SMS},
                                PERMISSION_SEND_SMS);
                    }
                } else {
                    Intent intent = new Intent(Intent.ACTION_SENDTO);
                    intent.setData(Uri.parse("smsto:"));
                    intent.putExtra("address", phone);
                    intent.putExtra("sms_body","Hi! I am having an issue, ");
                    startActivity(intent);
                }


            }
        });

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.CALL_PHONE)) {
                        final AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                                .setTitle("CALL PHONE")
                                .setMessage("We need access to your phone to be able to make a call")
                                .create();
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                alertDialog.dismiss();
                                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE},
                                        PERMISSION_CALL_PHONE);
                            }
                        });
                        alertDialog.show();
                    } else {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE},
                                PERMISSION_CALL_PHONE);
                    }
                } else {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + phone));
                    if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                        startActivity(intent);

                    }
                }
            }
        });

        return view;
    }

}
