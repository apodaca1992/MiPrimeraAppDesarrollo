package com.desarrollo.miprimeraapp.utilerias;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.desarrollo.miprimeraapp.screenApp.crudClientes.activities.altaCliente.AltaClienteViewModel;
import com.desarrollo.miprimeraapp.screenApp.crudClientes.activities.detalleCliente.DetalleClienteViewModel;
import com.desarrollo.miprimeraapp.screenApp.crudClientes.activities.eliminarCliente.EliminarClienteViewModel;
import com.desarrollo.miprimeraapp.screenApp.crudClientes.activities.modificarCliente.ModificarClienteViewModel;
import com.desarrollo.miprimeraapp.screenApp.crudClientes.fragments.clientesFragment.ClientesViewModel;

public class ViewModelFactory implements ViewModelProvider.Factory {
    private Context context;

    public ViewModelFactory(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ClientesViewModel.class)) {
            return (T) new ClientesViewModel(context);
        }else if (modelClass.isAssignableFrom(DetalleClienteViewModel.class)) {
            return (T) new DetalleClienteViewModel(context);
        }else if (modelClass.isAssignableFrom(EliminarClienteViewModel.class)) {
            return (T) new EliminarClienteViewModel(context);
        }else if (modelClass.isAssignableFrom(AltaClienteViewModel.class)) {
            return (T) new AltaClienteViewModel(context);
        }else if (modelClass.isAssignableFrom(ModificarClienteViewModel.class)) {
            return (T) new ModificarClienteViewModel(context);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}