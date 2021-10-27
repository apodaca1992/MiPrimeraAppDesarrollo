package com.desarrollo.miprimeraapp.screenApp.crudClientes.activities.eliminarCliente;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import com.desarrollo.miprimeraapp.R;
import com.desarrollo.miprimeraapp.databinding.EliminarClienteActivityBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class EliminarClienteActivity extends AppCompatActivity {
    private EliminarClienteActivityBinding binding;
    private EliminarClienteViewModel oEliminarClienteViewModel;
    private long idCliente;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.eliminar_cliente_activity);

        oEliminarClienteViewModel = new EliminarClienteViewModel(getApplicationContext());

        initUI();

        binding.btnEliminar.setOnClickListener(v ->{
            //eliminar cliente
            eliminarCliente();
        });

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            idCliente = extras.getLong("idCliente");

            oEliminarClienteViewModel.detalleCliente(idCliente, getApplicationContext()).observe(this, new Observer<JSONObject>() {
                @Override
                public void onChanged(JSONObject result) {

                    try {
                        binding.clienteEliminarProgress.setVisibility(View.GONE);

                        assert result != null;
                        if (result.getJSONObject("meta").getBoolean("isValid")){
                            JSONObject clienteWS = result.getJSONObject("data").getJSONObject("cliente");

                            binding.editNombre.setText(clienteWS.getString(getResources().getString(R.string.strParametroWSNombre)));
                            binding.editApellidos.setText(clienteWS.getString(getResources().getString(R.string.strParametroWSApellido)));
                            binding.editDireccion.setText(clienteWS.getString(getResources().getString(R.string.strParametroWSDireccion)));
                            binding.editCiudad.setText(clienteWS.getString(getResources().getString(R.string.strParametroWSCiudad)));

                            binding.btnEliminar.setEnabled(true);
                        }else{
                            Toast.makeText(getApplicationContext(), result.getJSONObject("meta").getString("message"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
        }
    }

    public void eliminarCliente(){
        binding.clienteEliminarProgress.setVisibility(View.VISIBLE);

        oEliminarClienteViewModel.eliminarCliente(idCliente, getApplicationContext()).observe(this, new Observer<JSONObject>() {
            @Override
            public void onChanged(JSONObject result) {
                try {
                    binding.clienteEliminarProgress.setVisibility(View.GONE);

                    assert result != null;

                    if (result.getJSONObject("meta").getBoolean("isValid")){
                        Toast.makeText(getApplicationContext(), result.getJSONObject("meta").getString("message"), Toast.LENGTH_LONG).show();
                        onBackPressed();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void initUI(){
        setSupportActionBar(binding.toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        binding.clienteEliminarProgress.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.primary), PorterDuff.Mode.SRC_IN);

        desactivarControles();
    }

    private void desactivarControles(){
        binding.editNombre.setEnabled(false);
        binding.editApellidos.setEnabled(false);
        binding.editDireccion.setEnabled(false);
        binding.editCiudad.setEnabled(false);
        binding.btnEliminar.setEnabled(false);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
