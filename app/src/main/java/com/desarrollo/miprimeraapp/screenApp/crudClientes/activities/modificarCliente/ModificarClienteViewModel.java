package com.desarrollo.miprimeraapp.screenApp.crudClientes.activities.modificarCliente;

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
import com.desarrollo.miprimeraapp.models.Clientes;
import com.desarrollo.miprimeraapp.utilerias.Urls;
import com.desarrollo.miprimeraapp.utilerias.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ModificarClienteViewModel extends ViewModel {
    private VolleySingleton oVolleySingleton;
    private MutableLiveData<JSONObject> resultDetalle = new MutableLiveData<>();
    private MutableLiveData<JSONObject> resultModificar = new MutableLiveData<>();

    public ModificarClienteViewModel(Context context){
        oVolleySingleton = VolleySingleton.getInstance(context);
    }

    public MutableLiveData<JSONObject> getResultDetalle(){
        return this.resultDetalle;
    }

    public MutableLiveData<JSONObject> getResultModificar(){
        return this.resultModificar;
    }

    public void detalleCliente(Long id_cliente, Context context){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.API_URL,
                response -> {
                    try {
                        JSONObject object = new JSONObject(response);
                        resultDetalle.setValue(object);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(context.getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        resultDetalle.setValue(null);
                    }
                },
                error -> {
                    Toast.makeText(context.getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    resultDetalle.setValue(null);

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

    public void modificarCliente(Clientes cliente, Context context){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.API_URL,
                response -> {
                    try {
                        JSONObject object = new JSONObject(response);
                        resultModificar.setValue(object);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(context.getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        resultModificar.setValue(null);
                    }
                },
                error -> {
                    Toast.makeText(context.getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    resultModificar.setValue(null);

                }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put(context.getResources().getString(R.string.strParametroIdentificador), "prueba");
                params.put(context.getResources().getString(R.string.strControl), context.getResources().getString(R.string.strControlUpdate));

                params.put(context.getResources().getString(R.string.strParametroWSClienteId), cliente.getId()+"");
                params.put(context.getResources().getString(R.string.strParametroWSNombre), cliente.getNombres());
                params.put(context.getResources().getString(R.string.strParametroWSApellido), cliente.getApellidos());
                params.put(context.getResources().getString(R.string.strParametroWSDireccion), cliente.getDireccion());
                params.put(context.getResources().getString(R.string.strParametroWSCiudad), cliente.getCiudad());
                return params;
            }
        };

        oVolleySingleton.addToRequestQueue(stringRequest);
    }


}
