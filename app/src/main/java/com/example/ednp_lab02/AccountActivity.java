package com.example.ednp_lab02;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ednp_lab02.databinding.ActivityMainBinding;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class AccountActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        EditText edtUsername = binding.edtUsername;
        EditText edtPassword = binding.edtPassword;
        Button btnLogin = binding.btnLogin;
        Button btnAddAccount = binding.btnAddAccount;

        // Listener para el botón de inicio de sesión
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtUsername.getText().toString();
                String password = edtPassword.getText().toString();

                // Validar credenciales usando el archivo cuentas.txt
                if (validateAccount(username, password)) {
                    Toast.makeText(getApplicationContext(), "Bienvenido " + username, Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Bienvenido " + username);

                    // Iniciar HomeActivity
                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Error en la autenticación", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Error en la autenticación");
                }
            }
        });

        // Listener para el botón de agregar cuenta
        btnAddAccount.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), AccountActivity.class);
            startActivity(intent);
        });
    }

    // Método para validar el usuario en el archivo cuentas.txt
    private boolean validateAccount(String username, String password) {
        try {
            // Abrir el archivo cuentas.txt desde assets
            InputStream is = getAssets().open("cuentas.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            String line;
            while ((line = reader.readLine()) != null) {
                // Suponiendo que las líneas tienen el formato: username,password,firstname,lastname,email,phone
                String[] accountData = line.split(",");

                if (accountData.length >= 2) {
                    String storedUsername = accountData[0];
                    String storedPassword = accountData[1];

                    // Comparar credenciales
                    if (storedUsername.equals(username) && storedPassword.equals(password)) {
                        return true;
                    }
                }
            }

            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;  // Retornar false si no se encontró la cuenta
    }
}
