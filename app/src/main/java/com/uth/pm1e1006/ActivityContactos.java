package com.uth.pm1e1006;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;

import Configuracion.OperacionBD;
import Configuracion.SQLiteConexion;
import Models.contactos;

import java.io.Serializable;



    public class ActivityContactos extends AppCompatActivity {

        static final int PETICION_LLAMADA = 100;

        SQLiteConexion conexion;
        ListView listaContactos;
        ArrayList<contactos> lista;
        ArrayList<String> Arreglo;

        SearchView buscar;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_contactos);

            buscar = findViewById(R.id.campoBuscar);


            conexion = new SQLiteConexion(this, OperacionBD.DBname, null, 1);
            listaContactos = (ListView) findViewById(R.id.listaContactos);

            ObtenerInfo();

            ArrayAdapter adp = new ArrayAdapter(this, android.R.layout.simple_list_item_1, Arreglo);

            listaContactos.setAdapter(adp);

            buscar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    filtrarContactos(newText);
                    return false;
                }
            });


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
                    contactos persona = lista.get(position);
                    final String telefono = persona.getTelefono();

                    AlertDialog.Builder builder = new AlertDialog.Builder(ActivityContactos.this);
                    builder.setTitle("Confirmación");
                    builder.setMessage("¿Desea realizar la llamada a este contacto?");
                    builder.setPositiveButton("Llamar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            Intent callIntent = new Intent(Intent.ACTION_CALL);
                            callIntent.setData(Uri.parse("tel:" + telefono));


                            if (ActivityCompat.checkSelfPermission(ActivityContactos.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                ActivityCompat.requestPermissions(ActivityContactos.this, new String[]{android.Manifest.permission.CALL_PHONE}, PETICION_LLAMADA);
                                return;
                            }

                            startActivity(callIntent);
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });

                    builder.create().show();
                }


            });


        }


        private void filtrarContactos(String query) {
            ArrayList<contactos> listaFiltrada = new ArrayList<>();
            for (contactos contacto : lista) {
                if (contacto.getNombreCompleto().toLowerCase().contains(query.toLowerCase())) {
                    listaFiltrada.add(contacto);
                }
            }
            // Actualiza la lista mostrada en el ListView con los resultados filtrados
            ArrayAdapter<String> adp = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, convertirAString(listaFiltrada));
            listaContactos.setAdapter(adp);
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
            Arreglo = convertirAString(lista);
        }

        private ArrayList<String> convertirAString(ArrayList<contactos> lista) {
            ArrayList<String> resultado = new ArrayList<>();
            for (contactos persona : lista) {
                resultado.add(persona.getId() + "   |   " +
                        persona.getNombreCompleto() + "\n Numero Telefonico: " +
                        persona.getTelefono());
            }
            return resultado;
        }

    }
