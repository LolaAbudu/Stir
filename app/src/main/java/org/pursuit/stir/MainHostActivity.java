package org.pursuit.stir;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Toast;

import org.pursuit.stir.models.FoursquareJSON;

public class MainHostActivity extends AppCompatActivity implements MainHostListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_host);
        replaceWithHomeFragment();

        BottomNavigationView navigationView = findViewById(R.id.bottom_navigation);
        navigationView.setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.navigation_home:
                    Toast.makeText(this, "home selected", Toast.LENGTH_SHORT).show();
                    replaceWithHomeFragment();
                    break;
                case R.id.navigation_profile:
                    Toast.makeText(this, "profile selected", Toast.LENGTH_SHORT).show();
                    replaceWithProfileFragment();
                    break;
                case R.id.navigation_shops:
                    Toast.makeText(this, "shops selected", Toast.LENGTH_SHORT).show();
                    replaceWithShopFragment();
                    break;
                case R.id.navigation_photo_upload:
                    Toast.makeText(this, "add photo selected", Toast.LENGTH_SHORT).show();
                    replaceWithImageUploadFragment();
                    break;
            }
            return true;
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_settings, menu);
        return true;
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
    public void replaceWithProfileFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit)
                .replace(R.id.main_host_container, ProfileFragment.newInstance())
                .commit();
    }

    @Override
    public void replaceWithShopFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit)
                .replace(R.id.main_host_container, ShopFragment.newInstance())
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

    @Override
    public void moveToMap(FoursquareJSON.FoursquareResponse.FoursquareGroup.FoursquareResults.FoursquareVenue foursquareVenue) {
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit)
                .replace(R.id.main_host_container, MapFragment.newInstance(foursquareVenue))
                .commit();

    }
}