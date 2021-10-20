package com.desarrollo.miprimeraapp.screenApp.crudClientes.fragments.clientesFragment;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.desarrollo.miprimeraapp.models.Clientes;

import java.util.List;

public class ClientesViewModel extends ViewModel {
    private ClientesModel oClientesModel;
    private MutableLiveData<List<Clientes>>  newsList;

    public ClientesViewModel(Context context){
        oClientesModel = new ClientesModel(context);
    }

    public LiveData<List<Clientes>> getData(Context context){
        newsList = new MutableLiveData<>();
        oClientesModel.loadData(context, newsList);
        return newsList;
    }
}
