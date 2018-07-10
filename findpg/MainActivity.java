package com.example.kankan.findpg;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private BottomNavigationView navigation;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    UploadData uploadData=new UploadData();
                    FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frame_layout,uploadData);
                    fragmentTransaction.commit();

                    return true;
                case R.id.navigation_dashboard:
                    ShowData showData=new ShowData();
                    FragmentTransaction fragmentTransaction1=getSupportFragmentManager().beginTransaction();
                    fragmentTransaction1.replace(R.id.frame_layout,showData);
                    fragmentTransaction1.commit();
                    return true;
                case R.id.navigation_notifications:
                    AccountFragment accountFragment=new AccountFragment();
                    FragmentTransaction fragmentTransaction2=getSupportFragmentManager().beginTransaction();
                    fragmentTransaction2.replace(R.id.frame_layout,accountFragment);
                    fragmentTransaction2.commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.message);
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        UploadData uploadData=new UploadData();
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,uploadData);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_dash,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.mnuRefresh:

                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
