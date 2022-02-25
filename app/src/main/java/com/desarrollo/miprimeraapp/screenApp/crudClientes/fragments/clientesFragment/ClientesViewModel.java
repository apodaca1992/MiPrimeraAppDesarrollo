package com.desarrollo.miprimeraapp.screenApp.crudClientes.fragments.clientesFragment;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientesViewModel extends ViewModel {
    private MutableLiveData<List<Clientes>>  lstClientes = new MutableLiveData<>();
    private VolleySingleton oVolleySingleton;

    public ClientesViewModel(Context context){
        oVolleySingleton = VolleySingleton.getInstance(context);
    }

    public void getData(Context context){
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
                            lstClientes.setValue(clientesList);

                        }else{
                            Toast.makeText(context, object.getJSONObject("meta").getString("message"), Toast.LENGTH_LONG).show();
                            lstClientes.setValue(clientesList);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        lstClientes.setValue(clientesList);
                    }

                },
                error -> {
                    Toast.makeText(context, "Se ha generado un error.", Toast.LENGTH_LONG).show();
                    lstClientes.setValue(clientesList);
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

    public MutableLiveData<List<Clientes>> getLstClientes(){
        return this.lstClientes;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
