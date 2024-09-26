package com.example.ednp_lab02;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;

public class AccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        // Referencias a los componentes de la interfaz
        EditText edtFirstname = findViewById(R.id.edtFirstname);
        EditText edtLastname = findViewById(R.id.edtLastname);
        EditText edtEmail = findViewById(R.id.edtEmail);
        EditText edtPhone = findViewById(R.id.edtPhone);
        EditText edtUsername = findViewById(R.id.edtUsername);
        EditText edtPassword = findViewById(R.id.edtPassword);
        Button btnAccept = findViewById(R.id.btnAccept);
        Button btnCancel = findViewById(R.id.btnCancel);

        // Botón Aceptar para crear cuenta y devolver datos
        btnAccept.setOnClickListener(v -> {
            try {
                // Crear un objeto JSON con los detalles de la cuenta
                JSONObject accountObject = new JSONObject();
                accountObject.put("firstname", edtFirstname.getText().toString());
                accountObject.put("lastname", edtLastname.getText().toString());
                accountObject.put("email", edtEmail.getText().toString());
                accountObject.put("phone", edtPhone.getText().toString());
                accountObject.put("username", edtUsername.getText().toString());
                accountObject.put("password", edtPassword.getText().toString());

                // Guardar los detalles de la cuenta en un archivo
                saveAccountToPrivateExternalStorage(accountObject);

                // Regresar a LoginActivity
                Intent resultIntent = new Intent();
                setResult(RESULT_OK, resultIntent);
                finish();  // Cerrar AccountActivity
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // Botón Cancelar para regresar a LoginActivity
        btnCancel.setOnClickListener(v -> {
            setResult(RESULT_CANCELED);
            finish();
        });
    }

    // Método para guardar la cuenta en el almacenamiento externo privado
    private void saveAccountToPrivateExternalStorage(JSONObject accountObject) {
        try {
            // Obtener el directorio privado en el almacenamiento externo
            File externalPrivateDir = getExternalFilesDir(null);  // null indica el directorio raíz privado

            // Si el directorio no existe, crearlo
            if (!externalPrivateDir.exists()) {
                externalPrivateDir.mkdirs();
            }

            // Crear el archivo "cuentas.txt" en el almacenamiento externo privado
            File file = new File(externalPrivateDir, "cuentas.txt");
            FileWriter writer = new FileWriter(file, true);  // 'true' para agregar contenido

            // Escribir los detalles de la cuenta en formato CSV
            String accountData = accountObject.getString("username") + "," +
                    accountObject.getString("password") + "," +
                    accountObject.getString("firstname") + "," +
                    accountObject.getString("lastname") + "," +
                    accountObject.getString("email") + "," +
                    accountObject.getString("phone") + "\n";

            writer.write(accountData);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //Leer archivos
    private String readFile(String filename) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            FileInputStream fis = openFileInput(filename);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));

            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }

            reader.close();
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return stringBuilder.toString();
    }
}
