package com.example.kankan.findpg;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

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

public class UserRegisterDetails extends AppCompatActivity implements View.OnClickListener,RadioGroup.OnCheckedChangeListener{

    private EditText edtUploadProfileName,edtUploadProfileLocality,edtUploadProfileDistrict,edtUploadProfileFlatNo,
                    edtUploadProfilePincode,edtUploadProfileState;
    private TextView txtUploadProfileEmailid;
    private RadioGroup radGrpUserProfile;
    private RadioButton radbtnUploadProfileMalesex,radbtnUploadProfileFemalesex;
    private Button btnUploadProfile;
    private DatabaseReference myRef;
    private FirebaseAuth myAuth;
    private FirebaseUser curUser;
    private ImageView imgUploadProfilePhoto;
    private String sex="";
    private static final int GALARY_CODE=987;
    private String imgUri;
    private Uri uri,cpyUri;
    private StorageReference myStore;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register_details);

        imgUploadProfilePhoto=findViewById(R.id.imgUploadProfilePhoto);

        edtUploadProfileLocality=findViewById(R.id.edtUploadProfileLocality);
        edtUploadProfileDistrict=findViewById(R.id.edtUploadProfileDistrict);
        edtUploadProfileFlatNo=findViewById(R.id.edtUploadProfileFlatNo);
        edtUploadProfilePincode=findViewById(R.id.edtUploadProfilePincode);
        edtUploadProfileState=findViewById(R.id.edtUploadProfileState);
        edtUploadProfileName=findViewById(R.id.edtUploadProfileName);

        txtUploadProfileEmailid=findViewById(R.id.txtUploadProfileEmailid);

        radGrpUserProfile=findViewById(R.id.radGrpUserProfile);

        radbtnUploadProfileFemalesex=findViewById(R.id.radbtnUploadProfileFemalesex);
        radbtnUploadProfileMalesex=findViewById(R.id.radbtnUploadProfileMalesex);

        btnUploadProfile=findViewById(R.id.btnUploadProfile);

        dialog=new ProgressDialog(UserRegisterDetails.this);

        myRef=FirebaseDatabase.getInstance().getReferenceFromUrl("https://findpg-6492d.firebaseio.com/");
        myStore=FirebaseStorage.getInstance().getReference();
        myAuth=FirebaseAuth.getInstance();
        curUser=myAuth.getCurrentUser();
        txtUploadProfileEmailid.setText(curUser.getEmail().toString());

        Bitmap bm= BitmapFactory.decodeResource(getResources(),R.drawable.findpg);
        RoundedBitmapDrawable factory=RoundedBitmapDrawableFactory.create(getResources(),bm);
        factory.setCircular(true);
        imgUploadProfilePhoto.setImageDrawable(factory);

        btnUploadProfile.setOnClickListener(this);
        radGrpUserProfile.setOnCheckedChangeListener(this);
        imgUploadProfilePhoto.setOnClickListener(this);


    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId)
        {
            case R.id.radbtnUploadProfileFemalesex:
                sex="Female";
                break;
            case R.id.radbtnUploadProfileMalesex:
                sex="Male";
                break;
        }
    }



    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnUploadProfile:
                uploadProfile();
                break;
            case R.id.imgUploadProfilePhoto:
                Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,GALARY_CODE);
                break;
        }
    }

    private void uploadProfile() {
        final String name=edtUploadProfileName.getText().toString().trim();
        final String locality=edtUploadProfileLocality.getText().toString().trim();
        final String district=edtUploadProfileDistrict.getText().toString().trim();
        final String state=edtUploadProfileState.getText().toString().trim();
        final String pincode=edtUploadProfilePincode.getText().toString().trim();
        final String flat=edtUploadProfileFlatNo.getText().toString().trim();
        if(TextUtils.isEmpty(name))
        {
            Toast.makeText(UserRegisterDetails.this,"Enter Valid Name",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(locality))
        {
            Toast.makeText(UserRegisterDetails.this,"Enter Valid Address",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(sex))
        {
            Toast.makeText(UserRegisterDetails.this,"Enter valid Sex type",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(district))
        {
            Toast.makeText(UserRegisterDetails.this,"Enter valid District",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(pincode)||(pincode.length()<6))
        {
            Toast.makeText(UserRegisterDetails.this,"Enter valid pincode",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(flat))
        {
            Toast.makeText(UserRegisterDetails.this,"Enter Your House Details",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(state))
        {
            Toast.makeText(UserRegisterDetails.this,"Enter valid State",Toast.LENGTH_SHORT).show();
            return;
        }
        dialog.setMessage("Loading Your Profile...");
        dialog.show();
        StorageReference ref=myStore.child("profile Photo "+System.currentTimeMillis());
        ref.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                String url=taskSnapshot.getDownloadUrl().toString();

                MyProfile myProfile=new MyProfile(name,locality,state,flat,url,district,sex,pincode);
                myRef.child("Profile").child(curUser.getUid()).setValue(myProfile);
                startActivity(new Intent(UserRegisterDetails.this,MainActivity.class));
                dialog.dismiss();
                finish();
            }
        });


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GALARY_CODE && resultCode==RESULT_OK)
        {
            uri=data.getData();
            try
            {
                Bitmap bm1= MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                RoundedBitmapDrawable fact=RoundedBitmapDrawableFactory.create(getResources(),bm1);
                fact.setCircular(true);
                imgUploadProfilePhoto.setImageDrawable(fact);
                Toast.makeText(UserRegisterDetails.this,uri.toString(),Toast.LENGTH_LONG).show();
            }
            catch (Exception e)
            {

            }
        }
    }

}
