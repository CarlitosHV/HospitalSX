package com.example.hospitalsx.ui.listar;

import androidx.core.content.FileProvider;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hospitalsx.R;
import com.example.hospitalsx.bd.sqlite;

import java.io.File;
import java.util.ArrayList;

public class ListarFragment extends Fragment {

    private ListarViewModel mViewModel;
    ArrayList<String> registros;
    ArrayList<String> imagenes;


    sqlite sqLite;

    public static ListarFragment newInstance() {
        return new ListarFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_listar, container, false);

        ListView list = root.findViewById(R.id.lvlListaPacientes);
        sqLite = new sqlite(getContext());

        sqLite.abrir();
        Cursor cursor = sqLite.getRegistro();
        registros = sqLite.getPacientes(cursor);
        imagenes = sqLite.getImagenes(cursor);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, registros);

        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialogo_paciente, null);
                ((TextView) dialogView.findViewById(R.id.tvdpInfoPaciente)).setText(registros.get(i));

                ImageView ivImagen = dialogView.findViewById(R.id.ivDPFoto);
                cargarImagen(imagenes.get(i), ivImagen);
                AlertDialog.Builder dialogo = new AlertDialog.Builder(getContext());
                dialogo.setTitle("Paciente");
                dialogo.setView(dialogView);
                dialogo.setPositiveButton("Aceptar", null);
                dialogo.show();
            }
        });


        sqLite.cerrar();

        return root;
    }

    public void cargarImagen(String imagen, ImageView iv){
        try {
            File file = new File(imagen);
            Uri uriPhoto = FileProvider.getUriForFile(getContext(), "com.example.HospitalSX", file);
            iv.setImageURI(uriPhoto);
        }catch(Exception ex){
            Toast.makeText(getContext(), "Error al cargar la imagen", Toast.LENGTH_SHORT).show();
            Log.d("Carga de imagen", "Error al cargar imagen" + imagen + "\n Mensaje" + ex.getMessage() +
                    "\n Causa" + ex.getCause());
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ListarViewModel.class);
        // TODO: Use the ViewModel
    }

}