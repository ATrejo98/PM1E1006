package com.uth.pm1e1006;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

import Configuracion.OperacionBD;
import Configuracion.SQLiteConexion;
import Models.contactos;


public class ActivityPerfil extends AppCompatActivity {

    SQLiteConexion conexion;

    Button btnVerImagen, btnCompartir, btnActualizar, btnEliminar;
    EditText txtnotaPerfil, txtTelefonoPerfil, txtNombrePerfil;
    Spinner spinnerPaisPerfil;
    ImageView imageViewPerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        contactos contactoSeleccionado = (contactos) getIntent().getSerializableExtra("contactoSeleccionado");

        btnVerImagen = findViewById(R.id.btnVerImagen);
        btnCompartir = findViewById(R.id.btnCompartir);
        btnActualizar = findViewById(R.id.btnActualizar);
        btnEliminar = findViewById(R.id.btnEliminar);
        txtnotaPerfil = (EditText) findViewById(R.id.txtnotaPerfil);
        txtTelefonoPerfil = (EditText) findViewById(R.id.txtTelefonoPerfil);
        txtNombrePerfil = (EditText) findViewById(R.id.txtNombrePerfil);
        spinnerPaisPerfil = (Spinner) findViewById(R.id.spinnerPaisPerfil);
        imageViewPerfil = (ImageView) findViewById(R.id.imageViewPerfil);


        txtNombrePerfil.setText(contactoSeleccionado.getNombreCompleto());
        txtTelefonoPerfil.setText(contactoSeleccionado.getTelefono());
        txtnotaPerfil.setText(contactoSeleccionado.getNota());
        String[] paises = {"Honduras", "Belice", "Guatemala", "El Salvador", "Costa Rica", "Panama"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, paises);
        spinnerPaisPerfil.setAdapter(adapter);

        String paisSeleccionado = contactoSeleccionado.getPais();

        int posicionSeleccionada = -1;
        for (int i = 0; i < paises.length; i++) {
            if (paises[i].equals(paisSeleccionado)) {
                posicionSeleccionada = i;
                break;
            }
        }

        if (posicionSeleccionada != -1) {
            spinnerPaisPerfil.setSelection(posicionSeleccionada);
        }


        btnCompartir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = txtNombrePerfil.getText().toString();
                String telefono = txtTelefonoPerfil.getText().toString();
                String nota = txtnotaPerfil.getText().toString();

                String mensaje = "Nombre: " + nombre + "\nTeléfono: " + telefono + "\nNota: " + nota;

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, mensaje);

                startActivity(Intent.createChooser(intent, "Compartir vía"));
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }



}