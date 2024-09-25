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

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflar el diseño de la actividad con ViewBinding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // Referencias a los componentes de la interfaz
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

                // Validar credenciales y obtener el firstname si es correcto
                String firstname = validateAccount(username, password);

                if (firstname != null) {
                    // Si la validación es exitosa, mostrar mensaje y navegar a HomeActivity
                    Toast.makeText(getApplicationContext(), "Bienvenido " + firstname, Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Bienvenido " + firstname);

                    // Iniciar HomeActivity y pasar el firstname
                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    intent.putExtra("firstname", firstname);  // Pasar el firstname a HomeActivity
                    startActivity(intent);
                } else {
                    // Mostrar mensaje de error si las credenciales son incorrectas
                    Toast.makeText(getApplicationContext(), "Cuenta no encontrada", Toast.LENGTH_SHORT).show();
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
    private String validateAccount(String username, String password) {
        try {
            // Abrir el archivo cuentas.txt desde assets
            InputStream is = getAssets().open("cuentas.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            String line;
            while ((line = reader.readLine()) != null) {
                // Suponiendo que las líneas tienen el formato: username,password,firstname,lastname,email,phone
                String[] accountData = line.split(",");

                if (accountData.length >= 3) {
                    String storedUsername = accountData[0];
                    String storedPassword = accountData[1];
                    String firstname = accountData[2];  // Obtener el firstname

                    // Comparar credenciales
                    if (storedUsername.equals(username) && storedPassword.equals(password)) {
                        return firstname;  // Retornar el firstname si las credenciales coinciden
                    }
                }
            }

            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;  // Retornar null si no se encontró la cuenta o las credenciales no coinciden
    }
    // Handle new account creation response
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            // Receive new account data in JSON format and save it
            String jsonAccount = data.getStringExtra("accountData");
            saveAccountToFile(jsonAccount);
        }
    }
    // Save new account to cuentas.txt
    private void saveAccountToFile(String jsonAccount) {
        try {
            JSONObject accountObject = new JSONObject(jsonAccount);
            String username = accountObject.getString("username");
            String password = accountObject.getString("password");
            String firstname = accountObject.getString("firstname");
            String lastname = accountObject.getString("lastname");
            String email = accountObject.getString("email");
            String phone = accountObject.getString("phone");

            FileOutputStream fos = openFileOutput("cuentas.txt", MODE_APPEND);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos));
            writer.write(username + "," + password + "," + firstname + "," + lastname + "," + email + "," + phone + "\n");
            writer.close();
            fos.close();

            Toast.makeText(this, "Cuenta creada exitosamente", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error al crear la cuenta", Toast.LENGTH_SHORT).show();
        }
    }
}
