package com.desarrollo.miprimeraapp.screenApp.crudClientes.fragments.clientesFragment;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
    }
}
