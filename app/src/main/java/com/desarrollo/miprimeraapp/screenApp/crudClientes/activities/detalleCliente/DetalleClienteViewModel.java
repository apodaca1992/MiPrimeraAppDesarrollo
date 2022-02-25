package com.desarrollo.miprimeraapp.screenApp.crudClientes.activities.detalleCliente;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.desarrollo.miprimeraapp.R;
import com.desarrollo.miprimeraapp.utilerias.Urls;
import com.desarrollo.miprimeraapp.utilerias.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DetalleClienteViewModel extends ViewModel {
    private MutableLiveData<JSONObject> cliente = new MutableLiveData<>();
    private VolleySingleton oVolleySingleton;

    public DetalleClienteViewModel(Context context){
        oVolleySingleton = VolleySingleton.getInstance(context);
    }

    public MutableLiveData<JSONObject> getCliente(){
        return this.cliente;
    }

    public void obtenerCliente(Long id_cliente, Context context){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.API_URL,
                response -> {
                    try {
                        JSONObject object = new JSONObject(response);
                        cliente.setValue(object);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(context.getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        cliente.setValue(null);
                    }
                },
                error -> {
                    Toast.makeText(context.getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    cliente.setValue(null);

                }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put(context.getResources().getString(R.string.strParametroIdentificador), "prueba");
                params.put(context.getResources().getString(R.string.strControl), context.getResources().getString(R.string.strControlObtener));
                params.put(context.getResources().getString(R.string.strParametroWSClienteId), id_cliente+"");
                return params;
            }
        };

        oVolleySingleton.addToRequestQueue(stringRequest);

    }
}
