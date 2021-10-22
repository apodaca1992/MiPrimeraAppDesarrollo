package com.desarrollo.miprimeraapp.screenApp.crudClientes.activities.altaCliente;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.desarrollo.miprimeraapp.models.Clientes;

import org.json.JSONObject;

public class AltaClienteViewModel extends ViewModel {
    private AltaClienteModel oAltaClienteModel;
    private MutableLiveData<JSONObject> result;

    public AltaClienteViewModel(Context context){
        oAltaClienteModel = new AltaClienteModel(context);
    }

    public LiveData<JSONObject> altaCliente(Clientes cliente, Context context){
        if (result == null){
            result = new MutableLiveData<>();
            oAltaClienteModel.guardar(result, cliente, context);
        }
        return result;
    }
}
