package com.weapon.joker.app.mine;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MineActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_mine_content, new MainFragment()).commit();
    }
}
