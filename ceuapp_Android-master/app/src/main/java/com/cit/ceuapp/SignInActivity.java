package com.cit.ceuapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class SignInActivity extends AppCompatActivity {
    private AppCompatEditText et_sign_in_email;
    private AppCompatEditText et_sign_in_password;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        mAuth = FirebaseAuth.getInstance();

        et_sign_in_email = (AppCompatEditText) findViewById(R.id.et_sign_in_email);
        et_sign_in_password = (AppCompatEditText) findViewById(R.id.et_sign_in_password);
    }

    @Override
    public void onBackPressed() {
        // TODO: fechar aplicativo
    }

    public void bt_sign_in_access(View view) {
        final Snackbar snackbar = Snackbar.make(findViewById(R.id.cl_sign_in_body), R.string.signing_in, Snackbar.LENGTH_INDEFINITE);
        snackbar.show();

        final String email = et_sign_in_email.getText().toString();
        final String password = et_sign_in_password.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Intent intent = new Intent(SignInActivity.this, EditProfileActivity.class);
                    intent.putExtra(getString(R.string.bundle_code_is_registering), true);
                    startActivity(intent);
                }
                else {
                    try {
                        throw task.getException();
                    }
                    catch (FirebaseAuthUserCollisionException e) {
                        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()) {
                                    Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                                    snackbar.dismiss();
                                    intent.putExtra(getString(R.string.bundle_code_is_registering), false);
                                    intent.putExtra(getString(R.string.bundle_code_show_signed_in_successful), true);
                                    startActivity(intent);
                                }
                                else {
                                    snackbar.dismiss();
                                    AlertDialog.Builder dialog = new AlertDialog.Builder(SignInActivity.this);
                                    dialog.setMessage(R.string.sign_in_error);
                                    dialog.setPositiveButton(R.string.ok, null);
                                    dialog.show();
                                }
                            }
                        });
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
