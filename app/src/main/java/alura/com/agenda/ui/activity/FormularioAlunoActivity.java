package alura.com.agenda.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import alura.com.agenda.R;
import alura.com.agenda.dao.AlunoDAO;
import alura.com.agenda.model.Aluno;

public class FormularioAlunoActivity extends AppCompatActivity implements Constantes {
    private Aluno aluno;
    private EditText campoNome;
    private EditText campoTelefone;
    private EditText campoEmail;
    final AlunoDAO dao = new AlunoDAO();

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
        carregaDadosAlunoExistente();
    }

    private void carregaDadosAlunoExistente() {
        Intent dados = getIntent();
        aluno = (Aluno) dados.getSerializableExtra(CHAVE_ALUNO);
        if(aluno != null) {
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
        alunoExiste();
        aluno.setNome(nome);
        aluno.setTelefone(telefone);
        aluno.setEmail(email);
        salvaAluno();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if(itemId == R.id.activity_formulario_aluno_menu_salva) {
            criaEditaAluno();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_formulario_aluno_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void alunoExiste() {
        aluno = aluno == null ? new Aluno(): aluno;
    }

    private void salvaAluno() {
        if(aluno.getId() == null) {
            dao.salvar(aluno);
        } else {
            dao.editar(aluno);
        }
        finish();
    }
}