package org.pursuit.stir;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainHostActivity extends AppCompatActivity implements HomeListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_host);

        replaceWithImageUploadFragment();
    }

    @Override
    public void replaceWithHomeFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit)
                .replace(R.id.main_host_container, HomeFragment.newInstance())
                .commit();
    }

    @Override
    public void replaceWithImageUploadFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit)
                .replace(R.id.main_host_container, ImageUploadFragment.newInstance())
                .commit();
    }
}
