package com.desarrollo.miprimeraapp.screenApp.crudClientes.activities.altaCliente;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.desarrollo.miprimeraapp.R;
import com.desarrollo.miprimeraapp.databinding.AltaClienteActivityBinding;
import com.desarrollo.miprimeraapp.models.Clientes;
import com.desarrollo.miprimeraapp.screenApp.crudClientes.activities.detalleCliente.DetalleClienteViewModel;
import com.desarrollo.miprimeraapp.utilerias.ViewModelFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class AltaClienteActivity extends AppCompatActivity {
    private AltaClienteActivityBinding binding;
    private AltaClienteViewModel oAltaClienteViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.alta_cliente_activity);

        initUI();

        ViewModelFactory clientesViewModelFactory = new ViewModelFactory(getApplicationContext());
        oAltaClienteViewModel = new ViewModelProvider(this, clientesViewModelFactory).get(AltaClienteViewModel.class);

        binding.btnGuardar.setOnClickListener(v ->{

            if (binding.editNombre.getEditText().getText().toString().trim().equals("")){
                binding.editNombre.setError("Nombre es obligatorio");
                binding.editNombre.setHint("Ingrese el nombre del cliente");
                return;
            }

            if (binding.editApellidos.getEditText().getText().toString().trim().equals("")){
                binding.editApellidos.setError("Apellidos es obligatorio.");
                binding.editApellidos.setHint("Ingrese los apellidos del cliente");
                return;
            }

            Clientes oCliente = new Clientes();
            oCliente.setNombres(binding.editNombre.getEditText().getText().toString());
            oCliente.setApellidos(binding.editApellidos.getEditText().getText().toString());
            oCliente.setDireccion(binding.editDireccion.getEditText().getText().toString());
            oCliente.setCiudad(binding.editCiudad.getEditText().getText().toString());

            binding.clientesAltaProgress.setVisibility(View.VISIBLE);
            oAltaClienteViewModel.getResult().observe(this, new Observer<JSONObject>() {
                @Override
                public void onChanged(JSONObject result) {
                    try {
                        binding.clientesAltaProgress.setVisibility(View.GONE);
                        assert result != null;
                        if (result.getJSONObject("meta").getBoolean("isValid")){
                            Toast.makeText(getApplicationContext(), "Registro almancenado correctamente", Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();

                    }
                }
            });

            oAltaClienteViewModel.altaCliente(oCliente, getApplicationContext());
        });
    }

    private void initUI(){
        setSupportActionBar(binding.toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        binding.editNombre.requestFocus();

        binding.clientesAltaProgress.getIndeterminateDrawable().setColorFilter(
                ContextCompat.getColor(getApplicationContext(), R.color.primary),
                PorterDuff.Mode.SRC_IN);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
