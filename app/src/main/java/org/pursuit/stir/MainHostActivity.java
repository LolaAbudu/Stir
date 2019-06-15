package org.pursuit.stir;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import org.pursuit.stir.models.FoursquareJSON;

public class MainHostActivity extends AppCompatActivity implements MainHostListener {


    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private static final String TAG = "germ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_host);
        firebaseAuth = FirebaseAuth.getInstance();

        userCheck();
        BottomNavigationView navigationView = findViewById(R.id.bottom_navigation);
        navigationView.setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.navigation_home:
                    replaceWithHomeFragment();
                    break;
                case R.id.navigation_profile:
                    replaceWithProfileFragment();
                    break;
                case R.id.navigation_shops:
                    replaceWithShopFragment();
                    break;
                case R.id.navigation_photo_upload:
                    replaceWithImageUploadFragment();
                    break;
                case R.id.navigation_find_coffee_lover:
                    //replaceWithCoffeeLoversFragment("random");
                    replaceWithChatListFragment();

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
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void replaceWithShopFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit)
                .replace(R.id.main_host_container, ShopFragment.newInstance())
                .addToBackStack(null)
                .commit();

    }

    @Override
    public void replaceWithImageUploadFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit)
                .replace(R.id.main_host_container, ImageUploadFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void replaceWithCoffeeLoversFragment(String chatKey) {
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit)
                .replace(R.id.main_host_container, CoffeeLoversFragment.newInstance(chatKey))
                .addToBackStack(null)
                .commit();
    }


    @Override
    public void moveToMap(FoursquareJSON.FoursquareResponse.FoursquareGroup.FoursquareResults.FoursquareVenue foursquareVenue) {
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit)
                .replace(R.id.main_host_container, MapFragment.newInstance(foursquareVenue))
                .addToBackStack(null)
                .commit();

    }

    @Override
    public void moveToDetailFragment(String imageName, String shopname, String imageUrl, String userID) {
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit)
                .replace(R.id.main_host_container, DetailFragment.newInstance(imageName, shopname, imageUrl, userID))
                .addToBackStack(null)
                .commit();
    }


    @Override

    public void startErrorActivity() {
        Intent intent = new Intent(this, ErrorActivity.class);
        startActivity(intent);
    }


    public void replaceWithCoffeePrefFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                //  .setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit)
                .replace(R.id.main_host_container, CoffeePrefFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void replaceWithChatListFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_host_container, ChatListFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void replaceWithProfilePhotoFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_host_container, ProfilePhotoFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sign_out:
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                return true;
//                default:
//                    return super.onOptionsItemSelected(item);
            case R.id.coffee_pref:
                replaceWithCoffeePrefFragment();
                return true;
            case R.id.edit_profile_photo:
                replaceWithProfilePhotoFragment();
                return true;

        }
        return super.onOptionsItemSelected(item);

    }

    public void userCheck() {
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        if (currentUser != null) {
            replaceWithHomeFragment();
            Log.d(TAG, "onStart: " + currentUser.getDisplayName());
        } else {
            Log.d(TAG, "onStart: error, currentUser is null");
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }
    }
}

