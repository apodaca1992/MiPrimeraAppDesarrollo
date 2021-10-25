package com.desarrollo.miprimeraapp.screenApp.crudClientes.activities.modificarCliente;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.desarrollo.miprimeraapp.models.Clientes;

import org.json.JSONObject;

public class ModificarClienteViewModel extends ViewModel {
    private ModificarClienteModel oModificarClienteModel;
    private MutableLiveData<JSONObject> resultDetalle;
    private MutableLiveData<JSONObject> resultModificar;

    public ModificarClienteViewModel(Context context){
        oModificarClienteModel = new ModificarClienteModel(context);
    }

    public LiveData<JSONObject> detalleCliente(Long id_cliente, Context context){
        if (resultDetalle == null){
            resultDetalle = new MutableLiveData<>();
            oModificarClienteModel.obtenerCliente(resultDetalle, id_cliente, context);
        }
        return resultDetalle;
    }

    public LiveData<JSONObject> modificarCliente(Clientes cliente, Context context){
        if (resultModificar == null){
            resultModificar = new MutableLiveData<>();
            oModificarClienteModel.modificarCliente(resultModificar, cliente, context);
        }
        return resultModificar;
    }


}
