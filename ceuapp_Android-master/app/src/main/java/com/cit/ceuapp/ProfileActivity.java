package com.cit.ceuapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.File;

public class ProfileActivity extends AppCompatActivity {
    private AppCompatImageView iv_profile_profile_picture;
    private AppCompatTextView tv_profile_description;
    private AppCompatTextView tv_profile_name;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        if(getSupportActionBar() != null) getSupportActionBar().setTitle(R.string.profile);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();

        iv_profile_profile_picture = (AppCompatImageView) findViewById(R.id.iv_profile_profile_picture);
        tv_profile_name = (AppCompatTextView) findViewById(R.id.tv_profile_name);
        tv_profile_description = (AppCompatTextView) findViewById(R.id.tv_profile_description);

        new DAO(ProfileActivity.this).getUserInfo(currentUser.getUid(), new UserInfoListener() {
            @Override
            public void onComplete(@NonNull CeuUser user) {
                fillProfile(user);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_profile_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.profile_action_edit) {
            Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    public void fillProfile(@NonNull CeuUser user) {
        File profilePicture = new File(getFilesDir().getPath() + "/" + currentUser.getUid() + ".jpg");
        iv_profile_profile_picture.setImageURI(Uri.fromFile(profilePicture));
        tv_profile_name.setText(user.getName());
        tv_profile_description.setText(user.getDescription());
    }
}
