package org.pursuit.stir;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public class ErrorActivity extends AppCompatActivity {

    private static final int ERROR_DISPLAY_LENGTH = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.LoginTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error);
        ImageView errorGif = findViewById(R.id.error_gif);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(getApplicationContext(), MainHostActivity.class);
                startActivity(intent);
                finish();
            }
        }, ERROR_DISPLAY_LENGTH);
    }
}

