package com.example.appproyectofinal;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterDatos extends RecyclerView.Adapter<AdapterDatos.DatosHolder> {
    private List<Datos> Datos;

    public AdapterDatos(List<Datos> datos) {
        Datos = datos;
    }

    @NonNull
    @Override
    public AdapterDatos.DatosHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_datos, parent, false);
        return new DatosHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDatos.DatosHolder holder, int position) {
        holder.addData(Datos.get(position));
    }

    @Override
    public int getItemCount() {
        return Datos.size();
    }

    public class DatosHolder extends RecyclerView.ViewHolder {
        private TextView fecha, estado;
        public DatosHolder(@NonNull View itemView) {
            super(itemView);
            fecha = itemView.findViewById(R.id.fecha);
            estado = itemView.findViewById(R.id.estado);
        }
        public void addData(Datos dato) {
            fecha.setText(dato.getFecha());
            estado.setText(dato.getEstado());
        }
    }
}
