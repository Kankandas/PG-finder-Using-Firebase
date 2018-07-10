package com.example.kankan.findpg;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.kankan.findpg.UserLogIn.SignInActivity;
import com.example.kankan.findpg.UserLogIn.SignUpActivity;

public class Splash extends AppCompatActivity {

    private TextView txtName,txtCreater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        txtCreater=findViewById(R.id.txtSplashCreater);
        txtName=findViewById(R.id.txtSplashName);
        Typeface font1=Typeface.createFromAsset(getAssets(),"actionis.ttf");
        Typeface font2=Typeface.createFromAsset(getAssets(),"Beyond Wonderland.ttf");

        txtCreater.setTypeface(font2);
        txtName.setTypeface(font1);
        Handler handler=new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(Splash.this, SignInActivity.class));
                finish();

            }
        },5000);



    }
}
