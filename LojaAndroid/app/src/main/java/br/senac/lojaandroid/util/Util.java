package br.senac.lojaandroid.util;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;

import br.senac.lojaandroid.view.CarrinhoActivity;

public class Util {

    // Formata o valor do Preco
    public static String formatPreco(Double x){
        DecimalFormat format = new DecimalFormat("RS: 0.00");
        return format.format(x);
    }

    // Cria Toast
    public static void showToast(final Application app, String msg){
        Toast.makeText(app, msg, Toast.LENGTH_LONG).show();
    }

    // Cria uma Dialog
    public static void showMsg(final AppCompatActivity app, String title, String msg, String btn){
        final AlertDialog.Builder builder = new AlertDialog.Builder(app);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setPositiveButton(btn, null);
        builder.show();
    }

    // Pega o arquivo da preferencia compartilhada default
    public static SharedPreferences getPreference(Context context){
        SharedPreferences pref = context.getSharedPreferences("settings_default", context.MODE_PRIVATE);
        return pref;
    }

    public static boolean checkLogin(Context context){
        boolean logou = getPreference(context).getBoolean("logado", false);
        return logou;
    }
}
