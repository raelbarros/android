package br.senac.lojaandroid.util;

import android.app.AlertDialog;
import android.app.Application;
import android.content.DialogInterface;
import android.content.Intent;
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
    public static void showMsg(final AppCompatActivity app, String title, String msg){
        final AlertDialog.Builder builder = new AlertDialog.Builder(app);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setPositiveButton("Ir para o Carrinho", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(app, CarrinhoActivity.class);
                app.startActivity(intent);
            }
        });
        builder.setNegativeButton("Continuar Comprando", null);
        builder.show();
    }
}
