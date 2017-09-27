package br.com.tibomenga.artigospub;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RecupSenhaActivity extends AppCompatActivity {
    EditText editTextNovaSenha, editTextRecupCadas;
    Button btnNovaSenha;
    String recuperar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recup_senha);


        editTextNovaSenha = (EditText) findViewById(R.id.editTextNovaSenha);
        editTextRecupCadas = (EditText) findViewById(R.id.textViewNomeRecup);
        btnNovaSenha = (Button) findViewById(R.id.btnNovaSenha);

        SharedPreferences settings = getSharedPreferences("PREFS",0);
        recuperar = settings.getString("recuperar","");

        btnNovaSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = editTextNovaSenha.getText().toString();
                if(text.equals("")){
                    Toast.makeText(RecupSenhaActivity.this,"Preencha todos os campos!", Toast.LENGTH_SHORT).show();
                }else {
                    if (text.equals(recuperar)) {
                        Intent intent = new Intent(getApplicationContext(), CriarSenhaActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(RecupSenhaActivity.this, "Nome incorreto", Toast.LENGTH_SHORT).show();

                    }

                }

            }
        });
    }
}
