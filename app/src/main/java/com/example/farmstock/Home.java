package com.example.farmstock;
//Developed By Pranshu Ranjan
import android.app.Application;
import android.content.Intent;
import android.widget.Toast;

import com.example.farmstock.ui.slideshow.Function;
import com.example.farmstock.ui.slideshow.SlideshowFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Home extends Application {
    @Override
    public void onCreate(){
        super.onCreate();
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();

        if (Function.isNetworkAvailable(this)) {
            if(firebaseUser!=null){
                Intent intent=new Intent(Home.this,Main2Activity.class);

                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(intent);

            }
        } else {
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_LONG).show();
            System.exit(0);
        }


    }
}
