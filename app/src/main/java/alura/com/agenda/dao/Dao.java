package alura.com.agenda.dao;

import java.util.List;

import alura.com.agenda.model.Aluno;

public interface Dao {
    void salvar(Aluno aluno);
    void editar(Aluno aluno);
    void delete(Aluno aluno);
    List<Aluno> todos();
}
