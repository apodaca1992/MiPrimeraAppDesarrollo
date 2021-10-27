package com.desarrollo.miprimeraapp.screenApp.crudClientes.activities.eliminarCliente;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.json.JSONObject;

public class EliminarClienteViewModel extends ViewModel {
    private EliminarClienteModel oEliminarClienteModel;
    private MutableLiveData<JSONObject> resultDetalle;
    private MutableLiveData<JSONObject> resultEliminar;

    public EliminarClienteViewModel(Context context){
        oEliminarClienteModel = new EliminarClienteModel(context);
    }

    public LiveData<JSONObject> detalleCliente(Long id_cliente, Context context){
        if (resultDetalle == null){
            resultDetalle = new MutableLiveData<>();
            oEliminarClienteModel.obtenerCliente(resultDetalle, id_cliente, context);
        }
        return resultDetalle;
    }

    public LiveData<JSONObject> eliminarCliente(Long id_cliente, Context context){
        if (resultEliminar == null){
            resultEliminar = new MutableLiveData<>();
            oEliminarClienteModel.eliminarCliente(resultEliminar, id_cliente, context);
        }
        return resultEliminar;
    }
}
