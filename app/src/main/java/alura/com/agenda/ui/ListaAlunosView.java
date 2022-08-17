package alura.com.agenda.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.widget.AdapterView;

import alura.com.agenda.R;
import alura.com.agenda.dao.AlunoDAO;
import alura.com.agenda.model.Aluno;
import alura.com.agenda.ui.adapter.ListaAlunosAdapter;

public class ListaAlunosView {

    private final ListaAlunosAdapter adapter;
    private final AlunoDAO dao;
    private final Context context;

    public ListaAlunosView(Context context) {
        this.context = context;
        adapter = new ListaAlunosAdapter(this.context);
        dao = new AlunoDAO();

    }

    public void confirmaRemocao(AdapterView.AdapterContextMenuInfo menuInfo) {
        new AlertDialog.Builder(context)
                .setTitle("Tem certeza que deseja excluir o aluno?")
                .setPositiveButton("Sim", (dialog, which) -> {
                    Aluno alunoEscolhido = (Aluno) adapter.getItem(menuInfo.position);
                    remove(alunoEscolhido);
                })
                .setNegativeButton("Não", null)
                .show();
    }

    public void remove(Aluno alunoSelecionado) {
        dao.delete(alunoSelecionado);
        adapter.remove(alunoSelecionado);
    }


    public void atualizaAlunos() {
        adapter.atualiza(dao.todos());
    }





}
