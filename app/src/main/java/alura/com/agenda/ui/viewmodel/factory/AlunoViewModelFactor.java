package alura.com.agenda.ui.viewmodel.factory;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import alura.com.agenda.repository.AlunoRepository;
import alura.com.agenda.ui.viewmodel.AlunoViewModel;

public class AlunoViewModelFactor implements ViewModelProvider.Factory{
    private AlunoRepository repository;

    public AlunoViewModelFactor(AlunoRepository repository) {
        Log.i("Contruiu Fabrica", "AlunoViewModelTesteFactor: ");
        this.repository = repository;
    }

    @SuppressLint("LongLogTag")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        Log.i("AlunoViewModelTesteFactor", "create: ");
        return (T) new AlunoViewModel(repository);
    }
}
