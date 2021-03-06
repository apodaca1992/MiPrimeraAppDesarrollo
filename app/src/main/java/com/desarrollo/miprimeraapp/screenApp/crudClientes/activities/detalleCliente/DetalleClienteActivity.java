package com.desarrollo.miprimeraapp.screenApp.crudClientes.activities.detalleCliente;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.desarrollo.miprimeraapp.R;
import com.desarrollo.miprimeraapp.databinding.DetalleClienteActivityBinding;
import com.desarrollo.miprimeraapp.utilerias.ViewModelFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class DetalleClienteActivity extends AppCompatActivity {
    private DetalleClienteActivityBinding binding;
    private DetalleClienteViewModel oDetalleClienteViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.detalle_cliente_activity);

        ViewModelFactory clientesViewModelFactory = new ViewModelFactory(getApplicationContext());
        oDetalleClienteViewModel = new ViewModelProvider(this, clientesViewModelFactory).get(DetalleClienteViewModel.class);

        initUI();

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            long idCliente = extras.getLong("idCliente");

            oDetalleClienteViewModel.getCliente().observe(this, new Observer<JSONObject>() {
                @Override
                public void onChanged(JSONObject result) {

                    try {
                        binding.clienteDetalleProgress.setVisibility(View.GONE);

                        assert result != null;
                        if (result.getJSONObject("meta").getBoolean("isValid")){
                            JSONObject clienteWS = result.getJSONObject("data").getJSONObject("cliente");

                            binding.editNombre.getEditText().setText(clienteWS.getString(getResources().getString(R.string.strParametroWSNombre)));
                            binding.editApellidos.getEditText().setText(clienteWS.getString(getResources().getString(R.string.strParametroWSApellido)));
                            binding.editDireccion.getEditText().setText(clienteWS.getString(getResources().getString(R.string.strParametroWSDireccion)));
                            binding.editCiudad.getEditText().setText(clienteWS.getString(getResources().getString(R.string.strParametroWSCiudad)));
                        }else{
                            Toast.makeText(getApplicationContext(), result.getJSONObject("meta").getString("message"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });

            oDetalleClienteViewModel.obtenerCliente(idCliente, getApplicationContext());

        }

    }

    private void initUI(){

        setSupportActionBar(binding.toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        desabilitarControles();

        binding.clienteDetalleProgress.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.primary), PorterDuff.Mode.SRC_IN);

    }

    private void desabilitarControles(){
        binding.editNombre.setEnabled(false);
        binding.editApellidos.setEnabled(false);
        binding.editDireccion.setEnabled(false);
        binding.editCiudad.setEnabled(false);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
