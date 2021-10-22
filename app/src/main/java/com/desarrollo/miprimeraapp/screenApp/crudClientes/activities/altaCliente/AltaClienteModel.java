package com.desarrollo.miprimeraapp.screenApp.crudClientes.activities.altaCliente;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

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

public class AltaClienteModel {
    private VolleySingleton oVolleySingleton;
    private JSONObject result = new JSONObject();

    public AltaClienteModel(Context context){
        oVolleySingleton = VolleySingleton.getInstance(context);
    }

    public void guardar(MutableLiveData<JSONObject> prResult, Clientes cliente, Context context){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.API_URL,
                response -> {

                    try {
                        JSONObject object = new JSONObject(response);
                        prResult.setValue(object);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(context.getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        prResult.setValue(null);
                    }

                }, error -> {
                    Toast.makeText(context.getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    prResult.setValue(null);
                }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put(context.getResources().getString(R.string.strParametroIdentificador), "prueba");
                params.put(
                        context.getResources().getString(R.string.strControl),
                        context.getResources().getString(R.string.strControlAlta)
                );

                params.put(
                        context.getResources().getString(R.string.strParametroWSClienteId),
                        ""
                );
                params.put(
                        context.getResources().getString(R.string.strParametroWSNombre),
                        cliente.getNombres()
                );
                params.put(
                        context.getResources().getString(R.string.strParametroWSApellido),
                        cliente.getApellidos()
                );
                params.put(
                        context.getResources().getString(R.string.strParametroWSDireccion),
                        cliente.getDireccion()
                );
                params.put(
                        context.getResources().getString(R.string.strParametroWSCiudad),
                        cliente.getCiudad()
                );

                return params;
            }
        };

        oVolleySingleton.addToRequestQueue(stringRequest);



    }
}
