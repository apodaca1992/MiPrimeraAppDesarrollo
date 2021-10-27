package com.desarrollo.miprimeraapp.screenApp.crudClientes.fragments.clientesFragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.desarrollo.miprimeraapp.R;
import com.desarrollo.miprimeraapp.adapter.ClientesAdapter;
import com.desarrollo.miprimeraapp.databinding.ClientesFragmentBinding;
import com.desarrollo.miprimeraapp.models.Clientes;
import com.desarrollo.miprimeraapp.screenApp.crudClientes.activities.altaCliente.AltaClienteActivity;
import com.desarrollo.miprimeraapp.screenApp.crudClientes.activities.detalleCliente.DetalleClienteActivity;
import com.desarrollo.miprimeraapp.screenApp.crudClientes.activities.eliminarCliente.EliminarClienteActivity;
import com.desarrollo.miprimeraapp.screenApp.crudClientes.activities.modificarCliente.ModificarClienteActivity;
import com.desarrollo.miprimeraapp.utilerias.Urls;

import java.util.ArrayList;
import java.util.List;

public class ClientesFragment extends Fragment {
    private ClientesFragmentBinding binding;
    private ClientesAdapter adapter;
    private ClientesViewModel clientesViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.clientes_fragment, container, false);

        initUI();

        clientesViewModel = new ClientesViewModel(getActivity());

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        binding.clientesProgress.setVisibility(View.VISIBLE);
        clientesViewModel.getData(getActivity()).observe(getViewLifecycleOwner(), new Observer<List<Clientes>>() {
            @Override
            public void onChanged(List<Clientes> clientesList) {
                binding.clientesProgress.setVisibility(View.GONE);
                setUpRecyclerView(clientesList);
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main_clientes, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_alta_cliente){
            Intent addCliente = new Intent(getContext(), AltaClienteActivity.class);
            startActivity(addCliente);
        }
        return super.onOptionsItemSelected(item);
    }

    private void initUI(){
        binding.rcvClientes.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        binding.rcvClientes.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(requireContext(), layoutManager.getOrientation());
        binding.rcvClientes.addItemDecoration(dividerItemDecoration);

        binding.clientesProgress.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(requireContext(), R.color.primary), PorterDuff.Mode.SRC_IN);

    }

    private void setUpRecyclerView(final List<Clientes> oClientes){
        adapter = new ClientesAdapter(getContext(), (ArrayList<Clientes>) oClientes);
        binding.rcvClientes.setAdapter(adapter);
        adapter.setItemClickListener((cliente, view) -> {
            showPopup(cliente, view);
        });
    }

    @SuppressLint("RestrictedApi")
    public void showPopup(Clientes cliente, View v){
        PopupMenu popup = new PopupMenu(requireContext(), v);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_modificar:
                        Intent intent = new Intent(getContext(), ModificarClienteActivity.class);
                        intent.putExtra("idCliente", cliente.getId());
                        startActivity(intent);
                        return true;
                    case R.id.action_eliminar:
                        intent = new Intent(getContext(), EliminarClienteActivity.class);
                        intent.putExtra("idCliente", cliente.getId());
                        startActivity(intent);
                        return true;
                    case R.id.action_ver_detalle:
                        intent = new Intent(getContext(), DetalleClienteActivity.class);
                        intent.putExtra("idCliente", cliente.getId());
                        startActivity(intent);
                        return true;
                    default:
                        return false;
                }
            }
        });

        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_opciones_clientes, popup.getMenu());
        Menu menu = popup.getMenu();
        if (menu instanceof MenuBuilder){
            MenuBuilder menuBuilder = (MenuBuilder) menu;
            menuBuilder.setOptionalIconsVisible(true);
        }
        popup.show();
    }
}
