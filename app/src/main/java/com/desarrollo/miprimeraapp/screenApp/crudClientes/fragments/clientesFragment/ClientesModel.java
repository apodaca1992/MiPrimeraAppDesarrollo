package com.desarrollo.miprimeraapp.screenApp.crudClientes.fragments.clientesFragment;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientesModel {
    private VolleySingleton oVolleySingleton;

    public ClientesModel(Context context){
        oVolleySingleton = VolleySingleton.getInstance(context);
    }

    public void loadData(final Context context, MutableLiveData<List<Clientes>> newsList){
        List<Clientes> clientesList = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.API_URL,
                response -> {

                    try {
                        JSONObject object = new JSONObject(response);

                        if (object.getJSONObject("meta").getBoolean("isValid")){

                            JSONArray clientes =object.getJSONObject("data").getJSONArray("clientes");

                            for (int i=0; i< clientes.length(); i++){
                                JSONObject item = clientes.getJSONObject(i);
                                Clientes clientes1 = new Clientes(item.getLong("id_cliente"),
                                        item.getString("nombre"),
                                        item.getString("apellido"),
                                        item.getString("direccion"),
                                        item.getString("ciudad"));
                                clientesList.add(clientes1);
                            }
                            newsList.setValue(clientesList);

                        }else{
                            Toast.makeText(context, object.getJSONObject("meta").getString("message"), Toast.LENGTH_LONG).show();
                            newsList.setValue(clientesList);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        newsList.setValue(clientesList);
                    }

                },
                error -> {
                    Toast.makeText(context, "Se ha generado un error.", Toast.LENGTH_LONG).show();
                    newsList.setValue(clientesList);
                }){

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put(context.getResources().getString(R.string.strParametroIdentificador), "prueba");
                params.put(context.getResources().getString(R.string.strControl), context.getResources().getString(R.string.strControlListado));
                return params;
            }
        };

        oVolleySingleton.addToRequestQueue(stringRequest);
    }

}
