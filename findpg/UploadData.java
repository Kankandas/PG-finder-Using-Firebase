package com.example.kankan.findpg;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class UploadData extends Fragment implements View.OnClickListener,RadioGroup.OnCheckedChangeListener {
    private Button btnUpload;
    private ImageView imgHouse,imgRoom;
    private Spinner sp;
    private List<String> arrayList;
    private EditText edtState,edtPincode,edtLocality,edtDistrict;
    private RadioGroup radioGroup;
    private RadioButton radbtn2,radbtn3,radbtn4,radbtn5;
    private String roomType="",state,district,locality,pincode;
    private TextView txtSelect,txtNoBed;
    private int noOfBed=0;
    private static final int GALARY_CODE_HOUSE=100;
    private static final int GALARY_CODE_ROOM=101;
    private Uri imgUriHouse,imgUriRoom;
    private boolean boobs=false;
    private StorageReference mStorage;
    private DatabaseReference mDatabase;
    String imgHouseUrl;
    String imgRoomUrl;


    public UploadData() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_upload_data, container, false);
        btnUpload=(Button)v.findViewById(R.id.btnUpload);
        btnUpload.setVisibility(View.INVISIBLE);
        imgHouse=(ImageView)v.findViewById(R.id.imgHouse);
        imgRoom=(ImageView)v.findViewById(R.id.imgRoom);
        sp=(Spinner)v.findViewById(R.id.spTypeOFRoom);
        radioGroup=(RadioGroup)v.findViewById(R.id.radioGrp);
        imgHouse.setImageResource(R.drawable.galary);
        imgRoom.setImageResource(R.drawable.galary);
        edtDistrict=(EditText)v.findViewById(R.id.edtDistrict);
        edtLocality=(EditText)v.findViewById(R.id.edtLocality);
        edtPincode=(EditText)v.findViewById(R.id.edtPincode);
        edtState=(EditText)v.findViewById(R.id.edtState);
        radbtn2=(RadioButton)v.findViewById(R.id.radbtn2);
        radbtn3=(RadioButton)v.findViewById(R.id.radbtn3);
        radbtn4=(RadioButton)v.findViewById(R.id.radbtn4);
        radbtn5=(RadioButton)v.findViewById(R.id.radbtnMore);
        radioGroup.setVisibility(View.INVISIBLE);
        txtSelect=(TextView)v.findViewById(R.id.txtSelect);
        txtNoBed=(TextView)v.findViewById(R.id.txtNoBed);
        btnUpload.setOnClickListener(UploadData.this);
        imgRoom.setOnClickListener(UploadData.this);
        imgHouse.setOnClickListener(UploadData.this);

        mDatabase= FirebaseDatabase.getInstance().getReferenceFromUrl("https://findpg-6492d.firebaseio.com/");
        mStorage= FirebaseStorage.getInstance().getReference();

        txtNoBed.setVisibility(View.INVISIBLE);
        arrayList=new ArrayList<String>();
        arrayList.add("Not Selected");
        arrayList.add("Single Bed Room");
        arrayList.add("Multi Bed Room");
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_dropdown_item_1line,arrayList);
        sp.setAdapter(adapter);

        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position)
                {
                    case 0:
                            txtSelect.setVisibility(View.VISIBLE);
                            radioGroup.setVisibility(View.INVISIBLE);
                            btnUpload.setVisibility(View.INVISIBLE);
                            txtNoBed.setVisibility(View.INVISIBLE);
                            noOfBed=0;
                        break;
                    case 1:
                        roomType=parent.getItemAtPosition(position).toString();
                        txtSelect.setVisibility(View.INVISIBLE);
                        radioGroup.setVisibility(View.INVISIBLE);
                        btnUpload.setVisibility(View.VISIBLE);
                        txtNoBed.setVisibility(View.INVISIBLE);
                        boobs=true;
                        noOfBed=1;
                        break;
                    case 2:
                        noOfBed=0;
                        roomType=parent.getItemAtPosition(position).toString();
                        radioGroup.setVisibility(View.VISIBLE);
                        radioGroup.setOnCheckedChangeListener(UploadData.this);
                        txtSelect.setVisibility(View.INVISIBLE);
                        btnUpload.setVisibility(View.VISIBLE);
                        txtNoBed.setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        state=edtState.getText().toString();
        locality=edtLocality.getText().toString();
        district=edtDistrict.getText().toString();
        pincode=edtPincode.getText().toString();




        return v;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.imgHouse:
                Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,GALARY_CODE_HOUSE);
                break;
            case R.id.imgRoom:
                Intent intent1=new Intent(Intent.ACTION_GET_CONTENT);
                intent1.setType("image/*");
                startActivityForResult(intent1,GALARY_CODE_ROOM);
                break;
            case R.id.btnUpload:
                try {
                    if (edtState.getText().toString().equals("") || edtPincode.getText().toString().equals("") || edtLocality.getText().toString().equals("")
                            || edtDistrict.getText().toString().equals("") || (noOfBed == 0) || imgUriRoom.equals(null) || imgUriHouse.equals(null)) {


                        Toast.makeText(getActivity(), "Enter Valid Details", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                catch (Exception e){
                    Toast.makeText(getActivity(),"Please Select Some Photo",Toast.LENGTH_SHORT).show();
                    return;
                }
                final ProgressDialog dialog=new ProgressDialog(getActivity());
                dialog.setTitle("UPLOAD");
                dialog.setMessage("Uploading on Progress....");
                dialog.show();
                if(noOfBed==1)
                {
                    roomType="Single Bed Room";
                }
                if(noOfBed>1)
                {roomType="Multi Bed Room";
                }

                if(imgUriHouse!=null)
                {
                    StorageReference ref1=mStorage.child("image House"+System.currentTimeMillis());
                    ref1.putFile(imgUriHouse).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            imgHouseUrl=taskSnapshot.getDownloadUrl().toString();
                        }
                    });
                }
                if(imgUriRoom!=null)
                {
                    StorageReference ref2=mStorage.child("image Room"+System.currentTimeMillis());
                    ref2.putFile(imgUriRoom).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            dialog.dismiss();
                            Toast.makeText(getContext(),"Uploading Done",Toast.LENGTH_SHORT).show();
                            imgRoomUrl=taskSnapshot.getDownloadUrl().toString();
                            MyRoom myRoom=new MyRoom(imgRoomUrl,imgHouseUrl,roomType,String.valueOf(noOfBed),edtState.getText().toString()
                                                    ,edtDistrict.getText().toString(),edtLocality.getText().toString(),edtPincode.getText().toString());
                            String uploadId=mDatabase.push().getKey();
                            mDatabase.child("Dummy Rooms").child(uploadId).setValue(myRoom);
                            edtDistrict.setText("");
                            edtLocality.setText("");
                            edtPincode.setText("");
                            edtState.setText("");
                            imgRoom.setImageResource(R.drawable.galary);
                            imgHouse.setImageResource(R.drawable.galary);


                        }
                    });
                }

                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.radbtn2:
                noOfBed=2;
                boobs=true;
                break;
            case R.id.radbtn3:
                noOfBed=3;
                boobs=true;
                break;
            case R.id.radbtn4:
                noOfBed=4;
                boobs=true;
                break;
            case R.id.radbtnMore:
                noOfBed=6;
                boobs=true;
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GALARY_CODE_HOUSE && resultCode==getActivity().RESULT_OK)
        {
            imgUriHouse=data.getData();
            try
            {
                Bitmap bm= MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imgUriHouse);
                imgHouse.setImageBitmap(bm);

            }catch (Exception e)
            {

            }

        }
        if(requestCode==GALARY_CODE_ROOM && resultCode==getActivity().RESULT_OK)
        {
            imgUriRoom=data.getData();
            try
            {
                Bitmap bms= MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imgUriRoom);
                imgRoom.setImageBitmap(bms);

            }catch (Exception e)
            {

            }

        }


    }
}
