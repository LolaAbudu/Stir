package org.pursuit.stir;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

public class SignUpHostActivity extends AppCompatActivity implements SignUpListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.LoginTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_host);
        replaceWithSignUpFragment();
    }


    @Override
    public void replaceWithSignUpFragment(){
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit)
                .replace(R.id.sign_up_container, SignUpFragment.newInstance())
                .commit();
    }

    @Override
    public void replaceWithCoffeePrefFragment(){
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit)
                .replace(R.id.sign_up_container, CoffeePrefFragment.newInstance())
                .commit();
    }

    @Override
    public void moveToPrivacyPolicyActivity() {
        Intent intent = new Intent(this,
                PrivacyPolicyActivity.class);
        startActivity(intent);

    }
}
