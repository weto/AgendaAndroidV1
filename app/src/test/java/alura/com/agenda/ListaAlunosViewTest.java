package alura.com.agenda;

import static org.junit.Assert.assertEquals;

import android.content.Context;

import org.junit.Test;

import alura.com.agenda.dao.AlunoDAO;
import alura.com.agenda.model.Aluno;
import alura.com.agenda.ui.ListaAlunosView;
import alura.com.agenda.ui.adapter.ListaAlunosAdapter;

public class ListaAlunosViewTest {
    private Context context;

    @Test
    public void testAtualizaAlunosVazia() {
        AlunoDAO dao = new AlunoDAO();
        ListaAlunosAdapter adapter = new ListaAlunosAdapter(context);
        ListaAlunosView listaAlunosView = new ListaAlunosView(context, dao, adapter);
        listaAlunosView.atualizaAlunos();
        assertEquals(dao.todos().size(), 0);
    }

    @Test
    public void testAtualizaAlunosComDados() {
        AlunoDAO dao = new AlunoDAO();
        dao.salvar(new Aluno("Teste 1","(31)99849-3210","teste@teste.com"));
        ListaAlunosAdapter adapter = new ListaAlunosAdapter(context);
        ListaAlunosView listaAlunosView = new ListaAlunosView(context, dao, adapter);
        listaAlunosView.atualizaAlunos();
        assertEquals(dao.todos().size(), 1);
    }
}