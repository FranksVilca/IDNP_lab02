package com.example.ednp_lab02;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

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

        // //Botón Aceptar para crear cuenta y devolver datos
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
                saveAccountToFile(accountObject);

                // Regresar a LoginActivity
                Intent resultIntent = new Intent();
                setResult(RESULT_OK, resultIntent);
                finish();  // cerrar AccountActivity
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // btncancel para regresar a LoginActivity
        btnCancel.setOnClickListener(v -> {
            setResult(RESULT_CANCELED);
            finish();
        });
    }
    // Método para guardar la cuenta en un archivo
    private void saveAccountToFile(JSONObject accountObject) {
        try {
            String filename = "cuentas.txt";
            FileOutputStream fos = openFileOutput(filename, Context.MODE_APPEND);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos));

            // Convertir los detalles de la cuenta en formato CSV
            String accountData = accountObject.getString("username") + "," +
                    accountObject.getString("password") + "," +
                    accountObject.getString("firstname") + "," +
                    accountObject.getString("lastname") + "," +
                    accountObject.getString("email") + "," +
                    accountObject.getString("phone") + "\n";

            writer.write(accountData);
            writer.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


