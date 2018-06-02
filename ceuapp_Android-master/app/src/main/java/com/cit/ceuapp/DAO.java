package com.cit.ceuapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AlertDialog;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;

public class DAO {
    private Context mContext;
    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    private FirebaseStorage mStorage = FirebaseStorage.getInstance();

    public DAO(Context context) {
        this.mContext = context;
    }

    public void uploadProfilePicture(String uid, File profilePicture) {
        String path = mContext.getString(R.string.storage_code_profile_pictures_folder) + "/" + uid + ".jpg";
        StorageReference storageUserRef = mStorage.getReference().child(path);
        storageUserRef.putFile(Uri.fromFile(profilePicture));
    }

    public void getUserInfo(String uid, UserInfoListener listener) {
        SharedPreferences sharedPref = mContext.getSharedPreferences(mContext.getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        if(sharedPref.getString("uid", null) != null) {
            CeuUser user = new CeuUser();

            user.setUid(sharedPref.getString(mContext.getString(R.string.db_code_uid), null));
            user.setEmail(sharedPref.getString(mContext.getString(R.string.db_code_email), null));
            user.setName(sharedPref.getString(mContext.getString(R.string.db_code_name), null));
            user.setTitle(sharedPref.getString(mContext.getString(R.string.db_code_title), null));
            user.setCompany(sharedPref.getString(mContext.getString(R.string.db_code_company), null));
            user.setDescription(sharedPref.getString(mContext.getString(R.string.db_code_description), null));

            listener.onComplete(user);
        }
        else {
            downloadUserInfo(uid, listener);
        }
    }

    public void saveUserInfo(CeuUser user) {
        updateLocalUserInfo(user);
        uploadUserInfo(user);
    }

    private void updateLocalUserInfo(CeuUser user) {
        SharedPreferences.Editor sharedPrefEditor = mContext.getSharedPreferences(mContext.getString(R.string.preference_file_key), Context.MODE_PRIVATE).edit();
        sharedPrefEditor.putString(mContext.getString(R.string.db_code_uid), user.getUid());
        sharedPrefEditor.putString(mContext.getString(R.string.db_code_email), user.getEmail());
        sharedPrefEditor.putString(mContext.getString(R.string.db_code_name), user.getName());
        sharedPrefEditor.putString(mContext.getString(R.string.db_code_title), user.getTitle());
        sharedPrefEditor.putString(mContext.getString(R.string.db_code_company), user.getCompany());
        sharedPrefEditor.putString(mContext.getString(R.string.db_code_description), user.getDescription());
        sharedPrefEditor.putString(mContext.getString(R.string.db_code_type), user.getType().toString());
        sharedPrefEditor.apply();
    }

    private void uploadUserInfo(CeuUser user) {
        DatabaseReference dbUserRef = mDatabase.getReference(mContext.getString(R.string.db_code_users)).child(user.getUid());
        dbUserRef.setValue(user.toHashMap(mContext));
    }

    private void downloadUserInfo(final String uid, final UserInfoListener listener) {
        final AlertDialog dialog = new AlertDialog.Builder(mContext).create();
        // TODO: confirmar se devo pegar o listView como root
        dialog.setView(dialog.getLayoutInflater().inflate(R.layout.dialog_loading_user_data, dialog.getListView()));
        dialog.setCancelable(false);
        dialog.show();

        DatabaseReference dbUserRef = mDatabase.getReference("Users").child(uid);
        dbUserRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final CeuUser user = new CeuUser();
                String aux;

                user.setUid(uid);
                aux = mContext.getString(R.string.db_code_email);
                user.setEmail((String) dataSnapshot.child(aux).getValue());
                aux = mContext.getString(R.string.db_code_name);
                user.setName((String) dataSnapshot.child(aux).getValue());
                aux = mContext.getString(R.string.db_code_title);
                user.setTitle((String) dataSnapshot.child(aux).getValue());
                aux = mContext.getString(R.string.db_code_company);
                user.setCompany((String) dataSnapshot.child(aux).getValue());
                aux = mContext.getString(R.string.db_code_description);
                user.setDescription((String) dataSnapshot.child(aux).getValue());
                aux = mContext.getString(R.string.db_code_type);
                user.setType((String) dataSnapshot.child(aux).getValue());

                updateLocalUserInfo(user);

                String path = mContext.getString(R.string.storage_code_profile_pictures_folder) + "/" + uid + ".jpg";
                StorageReference storageUserRef = mStorage.getReference().child(path);

                final File file = new File(mContext.getFilesDir(), uid + ".jpg");
                storageUserRef.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        listener.onComplete(user);
                        dialog.dismiss();
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // TODO: tratar erro
            }
        });
    }
}
