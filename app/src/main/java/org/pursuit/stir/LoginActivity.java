package org.pursuit.stir;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class LoginActivity extends AppCompatActivity {

    private Button loginButton;
    private TextView signUpLink;
    private SignUpListener signUpListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.LoginTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        createRoundLogoImage();

        signUpLink = findViewById(R.id.login_sign_up_textview);
        signUpLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpHostActivity.class);
                startActivity(intent);
            }
        });
    }

    public void createRoundLogoImage() {
        ImageView imageView = findViewById(R.id.login_logo_imageView);
        Glide.with(getApplicationContext())
                .load(R.mipmap.stir_name_logo)
                .apply(RequestOptions.circleCropTransform())
                .into(imageView);
    }
}
