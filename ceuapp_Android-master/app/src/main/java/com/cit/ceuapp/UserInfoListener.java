package com.cit.ceuapp;

import android.support.annotation.NonNull;

public interface UserInfoListener {
    void onComplete(@NonNull CeuUser user);
    // TODO: colocar funcao para tratar erro
}
