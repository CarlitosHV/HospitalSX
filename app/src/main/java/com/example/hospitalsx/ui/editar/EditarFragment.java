package com.example.hospitalsx.ui.editar;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.hospitalsx.R;
import com.example.hospitalsx.bd.sqlite;
import com.example.hospitalsx.databinding.FragmentEditarBinding;
import com.example.hospitalsx.ui.crear.CrearViewModel;

import java.util.ArrayList;
import java.util.Calendar;

public class EditarFragment extends Fragment implements  View.OnClickListener, AdapterView.OnItemSelectedListener, DatePickerDialog.OnDateSetListener{

    private Button btnLimpiar, btnGuardar, btnBuscar;
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
    private String rutaImagen;

    public sqlite sqlite;

    private FragmentEditarBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        EditarModel editarModel =
                new ViewModelProvider(this).get(EditarModel.class);

        binding = FragmentEditarBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textEditar;
        editarModel.getText().observe(getViewLifecycleOwner(), textView::setText);

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
        etID = root.findViewById(R.id.etEDID);
        etNombre = root.findViewById(R.id.etEDnombre);
        etFecha = root.findViewById(R.id.etEDFechaIngresoP);
        etEdad = root.findViewById(R.id.etEDEdad);
        etEstatura = root.findViewById(R.id.etEDestatura);
        etPeso = root.findViewById(R.id.etEDPeso);
    }

    private void ButtonComponentes(View root){
        btnBuscar = root.findViewById(R.id.btnEDBuscar);
        btnLimpiar = root.findViewById(R.id.btnEDlimpiar);
        btnGuardar = root.findViewById(R.id.btnEDguardar);
        ivFoto = root.findViewById(R.id.ivEDFoto);
        btnCalendario = root.findViewById(R.id.ibtnEDFechaIngreso);

        btnCalendario.setOnClickListener(this);
        btnLimpiar.setOnClickListener(this);
        btnGuardar.setOnClickListener(this);
        btnBuscar.setOnClickListener(this);
        ivFoto.setOnClickListener(this);
    }

    private void SpinnerComponentes(View root){
        ArrayAdapter<CharSequence> areaAdapter, drAdapter, generoAdapter;
        areaAdapter = ArrayAdapter.createFromResource(getContext(), R.array.opciones, android.R.layout.simple_spinner_item);
        drAdapter = ArrayAdapter.createFromResource(getContext(), R.array.o0, android.R.layout.simple_spinner_item);
        generoAdapter = ArrayAdapter.createFromResource(getContext(), R.array.sx, android.R.layout.simple_spinner_item);

        spnArea = root.findViewById(R.id.spnEDareaP);
        spnArea.setAdapter(areaAdapter);

        spnDr = root.findViewById(R.id.spnEDdoctor);
        spnDr.setAdapter(drAdapter);

        spnGenero = root.findViewById(R.id.spnEgenero);
        spnGenero.setAdapter(generoAdapter);

        spnArea.setOnItemSelectedListener(this);
        spnDr.setOnItemSelectedListener(this);
        spnGenero.setOnItemSelectedListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        etFecha.setText(day + "/" + (month+1) + "/" + year);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        switch (adapterView.getId()){
            case R.id.spnEDareaP:
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
            case R.id.spnEDdoctor:
                if(position!=0){
                    d = adapterView.getItemAtPosition(position).toString();
                }else{
                    d="";
                }
                break;
            case R.id.spnEgenero:
                if(position!=0){
                    sex = adapterView.getItemAtPosition(position).toString();
                }else{
                    sex="";
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

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.ibtnEDFechaIngreso:
                cal = Calendar.getInstance();
                anio = cal.get(Calendar.YEAR);
                mes = cal.get(Calendar.MONTH);
                dia = cal.get(Calendar.DAY_OF_MONTH);
                dpd = new DatePickerDialog(getContext(), this, anio, mes, dia);
                dpd.show();
                break;
            case R.id.btnEDlimpiar:
                limpiar();
                break;
            //Hacer funcionar esta parte
            case R.id.ivEDFoto:
                break;
            case R.id.btnEDguardar:
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
                    String actualizacion = sqlite.updateRegistroPaciente(id, a, d, nom, sex, fecha, edad, estatura, peso, img);
                    if (actualizacion.contentEquals("Paciente actualizado con éxito")){
                        Toast.makeText(getContext(), "Información guardada con éxito", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getContext(), "Error al guardar la información", Toast.LENGTH_SHORT).show();
                    }
                    sqlite.cerrar();
                }
                break;
            case R.id.btnEDBuscar:
                if(etID.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "Ingrese el ID del paciente", Toast.LENGTH_SHORT).show();
                }else{
                    sqlite.abrir();
                    int idp = Integer.parseInt(etID.getText().toString());
                    if (sqlite.getValor(idp).getCount()==1){
                        Cursor cursor = sqlite.getValor(idp);
                        if (cursor.moveToFirst()){
                            do {
                                etNombre.setText(cursor.getString(3));
                                a = cursor.getString(1);
                                d = cursor.getString(2);
                                sex = cursor.getString(4);
                                etFecha.setText(cursor.getString(5));
                                etEdad.setText(cursor.getString(6));
                                etEstatura.setText(cursor.getString(7));
                                etPeso.setText(cursor.getString(8));
                                img = cursor.getString(9);
                                Bitmap imgBitMap = BitmapFactory.decodeFile(img);
                                ivFoto.setImageBitmap(imgBitMap);

                            }while(cursor.moveToNext());
                        }
                    }else{
                        Toast.makeText(getContext(), "Error al cargar la información", Toast.LENGTH_SHORT).show();
                    }
                    sqlite.cerrar();
                }
                break;
        }
    }
}