package com.example.hospitalsx.ui.crear;

import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Button;

import com.example.hospitalsx.R;

import java.util.Calendar;

public class CrearFragment extends Fragment {

    private Button btnLimpiar, btnGuardar;
    private EditText etID, etNombre, etFecha, etEdad, etEstatura, etPeso;
    private Spinner spnArea, spnDr, spnGenero;
    private ImageView ivFoto;
    private ImageButton btnCalendario;

    DatePickerDialog dpd;
    Calendar cal;

    private static int anio, mes, dia;
    public static String img = "", a, d, sex;
    public static final int REQUEST_TAKE_PHOTO =1;
    private Uri photoUri;

    private CrearViewModel mViewModel;

    public static CrearFragment newInstance() {
        return new CrearFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_crear, container, false);

        Componentes(root);

        return root;
    }

    private void Componentes (View root){
        EditTextComponentes(root);
        ButtonComponentes(root);
        SpinnerComponentes(root);
    }

    private void EditTextComponentes(View root){
        etID = root.findViewById(R.id.etCID);
        etNombre = root.findViewById(R.id.etCnombre);
        etFecha = root.findViewById(R.id.etCFechaIngresoP);
        etEdad = root.findViewById(R.id.etCEdad);
        etEstatura = root.findViewById(R.id.etCEstatura);
        etPeso = root.findViewById(R.id.etCPeso);
    }

    private void ButtonComponentes(View root){
        btnLimpiar = root.findViewById(R.id.btnClimpiar);
        btnGuardar = root.findViewById(R.id.btnCguardar);
        ivFoto = root.findViewById(R.id.ivcFoto);
        btnCalendario = root.findViewById(R.id.ibtnCFechaIngreso);
    }

    private void SpinnerComponentes(View root){

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(CrearViewModel.class);
        // TODO: Use the ViewModel
    }

}