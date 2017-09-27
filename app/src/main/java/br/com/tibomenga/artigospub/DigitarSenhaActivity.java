package br.com.tibomenga.artigospub;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.goodiebag.pinview.Pinview;

public class DigitarSenhaActivity extends AppCompatActivity {
    Pinview pinview3;
    Button btnLogar, btnEsqueciSenha;
    String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_digitar_senha);

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        password = sharedPrefs.getString("password","");

        pinview3 = (Pinview) findViewById(R.id.pinView3);
        btnLogar = (Button) findViewById(R.id.btnLogar);
        btnEsqueciSenha = (Button) findViewById(R.id.btnEsqueciSenha);
        btnLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = pinview3.getValue().toString();
                if(text.equals(password)){
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(DigitarSenhaActivity.this,"Senha incorreta", Toast.LENGTH_SHORT).show();

                }

            }
        });


        btnEsqueciSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), RecupSenhaActivity.class);
                    startActivity(intent);
                    finish();


            }
        });

    }
}
