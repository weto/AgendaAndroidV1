package alura.com.agenda.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import alura.com.agenda.model.Aluno;

public class AlunoDAO implements Dao {

    private static final List<Aluno> alunos = new ArrayList<>();

    public void salvar(Aluno aluno) {
        UUID uuid = UUID.randomUUID();
        aluno.setId(uuid);
        alunos.add(aluno);
    }

    @Override
    public void editar(Aluno aluno) {
        for (Aluno a: alunos) {
            if(a.getId().equals(aluno.getId())) {
                alunos.set(alunos.indexOf(a), aluno);
                break;
            }
        }
    }

    @Override
    public void delete(Aluno aluno) {
        for (Aluno a: alunos) {
            if(a.getId().equals(aluno.getId())) {
                alunos.remove(alunos.indexOf(a));
                break;
            }
        }
    }

    public List<Aluno> todos() { return new ArrayList<>(alunos); }
}