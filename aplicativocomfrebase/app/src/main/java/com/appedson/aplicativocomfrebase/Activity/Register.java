package com.appedson.aplicativocomfrebase.Activity;

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

public class Register extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText edt_email_cadastro;
    private EditText edt_pass_cadastro;
    private EditText edt_comfirmar_pass_cadastro;
    private CheckBox check_passview_cadastro;
    private Button btn_cadastro_register;
    private Button btn_login_cadastro;
    private ProgressBar login_progeress_cadastro;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        edt_email_cadastro = findViewById(R.id.edt_email_cadastro);
        edt_pass_cadastro = findViewById(R.id.edt_pass_cadastro);
        edt_comfirmar_pass_cadastro = findViewById(R.id.edt_comfirmar_pass_cadastro);
        check_passview_cadastro = findViewById(R.id.check_passview_cadastro);
        btn_cadastro_register = findViewById(R.id.btn_cadastro_register);
        btn_login_cadastro = findViewById(R.id.btn_login_cadastro);
        login_progeress_cadastro = findViewById(R.id.login_progeress_cadastro);

        check_passview_cadastro.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    edt_pass_cadastro.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    edt_comfirmar_pass_cadastro.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    edt_pass_cadastro.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    edt_comfirmar_pass_cadastro.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
        btn_cadastro_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String registerEmail = edt_email_cadastro.getText().toString();
                String senha = edt_pass_cadastro.getText().toString();
                String comfirmarSenha = edt_comfirmar_pass_cadastro.getText().toString();
                if (!TextUtils.isEmpty(registerEmail) && !TextUtils.isEmpty(senha) && !TextUtils.isEmpty(comfirmarSenha)) {
                    if (senha.equals(comfirmarSenha)) {
                        login_progeress_cadastro.setVisibility(View.VISIBLE);
                        mAuth.createUserWithEmailAndPassword(registerEmail, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    abrirTelaPrincipal();
                                } else {
                                    String error = task.getException().getMessage();
                                    Toast.makeText(Register.this, "" + error, Toast.LENGTH_SHORT).show();
                                    login_progeress_cadastro.setVisibility(View.INVISIBLE);
                                }
                            }
                        });
                    } else {
                        Toast.makeText(Register.this, "A senha deve ser a mesma em ambos os campos ;)", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        btn_login_cadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register.this,login.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void abrirTelaPrincipal() {
        Intent intent = new Intent(Register.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}