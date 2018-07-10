package com.example.kankan.findpg.UserLogIn;

import android.app.ProgressDialog;
import android.content.Intent;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInActivity extends AppCompatActivity  implements View.OnClickListener{
    private EditText edtEmailLogin,edtEmailpassword;
    private TextView txtViewSignUp;
    private Button btnSigninuser;
    private FirebaseAuth myauth;
    private ProgressDialog mydialog;
    private FirebaseUser curUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        edtEmailLogin=findViewById(R.id.edtEmailidForUser);
        edtEmailpassword=findViewById(R.id.edtPasswordForUser);

        txtViewSignUp=findViewById(R.id.txtSignUpForuserBurdwan);

        btnSigninuser=findViewById(R.id.btnSigninForuser);

        myauth=FirebaseAuth.getInstance();
        curUser=myauth.getCurrentUser();
        if(curUser !=null)
        {
            startActivity(new Intent(SignInActivity.this,MainActivity.class));
            finish();
        }

        mydialog=new ProgressDialog(SignInActivity.this);

        btnSigninuser.setOnClickListener(this);
        txtViewSignUp.setOnClickListener(this);




    }
    public void getSignin()
    {
        String email=edtEmailLogin.getText().toString().trim();
        String password=edtEmailpassword.getText().toString().trim();

        if(TextUtils.isEmpty(email))
        {
            Toast.makeText(SignInActivity.this,"Enter valid Email id",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password))
        {
            Toast.makeText(SignInActivity.this,"Enter valid Password",Toast.LENGTH_SHORT).show();
            return;
        }

        mydialog.setMessage("Logging in...");
        mydialog.show();

        myauth.signInWithEmailAndPassword(email,password).addOnCompleteListener(SignInActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                mydialog.dismiss();

                if(task.isSuccessful())
                {
                    startActivity(new Intent(SignInActivity.this, MainActivity.class));
                    finish();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnSigninForuser:
                getSignin();
                break;
            case R.id.txtSignUpForuserBurdwan:
                startActivity(new Intent(SignInActivity.this,SignUpActivity.class));
                finish();

                break;
        }
    }
}
