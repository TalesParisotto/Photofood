package com.photofood.tcc.projeto.photofood.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.photofood.tcc.projeto.photofood.R;

public class RedefinirSenhaActivity extends AppCompatActivity {

    private EditText editEmailRedefinir;
    private Button buttonRedefinir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redefinir_senha);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editEmailRedefinir = findViewById(R.id.editEmailRedefinir);
        buttonRedefinir = findViewById(R.id.buttonRedefinir);

        buttonRedefinir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String textoEmail = editEmailRedefinir.getText().toString();

                if ( !textoEmail.isEmpty() ){
                    View view = null;
                    enviarEmailRedefinirSenha(view);
                }else {
                    Toast.makeText(RedefinirSenhaActivity.this,
                            "Preencha o email!",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void enviarEmailRedefinirSenha(View view){
        FirebaseAuth auth = FirebaseAuth.getInstance();

        auth.sendPasswordResetEmail(editEmailRedefinir.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(RedefinirSenhaActivity.this,
                                    "Email enviado para redefinição de senha.",
                                    Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(RedefinirSenhaActivity.this,
                                    "Erro ao enviar email.",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}