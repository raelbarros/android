package br.senac.lojaandroid.util;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;

import br.senac.lojaandroid.view.CarrinhoActivity;

public class Util {

    // URI image user
    public static Uri getImageURIUser(String img){
        Uri teste =  Uri.parse("/storage/emulated/0/" + img);
        System.out.println(teste);
        return teste;
    }


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
        SharedPreferences pref = context.getSharedPreferences("settingsDefault", context.MODE_PRIVATE);
        return pref;
    }

    public static boolean checkLogin(Context context){
        return getPreference(context).getBoolean("isLogado", false);
    }

    public static int getCurrentUser(Context context){
        return getPreference(context).getInt("currentUser", 0);
    }
}

//Pega a Instancia do DB
//LojaDatabase appDB = LojaDatabase.getInstance(LoginActivity.this);
//Salva no SQLite
//appDB.clienteDao().insertCliente(clientResp);
//Pega do SQLite
//Cliente clienteDB = appDB.clienteDao().clienteById(clientResp.getIdCliente());
//showDialog(clienteDB.getNomeCompletoCliente(), "Logou");