package com.example.kankan.findpg;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;


/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment {
    private View view;
    private TextView txtProfileName,txtProfileEmail,txtProfileSex,txtLocation;
    private FirebaseAuth myAuth;
    private DatabaseReference myRef;
    private FirebaseUser curUser;
    private ImageView imgPhoto;
    private static final int PROFILE_CODE=103;
    private StorageReference myStore;
    private Uri uriProfilePhoto;
    private SwipeRefreshLayout swipeAcc;



    public AccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_account, container, false);

        txtProfileName=view.findViewById(R.id.txtProfileName);
        txtProfileEmail=view.findViewById(R.id.txtProfileEmail);
        txtProfileSex=view.findViewById(R.id.txtProfileSex);
        txtLocation=view.findViewById(R.id.txtAccountLocation);


        imgPhoto=(ImageView)view.findViewById(R.id.imgKankan);

        myRef= FirebaseDatabase.getInstance().getReferenceFromUrl("https://findpg-6492d.firebaseio.com/");

        myAuth=FirebaseAuth.getInstance();

        myStore= FirebaseStorage.getInstance().getReference();

        curUser=myAuth.getCurrentUser();

        //Toast.makeText(getActivity(),curUser.getUid().toString(),Toast.LENGTH_LONG).show();

        myRef.child("Profile").child(curUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                    //Toast.makeText(getActivity(),dataSnapshot.child("uri").toString(),Toast.LENGTH_LONG).show();
                    Glide.with(getActivity()).load(dataSnapshot.child("url").getValue()).into(imgPhoto);
                    txtProfileSex.setText("Sex: "+dataSnapshot.child("sex").getValue().toString());
                    txtProfileName.setText("Name: "+dataSnapshot.child("name").getValue().toString());
                    txtProfileEmail.setText("Email: "+curUser.getEmail());
                    txtLocation.setText(dataSnapshot.child("flat").getValue().toString()+", "+dataSnapshot.child("locality").getValue().toString()+", "
                                        +dataSnapshot.child("district").getValue().toString()+", "+dataSnapshot.child("pincode").getValue().toString()+
                                        ", "+dataSnapshot.child("state").getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return view;
    }
}
