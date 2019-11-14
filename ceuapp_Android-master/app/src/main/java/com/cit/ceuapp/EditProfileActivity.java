package com.cit.ceuapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class EditProfileActivity extends AppCompatActivity {
    private AppCompatEditText et_edit_profile_email;
    private AppCompatEditText et_edit_profile_name;
    private AppCompatEditText et_edit_profile_title;
    private AppCompatEditText et_edit_profile_company;
    private AppCompatEditText et_edit_profile_description;
    private boolean isRegistering;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        et_edit_profile_email = (AppCompatEditText) findViewById(R.id.et_edit_profile_email);
        et_edit_profile_name = (AppCompatEditText) findViewById(R.id.et_edit_profile_name);
        et_edit_profile_title = (AppCompatEditText) findViewById(R.id.et_edit_profile_title);
        et_edit_profile_company = (AppCompatEditText) findViewById(R.id.et_edit_profile_company);
        et_edit_profile_description = (AppCompatEditText) findViewById(R.id.et_edit_profile_description);

        isRegistering = getIntent().getExtras() != null && getIntent().getExtras().getBoolean(getString(R.string.bundle_code_is_registering));
        if (isRegistering) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setTitle(R.string.finish_profile);
            et_edit_profile_email.setText(currentUser.getEmail());
        }
        else {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_clear_white_36dp);
            getSupportActionBar().setTitle(R.string.edit_profile);
            new DAO(this).getUserInfo(currentUser.getUid(), new UserInfoListener() {
                @Override
                public void onComplete(@NonNull CeuUser user) {
                    fillProfile(user);
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        if(!isRegistering) super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_edit_profile_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.edit_profile_action_save) {
            CeuUser user = new CeuUser();
            user.setUid(currentUser.getUid());
            user.setEmail(currentUser.getEmail());
            user.setName(et_edit_profile_name.getText().toString());
            user.setTitle(et_edit_profile_title.getText().toString());
            user.setCompany(et_edit_profile_company.getText().toString());
            user.setDescription(et_edit_profile_description.getText().toString());
            user.setType("4");

            new DAO(EditProfileActivity.this).saveUserInfo(user);

            if(isRegistering) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
            else {
                NavUtils.navigateUpFromSameTask(this);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void fillProfile(@NonNull CeuUser user) {
        et_edit_profile_email.setText(user.getEmail());
        et_edit_profile_name.setText(user.getName());
        et_edit_profile_title.setText(user.getTitle());
        et_edit_profile_company.setText(user.getCompany());
        et_edit_profile_description.setText(user.getDescription());
    }
}
