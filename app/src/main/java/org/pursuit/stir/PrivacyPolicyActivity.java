package org.pursuit.stir;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

public class PrivacyPolicyActivity extends AppCompatActivity {

    WebView web;
    Button agreeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);

        web =(WebView)findViewById(R.id.webView);
        web.loadUrl("https://raw.githubusercontent.com/LolaAbudu/Stir/master/privacy_policy.md");
        agreeButton = findViewById(R.id.agree_button);
        agreeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PrivacyPolicyActivity.this,
                        SignUpHostActivity.class);
                startActivity(intent);
            }
        });


    }
}
