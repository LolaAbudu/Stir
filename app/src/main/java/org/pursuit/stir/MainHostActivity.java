package org.pursuit.stir;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import org.pursuit.stir.models.FoursquareJSON;

public class MainHostActivity extends AppCompatActivity implements MainHostListener {

    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;

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
                case R.id.navigation_find_coffee_lover:
                    Toast.makeText(this, "find coffee lovers selected", Toast.LENGTH_SHORT).show();
                    replaceWithCoffeeLoversFragment();
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
    public void replaceWithCoffeeLoversFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit)
                .replace(R.id.main_host_container, CoffeeLoversFragment.newInstance())
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
    public void moveToDetailFragment(String imageName, String imageUrl) {
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit)
                .replace(R.id.main_host_container, DetailFragment.newInstance(imageName, imageUrl))
                .addToBackStack(null)
                .commit();
    }

//        @Override
//    public void setBeanLike() {
//        ImageUpload image = new ImageUpload();
//        if (!image.hasLiked) {
//            // add new Like
//            image.hasLiked = true;
//            Bean like = new Bean(image.getImageName(), firebaseUser.getUid());
//            String key = databaseReference.child("likes").push().getKey();
//            databaseReference.child("likes").child(key).setValue(like);
//            image.userLike = key;
//            image.addLike();
//        } else {
//            // remove Like
//            image.hasLiked = false;
//            if (image.userLike != null) {
//                databaseReference.child("likes").child(image.userLike).removeValue();
//                image.removeLike();
//            }
//        }
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sign_out:
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                return true;
                default:
                    return super.onOptionsItemSelected(item);
        }
    }
}

