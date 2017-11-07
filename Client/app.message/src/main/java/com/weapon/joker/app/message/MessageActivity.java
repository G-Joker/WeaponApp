package com.weapon.joker.app.message;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_empty);
        getSupportFragmentManager().beginTransaction().replace(R.id.content, new MainFragment()).commit();
    }
}
