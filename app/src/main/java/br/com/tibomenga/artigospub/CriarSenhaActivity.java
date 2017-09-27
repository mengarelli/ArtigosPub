package br.com.tibomenga.artigospub;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.goodiebag.pinview.Pinview;

public class CriarSenhaActivity extends AppCompatActivity {
    Pinview pinview1, pinview2;
    EditText nomeRecuperar;
    Button btnConfrimar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_senha);

        pinview1 = (Pinview)findViewById(R.id.pinView1);
        pinview2 = (Pinview)findViewById(R.id.pinView2);
        nomeRecuperar = (EditText) findViewById(R.id.editTextNomeRecuperar);
        btnConfrimar = (Button) findViewById(R.id.btnConfirmarSenha);


        btnConfrimar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text1 = pinview1.getValue().toString();
                String text2 = pinview2.getValue().toString();
                String text3 = nomeRecuperar.getText().toString();
                if(text1.equals("") || text2.equals("") || text3.equals("")){
                    Toast.makeText(CriarSenhaActivity.this,"Preencha todos os campos!", Toast.LENGTH_SHORT).show();

                }else{
                    if(text1.equals(text2) ){
                        //SharedPreferences settings = getSharedPreferences("PREFS", 0 );
                        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(CriarSenhaActivity.this);
                        SharedPreferences.Editor editor = sharedPrefs.edit();
                        editor.putString("password",text1);
                        editor.putString("recuperar",text3);
                        editor.apply();

                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();

                    }else{
                        Toast.makeText(CriarSenhaActivity.this,"As senhas não são iguais!",Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });


    }
}
