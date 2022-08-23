package alura.com.agenda.ui.activity;

import static android.widget.Toast.LENGTH_SHORT;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

import alura.com.agenda.R;
import alura.com.agenda.model.Aluno;

public class FormularioLoginActivity extends AppCompatActivity implements Constantes {
    private EditText campoLogin;
    private EditText campoSenha;

    private String txtCampoLogin;
    private String txtCampoSenha;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        setTitle(TITULO_APPBAR_LOGIN);

        firebaseAuth = FirebaseAuth.getInstance();

        Button botaoLogar = findViewById(R.id.activity_login_logar);
        botaoLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                campoLogin = findViewById(R.id.activity_login_email);
                campoSenha = findViewById(R.id.activity_login_senha);

                txtCampoLogin = campoLogin.getText().toString();
                txtCampoSenha = campoSenha.getText().toString();

                if(!txtCampoLogin.isEmpty() || !txtCampoSenha.isEmpty()) {
                    autenticar(view);
                } else {
                    Snackbar.make(view, "Dados informados inválidos", Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        Button botaoCadastrar = findViewById(R.id.activity_login_cadastrar);
        botaoCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                campoLogin = findViewById(R.id.activity_login_email);
                campoSenha = findViewById(R.id.activity_login_senha);
                Log.i("TESTEEE", "onClick: "+campoLogin.getText().toString());

                txtCampoLogin = campoLogin.getText().toString();
                txtCampoSenha = campoSenha.getText().toString();
                    cadastraAluno(view);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        redireciona();
    }

    private void redireciona() {
        if(firebaseAuth.getCurrentUser() != null) {
            FirebaseUser currentUser = firebaseAuth.getCurrentUser();
            if(currentUser != null) {
                startActivity(new Intent(FormularioLoginActivity.this, ListaAlunosActivity.class));
                Toast.makeText(FormularioLoginActivity.this, "Já estou logado "+currentUser.getEmail(), LENGTH_SHORT).show();
            }
        }
    }

    private void autenticar(View v) {
        try {
            firebaseAuth.signInWithEmailAndPassword(txtCampoLogin, txtCampoSenha)
                    .addOnCompleteListener((tarefa) -> {
                        if(tarefa.isSuccessful()) {
                            Log.i("Login feito", "onClick: ");
                            startActivity(new Intent(FormularioLoginActivity.this, ListaAlunosActivity.class));
                            Toast.makeText(FormularioLoginActivity.this, "Autenticado com sucesso", LENGTH_SHORT).show();
                        } else {
                            String mensagemErro = "";
                            if (tarefa.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                mensagemErro = "Senha precisa de no minimo 6 digitos";
                            } else if (tarefa.getException() instanceof FirebaseAuthInvalidUserException) {
                                mensagemErro = "Email inválido ou desabilitado";
                            } else if (tarefa.getException() instanceof FirebaseAuthUserCollisionException) {
                                mensagemErro = "Email já cadastrado";
                            } else {
                                mensagemErro = "Erro desconhecido";
                            }

                            new AlertDialog.Builder(this)
                                    .setTitle("Cadastro não encontrado. Deseja fazer o cadastro?")
                                    .setPositiveButton("Sim", (dialog, which) -> {
                                        cadastraAluno(v);
                                    })
                                    .setNegativeButton("Não", null)
                                    .show();

                            Toast.makeText(FormularioLoginActivity.this, mensagemErro, LENGTH_SHORT).show();
                        }
                    });
        } catch (IllegalArgumentException erro) {
            Snackbar.make(v, "Email ou senha não pode ser vazio", Snackbar.LENGTH_SHORT).show();
        }
    }

    private void cadastraAluno(View view) {
        String txtCampoLogin = campoLogin.getText().toString();
        String txtCampoSenha = campoSenha.getText().toString();

        try {
            Task<AuthResult> userWithEmailAndPassword = firebaseAuth.createUserWithEmailAndPassword(txtCampoLogin, txtCampoSenha);
            userWithEmailAndPassword.addOnSuccessListener((results) -> {
                autenticar(view);
            });

            userWithEmailAndPassword.addOnFailureListener((falha) -> {
                String mensagemErro = "";
                if (falha instanceof FirebaseAuthInvalidCredentialsException) {
                    mensagemErro = "Senha precisa de no minimo 6 digitos";
                } else if (falha instanceof FirebaseAuthInvalidUserException) {
                    mensagemErro = "Email inválido";
                } else if (falha instanceof FirebaseAuthUserCollisionException) {
                    mensagemErro = "Email já cadastrado";
                } else {
                    mensagemErro = "Erro desconhecido";
                }
                Snackbar.make(view,
                        mensagemErro,
                        Snackbar.LENGTH_SHORT).show();
            });
        } catch (IllegalArgumentException erro) {
            Snackbar.make(view,
                    "Email ou senha não pode ser vazio",
                    Snackbar.LENGTH_SHORT).show();

        }
    }
}