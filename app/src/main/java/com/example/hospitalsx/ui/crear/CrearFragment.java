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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Button;
import android.widget.Toast;

import com.example.hospitalsx.R;
import com.example.hospitalsx.bd.sqlite;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class CrearFragment extends Fragment implements  View.OnClickListener, AdapterView.OnItemSelectedListener, DatePickerDialog.OnDateSetListener {

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
    public sqlite sqlite;

    private CrearViewModel mViewModel;

    public static CrearFragment newInstance() {
        return new CrearFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_crear, container, false);
        sqlite = new sqlite(getContext());
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

        btnCalendario.setOnClickListener(this);
        btnLimpiar.setOnClickListener(this);
        btnGuardar.setOnClickListener(this);
        ivFoto.setOnClickListener(this);
    }

    private void SpinnerComponentes(View root){
        ArrayAdapter<CharSequence> areaAdapter, drAdapter, generoAdapter;
        areaAdapter = ArrayAdapter.createFromResource(getContext(), R.array.opciones, android.R.layout.simple_spinner_item);
        drAdapter = ArrayAdapter.createFromResource(getContext(), R.array.o0, android.R.layout.simple_spinner_item);
        generoAdapter = ArrayAdapter.createFromResource(getContext(), R.array.sx, android.R.layout.simple_spinner_item);

        spnArea = root.findViewById(R.id.spnCareaP);
        spnArea.setAdapter(areaAdapter);

        spnDr = root.findViewById(R.id.spnCdoctor);
        spnDr.setAdapter(drAdapter);

        spnGenero = root.findViewById(R.id.spnCgenero);
        spnGenero.setAdapter(generoAdapter);

        spnArea.setOnItemSelectedListener(this);
        spnDr.setOnItemSelectedListener(this);
        spnGenero.setOnItemSelectedListener(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(CrearViewModel.class);
        // TODO: Use the ViewModel
    }



    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        switch (adapterView.getId()){
            case R.id.spnCareaP:
                ArrayAdapter<CharSequence> areaAdapter;

                switch (position){
                    case 1:
                        areaAdapter = ArrayAdapter.createFromResource(getContext(), R.array.o1, android.R.layout.simple_spinner_item);
                        break;
                    case 2:
                        areaAdapter = ArrayAdapter.createFromResource(getContext(), R.array.o2, android.R.layout.simple_spinner_item);
                        break;
                    case 3:
                        areaAdapter = ArrayAdapter.createFromResource(getContext(), R.array.o3, android.R.layout.simple_spinner_item);
                        break;
                    case 4:
                        areaAdapter = ArrayAdapter.createFromResource(getContext(), R.array.o4, android.R.layout.simple_spinner_item);
                        break;
                    default:
                        areaAdapter = ArrayAdapter.createFromResource(getContext(), R.array.opciones, android.R.layout.simple_spinner_item);
                        break;
                }
                if(position!=0){
                    a = adapterView.getItemAtPosition(position).toString();
                }else{
                    a="";
                }
                spnDr.setAdapter(areaAdapter);
                break;
            case R.id.spnCdoctor:
                if(position!=0){
                    d = adapterView.getItemAtPosition(position).toString();
                }else{
                    d="";
                }
                break;
            case R.id.spnCgenero:
                if(position!=0){
                    sex = adapterView.getItemAtPosition(position).toString();
                }else{
                    sex="";
                }
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        etFecha.setText(day + "/" + (month+1) + "/" + year);
    }

    @Override
    public void onClick(View view){
        switch(view.getId()) {
            case R.id.ibtnCFechaIngreso:
                cal = Calendar.getInstance();
                anio = cal.get(Calendar.YEAR);
                mes = cal.get(Calendar.MONTH);
                dia = cal.get(Calendar.DAY_OF_MONTH);
                dpd = new DatePickerDialog(getContext(), this, anio, mes, dia);
                dpd.show();
                break;
            case R.id.btnClimpiar:
                limpiar();
                break;
                //Hacer funcionar esta parte
            case R.id.ivcFoto:
                break;
            case R.id.btnCguardar:
                if (etID.getText().equals("") || etNombre.getText().equals("")
                || etPeso.getText().equals("") || etEstatura.getText().equals("") || etEdad.getText().equals("") || etFecha.getText().equals("")
                ){
                    Toast.makeText(getContext(), "Campos vacíos, verifique la información", Toast.LENGTH_SHORT).show();
                }else{
                    int id = Integer.parseInt(etID.getText().toString());
                    a = a.toUpperCase();
                    d = d.toUpperCase();
                    String nom = etNombre.getText().toString().toUpperCase();
                    sex = sex.toUpperCase();
                    String fecha = etFecha.getText().toString().trim();
                    String edad = etEdad.getText().toString().trim();
                    String estatura = etEstatura.getText().toString().trim();
                    String peso = etPeso.getText().toString().trim();
                    sqlite.abrir();
                    if (sqlite.addRegistroPaciente(id, a, d, nom, sex, fecha, edad, estatura, peso, img)){
                        Toast.makeText(getContext(), "Información guardada con éxito", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getContext(), "Error al guardar la información", Toast.LENGTH_SHORT).show();
                    }
                    sqlite.cerrar();
                }
                break;
        }
    }

    private void limpiar(){
        etID.setText("");
        etNombre.setText("");
        etFecha.setText("");
        etEdad.setText("");
        etEstatura.setText("");
        etPeso.setText("");
        ivFoto.setImageResource(R.drawable.ic_menu_camera);
        a="";
        d="";
        sex="";

        ArrayAdapter<CharSequence> areaAdapter, drAdapter, generoAdapter;
        areaAdapter = ArrayAdapter.createFromResource(getContext(), R.array.opciones, android.R.layout.simple_spinner_item);
        drAdapter = ArrayAdapter.createFromResource(getContext(), R.array.o0, android.R.layout.simple_spinner_item);
        generoAdapter = ArrayAdapter.createFromResource(getContext(), R.array.sx, android.R.layout.simple_spinner_item);
        spnArea.setAdapter(areaAdapter);
        spnDr.setAdapter(drAdapter);
        spnGenero.setAdapter(generoAdapter);

    }
}