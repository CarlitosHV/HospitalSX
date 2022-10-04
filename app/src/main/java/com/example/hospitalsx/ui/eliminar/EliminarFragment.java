package com.example.hospitalsx.ui.eliminar;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.hospitalsx.R;
import com.example.hospitalsx.bd.sqlite;
import com.example.hospitalsx.databinding.FragmentEliminarBinding;

import java.io.File;
import java.util.ArrayList;


public class EliminarFragment extends Fragment implements  View.OnClickListener, AdapterView.OnItemSelectedListener, DatePickerDialog.OnDateSetListener{

    private FragmentEliminarBinding binding;
    ArrayList<String> imagenes;
    ArrayList<String> registros;
    public sqlite sqlite;
    private Button btnLimpiar;
    private Button btnGuardar;
    private EditText etID;
    private TextView tvnombre, tvfecha, tvedad, tvestatura, tvpeso, tvarea, tvdoctor, tvgenero;
    private ImageView ivfoto;
    private String img="";
    public static int band=0, idp;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        EliminarModel eliminarModel =
                new ViewModelProvider(this).get(EliminarModel.class);

        binding = FragmentEliminarBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        final TextView textView = binding.textEliminar;
        eliminarModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        sqlite = new sqlite(getContext());
        sqlite.abrir();
        Cursor cursor = sqlite.getRegistro();
        imagenes = sqlite.getImagenes(cursor);
        Componentes(root);
        return root;
    }

    private void Componentes (View root){
        EditTextComponentes(root);
        ButtonComponentes(root);
        TextViewComponentes(root);
    }

    private void EditTextComponentes(View root){
        etID = root.findViewById(R.id.etELID);
    }

    private void TextViewComponentes(View root){
        tvnombre = root.findViewById(R.id.txtELarea);
        tvfecha = root.findViewById(R.id.txtELmedico);
        tvestatura = root.findViewById(R.id.txtELnombre);
        tvpeso = root.findViewById(R.id.txtELgenero);
    }

    private void ButtonComponentes(View root){
        Button btnBuscar = root.findViewById(R.id.btnELBuscar);
        btnLimpiar = root.findViewById(R.id.btnELlimpiar);
        btnGuardar = root.findViewById(R.id.btnELeloiminar);
        ivfoto = root.findViewById(R.id.ivELFoto);

        btnLimpiar.setOnClickListener(this);
        btnGuardar.setOnClickListener(this);
        btnBuscar.setOnClickListener(this);
    }

    private void limpiar(){
        etID.setText("");
        tvestatura.setText("");
        tvnombre.setText("");

        tvfecha.setText("");




        band = 0;
        ivfoto.setImageResource(R.drawable.ic_menu_camera);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btnClimpiar:
                limpiar();
                break;
            case R.id.btnELBuscar:
                if(etID.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "Ingrese el ID del paciente", Toast.LENGTH_SHORT).show();
                }else{
                    sqlite.abrir();
                    int idp = Integer.parseInt(etID.getText().toString());
                    if (sqlite.getValor(idp).getCount()==1){
                        Cursor cursor = sqlite.getValor(idp);
                        if (cursor.moveToFirst()){
                            do {
                                tvnombre.setText(cursor.getString(3));



                                tvfecha.setText(cursor.getString(5));

                                tvestatura.setText(cursor.getString(7));
                                tvpeso.setText(cursor.getString(8));
                                img = cursor.getString(9);

                            }while(cursor.moveToNext());
                            band =1;
                        }
                    }else{
                        Toast.makeText(getContext(), "Error al cargar la información", Toast.LENGTH_SHORT).show();
                    }
                    sqlite.cerrar();
                }
                break;
            case R.id.btnELeloiminar:
                if (band == 1){
                    View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialogo_paciente, null);
                    ((TextView) dialogView.findViewById(R.id.tvdpInfoPaciente)).setText("¿Desea eliminar al paciente?");
                    ImageView ivImagen = dialogView.findViewById(R.id.ivDPFoto);
                    cargarImagen(img, ivImagen);
                    AlertDialog.Builder dialogo = new AlertDialog.Builder(getContext());
                    dialogo.setTitle("Importante");
                    dialogo.setCancelable(false);
                    dialogo.setView(dialogView);
                    dialogo.setPositiveButton("Confirmar", (dialogInterface, i) -> {
                        sqlite.abrir();
                        sqlite.Eliminar(etID.getText());
                        sqlite.cerrar();
                        Toast.makeText(getContext(), "Paciente eliminado", Toast.LENGTH_SHORT).show();
                        limpiar();
                        band=0;
                    });
                    dialogo.setNegativeButton("Cancelar", (dialogInterface, i) -> Toast.makeText(getContext(), "Paciente aún activo", Toast.LENGTH_SHORT).show());
                    dialogo.show();
                }else{
                    Toast.makeText(getContext(), "Error al eliminar al paciente", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public void cargarImagen(String imagen, ImageView iv){
        try {
            File file = new File(imagen);
            Uri uriPhoto = FileProvider.getUriForFile(getContext(), "com.example.HospitalSX.fileprovider", file);
            iv.setImageURI(uriPhoto);
        }catch(Exception ex){
            Toast.makeText(getContext(), "Error al cargar la imagen", Toast.LENGTH_SHORT).show();
            Log.d("Carga de imagen", "Error al cargar imagen" + imagen + "\n Mensaje" + ex.getMessage() +
                    "\n Causa" + ex.getCause());
        }
    }



    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}