package org.pursuit.stir;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        circleLogoImage();
    }

    public void circleLogoImage() {
        ImageView imageView = findViewById(R.id.login_logo_imageView);
        Glide.with(getApplicationContext())
                .load(R.mipmap.stir_name_logo)
                .apply(RequestOptions.circleCropTransform())
                .into(imageView);
    }
}
