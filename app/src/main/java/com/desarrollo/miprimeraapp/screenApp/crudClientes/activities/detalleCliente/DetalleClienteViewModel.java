package com.desarrollo.miprimeraapp.screenApp.crudClientes.activities.detalleCliente;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import org.json.JSONObject;

public class DetalleClienteViewModel extends ViewModel {
    private DetalleClienteModel oDetalleClienteModel;
    private MutableLiveData<JSONObject> result;

    public DetalleClienteViewModel(Context context){
        oDetalleClienteModel = new DetalleClienteModel(context);
    }

    public LiveData<JSONObject> detalleCliente(Long id_cliente, Context context){
        if (result == null){
            result = new MutableLiveData<>();
            oDetalleClienteModel.obtenerCliente(result, id_cliente, context);
        }
        return result;
    }
}
