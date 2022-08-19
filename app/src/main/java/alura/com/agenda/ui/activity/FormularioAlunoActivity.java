package alura.com.agenda.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import alura.com.agenda.R;
import alura.com.agenda.model.Aluno;
import alura.com.agenda.repository.AlunoRepository;
import alura.com.agenda.ui.viewmodel.AlunoViewModel;
import alura.com.agenda.ui.viewmodel.factory.AlunoViewModelFactor;

public class FormularioAlunoActivity extends AppCompatActivity implements Constantes {
    private EditText campoNome;
    private EditText campoTelefone;
    private EditText campoEmail;

    private AlunoViewModel provedor;
    private AlunoViewModelFactor factory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_aluno);
        inicializacaoCampos();
    }

    private void inicializacaoCampos() {
        campoNome = findViewById(R.id.activity_formulario_aluno_nome);
        campoTelefone = findViewById(R.id.activity_formulario_aluno_telefone);
        campoEmail = findViewById(R.id.activity_formulario_aluno_email);
        this.factory = new AlunoViewModelFactor(new AlunoRepository(this));
        this.provedor = new ViewModelProvider(this, factory).get(AlunoViewModel.class);
        configuraTela();
    }

    private void configuraTela() {
        Intent dados = getIntent();
        Aluno aluno = (Aluno) dados.getSerializableExtra(CHAVE_ALUNO);
        provedor.setAluno(aluno);
        if(provedor.getAluno() != null) {
            setTitle(TITULO_APPBAR_EDICAO);
            campoNome.setText(aluno.getNome());
            campoTelefone.setText(aluno.getTelefone());
            campoEmail.setText(aluno.getEmail());
        } else {
            setTitle(TITULO_APPBAR_CADASTRO);
        }
    }

    private void criaEditaAluno() {
        String nome = campoNome.getText().toString();
        String telefone = campoTelefone.getText().toString();
        String email = campoEmail.getText().toString();
        configuraAluno(nome, telefone, email);
        provedor.salvaAluno();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if(itemId == R.id.activity_formulario_aluno_menu_salva) {
            criaEditaAluno();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_formulario_aluno_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void configuraAluno(String nome, String telefone, String email) {
        if(provedor.getAluno() == null) {
            provedor.setAluno(new Aluno());
        }
        provedor.getAluno().setNome(nome);
        provedor.getAluno().setTelefone(telefone);
        provedor.getAluno().setEmail(email);
    }
}