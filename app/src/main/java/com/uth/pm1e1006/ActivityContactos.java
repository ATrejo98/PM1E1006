package com.uth.pm1e1006;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import Configuracion.OperacionBD;
import Configuracion.SQLiteConexion;
import Models.contactos;

import java.io.Serializable;



    public class ActivityContactos extends AppCompatActivity {

        SQLiteConexion conexion;
        ListView listaContactos;
        ArrayList<contactos> lista;
        ArrayList<String> Arreglo;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_contactos);


            conexion = new SQLiteConexion(this, OperacionBD.DBname, null, 1);
            listaContactos = (ListView) findViewById(R.id.listaContactos);

            ObtenerInfo();

            ArrayAdapter adp = new ArrayAdapter(this, android.R.layout.simple_list_item_1, Arreglo);

            listaContactos.setAdapter(adp);


            listaContactos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                    contactos contactoSeleccionado = lista.get(i);


                    AlertDialog.Builder builder = new AlertDialog.Builder(ActivityContactos.this);
                    builder.setTitle("Confirmación");
                    builder.setMessage("¿Desea realizar alguna acción para este contacto?");
                    builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int which) {

                            Intent intent = new Intent(ActivityContactos.this, ActivityPerfil.class);
                            intent.putExtra("contactoSeleccionado", contactoSeleccionado);
                            startActivity(intent);
                        }
                    });
                    builder.setNegativeButton("No", null);


                    builder.create().show();
                    return true;
                }
            });


            listaContactos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                }
            });


        }

        private void ObtenerInfo() {
            SQLiteDatabase db = conexion.getReadableDatabase();
            contactos persona = null;
            lista = new ArrayList<contactos>();

            Cursor cursor = db.rawQuery(OperacionBD.SelectAllPersonas, null);

            while (cursor.moveToNext()) {
                persona = new contactos();
                persona.setId(cursor.getInt(cursor.getColumnIndexOrThrow(OperacionBD.id)));
                persona.setNombreCompleto(cursor.getString(cursor.getColumnIndexOrThrow(OperacionBD.nombreCompleto)));
                persona.setTelefono(cursor.getString(cursor.getColumnIndexOrThrow(OperacionBD.telefono)));
                persona.setNota(cursor.getString(cursor.getColumnIndexOrThrow(OperacionBD.nota)));
                persona.setPais(cursor.getString(cursor.getColumnIndexOrThrow(OperacionBD.pais)));

                lista.add(persona);

            }
            cursor.close();
            FillData();
        }

        private void FillData() {
            Arreglo = new ArrayList<String>();
            for (int i = 0; i < lista.size(); i++) {
                contactos persona = lista.get(i);
                Arreglo.add(persona.getId() + " | " +
                        persona.getPais() + " | " +
                        persona.getNombreCompleto() + " | " +
                        persona.getTelefono() + " | " +
                        persona.getNota()
                );
            }
        }

    }
