package com.example.kankan.findpg.UserLogIn;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kankan.findpg.MainActivity;
import com.example.kankan.findpg.R;
import com.example.kankan.findpg.UserRegisterDetails;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth mauth;
    private EditText edtEmailid,edtPassword;
    private Button btnSignin;
    private TextView txtSignin;
    private ProgressDialog dialog;
    private boolean isRegestered=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mauth=FirebaseAuth.getInstance();
        edtEmailid=findViewById(R.id.edtEmailid);
        edtPassword=findViewById(R.id.edtPassword);

        btnSignin=findViewById(R.id.btnSignup);

        txtSignin=findViewById(R.id.txtSignin);

        dialog=new ProgressDialog(SignUpActivity.this);

        mauth=FirebaseAuth.getInstance();

        btnSignin.setOnClickListener(this);
        txtSignin.setOnClickListener(this);

    }



    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnSignup:
                getSignin();

                break;
            case R.id.txtSignin:
                startActivity(new Intent(SignUpActivity.this,SignInActivity.class));
                finish();
                break;
        }

    }
    public void getSignin()
    {
        String email=edtEmailid.getText().toString().trim();
        String password=edtPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email))
        {
            Toast.makeText(SignUpActivity.this,"Enter valid Email id",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password))
        {
            Toast.makeText(SignUpActivity.this,"Enter valid password",Toast.LENGTH_SHORT).show();
            return;
        }
        dialog.setMessage("Registering please wait ...");
        dialog.show();

        mauth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(SignUpActivity.this,"Registered",Toast.LENGTH_SHORT).show();

                    dialog.cancel();
                    edtPassword.setText("");
                    edtEmailid.setText("");
                    isRegestered=true;
                    Intent intent=new Intent(SignUpActivity.this,UserRegisterDetails.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}
