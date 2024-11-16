package com.example.loginbl;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class CultivoAdapter extends RecyclerView.Adapter<CultivoAdapter.CultivoViewHolder> {

    private List<Cultivo> cultivoList;
    private OnCultivoClickListener listener;

    public CultivoAdapter(List<Cultivo> cultivoList, OnCultivoClickListener listener) {
        this.cultivoList = cultivoList;
        this.listener = listener;
    }

    public void setCultivos(List<Cultivo> cultivoList) {
        this.cultivoList = cultivoList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CultivoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cultivo, parent, false);
        return new CultivoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CultivoViewHolder holder, int position) {
        Cultivo cultivo = cultivoList.get(position);
        holder.nombreTextView.setText(cultivo.getNombre());
        holder.cantidadTextView.setText("Cantidad: " + cultivo.getCantidad());

        // Verificar si fecha no es nulo antes de acceder a toDate()
        if (cultivo.getFecha() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            holder.fechaTextView.setText("Fecha: " + sdf.format(cultivo.getFecha().toDate()));
        } else {
            // Establece un valor predeterminado si fecha es nulo
            holder.fechaTextView.setText("Fecha: No disponible");
        }

        holder.editButton.setOnClickListener(v -> listener.onEditClick(cultivo));
        holder.deleteButton.setOnClickListener(v -> listener.onDeleteClick(cultivo));
    }


    @Override
    public int getItemCount() {
        return cultivoList.size();
    }

    public static class CultivoViewHolder extends RecyclerView.ViewHolder {
        TextView nombreTextView, cantidadTextView, fechaTextView;
        Button editButton, deleteButton;

        public CultivoViewHolder(@NonNull View itemView) {
            super(itemView);
            nombreTextView = itemView.findViewById(R.id.cultivoNombre);
            cantidadTextView = itemView.findViewById(R.id.cultivoCantidad);
            fechaTextView = itemView.findViewById(R.id.cultivoFecha);
            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }

    public interface OnCultivoClickListener {
        boolean onCreateOptionsMenu(Menu menu);


        @SuppressLint("NonConstantResourceId")
        boolean onOptionsItemSelected(@NonNull MenuItem item);

        void onEditClick(Cultivo cultivo);
        void onDeleteClick(Cultivo cultivo);
    }
}
