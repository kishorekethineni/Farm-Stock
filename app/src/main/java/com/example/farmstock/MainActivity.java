package com.example.farmstock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
//Developed By Pranshu Ranjan
public class MainActivity extends AppCompatActivity {

    Button regBtn;
    TextView signIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        regBtn=(Button)findViewById(R.id.regbutton);
        signIn=(TextView)findViewById(R.id.signin);

        // click listener for register button
        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register=new Intent(MainActivity.this,RegisterAcitivity.class);
                startActivity(register);
                finish();
            }
        });
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login=new Intent(MainActivity.this,LoginActivity.class);
                startActivity(login);
                finish();
            }
        });
    }
}
