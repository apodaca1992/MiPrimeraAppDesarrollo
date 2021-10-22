package com.desarrollo.miprimeraapp.screenApp.crudClientes.activities.altaCliente;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import com.desarrollo.miprimeraapp.R;
import com.desarrollo.miprimeraapp.databinding.AltaClienteActivityBinding;
import com.desarrollo.miprimeraapp.models.Clientes;

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

        oAltaClienteViewModel = new AltaClienteViewModel(getApplicationContext());

        binding.btnGuardar.setOnClickListener(v ->{

            if (binding.editNombre.getText().toString().trim().equals("")){
                binding.editNombre.setError("Nombre es obligatorio");
                binding.editNombre.setHint("Ingrese el nombre del cliente");
                return;
            }

            if (binding.editApellidos.getText().toString().trim().equals("")){
                binding.editApellidos.setError("Apellidos es obligatorio.");
                binding.editApellidos.setHint("Ingrese los apellidos del cliente");
                return;
            }

            Clientes oCliente = new Clientes();
            oCliente.setNombres(binding.editNombre.getText().toString());
            oCliente.setApellidos(binding.editApellidos.getText().toString());
            oCliente.setDireccion(binding.editDireccion.getText().toString());
            oCliente.setCiudad(binding.editCiudad.getText().toString());

            binding.clientesAltaProgress.setVisibility(View.VISIBLE);
            oAltaClienteViewModel.altaCliente(oCliente, getApplicationContext()).observe(this, new Observer<JSONObject>() {
                @Override
                public void onChanged(JSONObject result) {
                    try {
                        binding.clientesAltaProgress.setVisibility(View.GONE);
                        assert result != null;
                        if (result.getJSONObject("meta").getBoolean("isValid")){

                            AlertDialog alertDialog = new AlertDialog.Builder(AltaClienteActivity.this).create();
                            alertDialog.setTitle("AVISO");
                            alertDialog.setMessage("Registro almancenado correctamente");
                            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", ((dialog, which) -> {
                                binding.editNombre.setText("");
                                binding.editNombre.requestFocus();
                                binding.editApellidos.setText("");
                                binding.editDireccion.setText("");
                                binding.editCiudad.setText("");
                                dialog.dismiss();
                            }));
                            alertDialog.show();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
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
