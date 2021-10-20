package com.desarrollo.miprimeraapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.desarrollo.miprimeraapp.R;
import com.desarrollo.miprimeraapp.models.Clientes;

import java.util.ArrayList;
import java.util.List;

public class ClientesAdapter extends RecyclerView.Adapter<ClientesAdapter.ClienteViewHolder> {
    private List<Clientes> clientesList;
    private Context context;
    private onItemClickListener listener;

    public interface onItemClickListener{
        void onItemClick(Clientes item, View view);
    }

    public ClientesAdapter(Context context, ArrayList<Clientes> list){
        this.context = context;
        this.clientesList = list;
    }

    @NonNull
    @Override
    public ClienteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_listado_clientes, parent, false);
        return new ClienteViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ClienteViewHolder holder, int position) {
        Clientes clientes = clientesList.get(position);
        holder.txtClienteName.setText(String.format("%s %s", clientes.getNombres(), clientes.getApellidos()));
    }

    @Override
    public int getItemCount() {
        return clientesList.size();
    }

    public class ClienteViewHolder extends RecyclerView.ViewHolder{
        public TextView txtClienteName;

        public ClienteViewHolder(View itemView, final ClientesAdapter.onItemClickListener listener){
            super(itemView);
            txtClienteName = itemView.findViewById(R.id.cliente_name);
        }

    }
}
