package com.desarrollo.miprimeraapp.screenApp.crudClientes.activities.modificarCliente;

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

import com.desarrollo.miprimeraapp.R;
import com.desarrollo.miprimeraapp.databinding.ModificarClienteActivityBinding;
import com.desarrollo.miprimeraapp.models.Clientes;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class ModificarClienteActivity extends AppCompatActivity {

    private ModificarClienteActivityBinding binding;
    private ModificarClienteViewModel oModificarClienteViewModel;
    private long idCliente;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.modificar_cliente_activity);

        oModificarClienteViewModel = new ModificarClienteViewModel(getApplicationContext());

        initUI();

        binding.btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateCliente();
            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            idCliente = extras.getLong("idCliente");

            oModificarClienteViewModel.detalleCliente(idCliente, getApplicationContext()).observe(this, new Observer<JSONObject>() {
                @Override
                public void onChanged(JSONObject result) {

                    try {
                        binding.clienteModificarProgress.setVisibility(View.GONE);
                        assert result != null;

                        if (result.getJSONObject("meta").getBoolean("isValid")){
                            JSONObject clienteWS = result.getJSONObject("data").getJSONObject("cliente");

                            binding.editNombre.setText(clienteWS.getString(getResources().getString(R.string.strParametroWSNombre)));
                            binding.editApellidos.setText(clienteWS.getString(getResources().getString(R.string.strParametroWSApellido)));
                            binding.editDireccion.setText(clienteWS.getString(getResources().getString(R.string.strParametroWSDireccion)));
                            binding.editCiudad.setText(clienteWS.getString(getResources().getString(R.string.strParametroWSCiudad)));
                            activarControles();

                        }else{
                            Toast.makeText(getApplicationContext(), result.getJSONObject("meta").getString("message"), Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });


        }

    }

    public void updateCliente(){

        if (binding.editNombre.getText().toString().trim().equals("")){
            binding.editNombre.setError("Nombre es obligatorio.");
            binding.editNombre.setHint("Ingrese el nombre del cliente.");
            return;
        }

        if (binding.editApellidos.getText().toString().trim().equals("")){
            binding.editApellidos.setError("Apellidos es obligatorio.");
            binding.editApellidos.setHint("Ingrese los apellidos del cliente.");
            return;
        }

        Clientes oCliente = new Clientes();
        oCliente.setId(idCliente);
        oCliente.setNombres(binding.editNombre.getText().toString());
        oCliente.setApellidos(binding.editApellidos.getText().toString());
        oCliente.setDireccion(binding.editDireccion.getText().toString());
        oCliente.setCiudad(binding.editCiudad.getText().toString());

        binding.clienteModificarProgress.setVisibility(View.VISIBLE);

        oModificarClienteViewModel.modificarCliente(oCliente, getApplicationContext()).observe(this, new Observer<JSONObject>() {
            @Override
            public void onChanged(JSONObject result) {

                try {
                    binding.clienteModificarProgress.setVisibility(View.GONE);
                    assert result != null;

                    if (result.getJSONObject("meta").getBoolean("isValid")){
                        AlertDialog alertDialog = new AlertDialog.Builder(ModificarClienteActivity.this).create();
                        alertDialog.setTitle("AVISO");
                        alertDialog.setMessage("Registro actualizado correctamente");
                        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", ((dialog, which) -> {
                            dialog.dismiss();
                        }));

                        alertDialog.show();

                    }else{
                        Toast.makeText(getApplicationContext(), result.getJSONObject("meta").getString("message"), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });


    }

    private void initUI(){
        setSupportActionBar(binding.toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        binding.editNombre.requestFocus();
        desactivarControles();

        binding.clienteModificarProgress.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.primary), PorterDuff.Mode.SRC_IN);
    }

    private void desactivarControles(){
        binding.editNombre.setEnabled(false);
        binding.editApellidos.setEnabled(false);
        binding.editDireccion.setEnabled(false);
        binding.editCiudad.setEnabled(false);
    }

    private void activarControles(){
        binding.editNombre.setEnabled(true);
        binding.editApellidos.setEnabled(true);
        binding.editDireccion.setEnabled(true);
        binding.editCiudad.setEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
