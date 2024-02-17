package com.uth.pm1e1006;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import Configuracion.OperacionBD;
import Configuracion.SQLiteConexion;

import android.database.sqlite.SQLiteDatabase;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends AppCompatActivity {


    ///Declaracion de Variables

    EditText nombreCompleto, telefono, nota;
    private String currentPhotoPath;
    static final int PETICION_ACCESO_CAM = 100;
    private static final int REQUEST_IMAGE_CAPTURE = 1;

    ImageView imageView;
    Button btntomarfoto, btnGuardarContacto, btnContactos;
    Spinner spinnerPais;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView) findViewById(R.id.imageViewPerfil);
        btntomarfoto = (Button) findViewById(R.id.btntomarfoto);
        btnGuardarContacto = findViewById(R.id.btnGuardarContacto);
        btnContactos = findViewById(R.id.btnContactos);


        spinnerPais = (Spinner) findViewById(R.id.spinnerPaisPerfil);
        nombreCompleto = (EditText) findViewById(R.id.txtNombrePerfil);
        telefono = (EditText) findViewById(R.id.txtTelefonoPerfil);
        nota = (EditText) findViewById(R.id.txtnotaPerfil);


        String[] paises = {"Honduras", "Belice", "Guatemala", "El Salvador", "Costa Rica", "Panama"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, paises);
        spinnerPais.setAdapter(adapter);


        btntomarfoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                permisos();
            }
        });

        btnGuardarContacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AgreContacto();

            }
        });

        btnContactos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), ActivityContactos.class);
                startActivity(intent);

            }
        });


    }

    private void permisos() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CAMERA) !=
                PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                        PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PETICION_ACCESO_CAM);
        } else {
            dispatchTakePictureIntent();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PETICION_ACCESO_CAM) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                dispatchTakePictureIntent();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Se necesitan permisos de acceso", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            File foto = new File(currentPhotoPath);
            imageView.setImageURI(Uri.fromFile(foto));
            galleryAddPic();
        }
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(currentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );

        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {

            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.uth.pm1e1006.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }


    private void AgreContacto() {

        SQLiteConexion conexion = new SQLiteConexion(this, OperacionBD.DBname, null, 1);
        SQLiteDatabase db = conexion.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(OperacionBD.pais, spinnerPais.getSelectedItem().toString());
        valores.put(OperacionBD.nombreCompleto, nombreCompleto.getText().toString());
        valores.put(OperacionBD.telefono, telefono.getText().toString());
        valores.put(OperacionBD.nota, nota.getText().toString());
        valores.put(OperacionBD.imagen, currentPhotoPath);


        Long resultado = db.insert(OperacionBD.TablePersonas, OperacionBD.id, valores);

        Toast.makeText(getApplicationContext(), "Contacto añadido con exito" + resultado,
                Toast.LENGTH_LONG).show();
        db.close();


    }

}




