package com.appedson.aplicativocomfrebase.Activity;

import static android.view.View.VISIBLE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.appedson.aplicativocomfrebase.MainActivity;
import com.appedson.aplicativocomfrebase.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {
    private EditText edt_email;
    private EditText edt_pass;
    private Button btn_login;
    private Button btn_cadastro;
    private FirebaseAuth mAuth;
    private ProgressBar login_progeress;
    private CheckBox check_passview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        edt_email = findViewById(R.id.edt_email);
        edt_pass = findViewById(R.id.edt_pass);
        btn_login = findViewById(R.id.btn_login);
        btn_cadastro = findViewById(R.id.btn_cadastro);
        login_progeress = findViewById(R.id.login_progeress);
        check_passview = findViewById(R.id.check_passview);


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String login_email = edt_email.getText().toString();
                String login_pass = edt_pass.getText().toString();

                if (!TextUtils.isEmpty(login_email) && !TextUtils.isEmpty(login_pass)) {
                    login_progeress.setVisibility(View.VISIBLE);
                    mAuth.signInWithEmailAndPassword(login_email, login_pass)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        abrirTelaPrincipal();
                                    } else {
                                        String error = task.getException().getMessage();
                                        Toast.makeText(login.this, "" + error, Toast.LENGTH_SHORT).show();
                                        login_progeress.setVisibility(View.INVISIBLE);
                                    }
                                }
                            });
                }
            }
        });

        btn_cadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login.this,Register.class);
                startActivity(intent);
                finish();
            }
        });

        check_passview.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    edt_pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else{
                    edt_pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
    }

    private void abrirTelaPrincipal() {
        Intent intent = new Intent(login.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}