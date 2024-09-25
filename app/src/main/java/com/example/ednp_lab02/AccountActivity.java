package com.example.ednp_lab02;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONObject;

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
        EditText edtUsername = findViewById(R.id.edtUsername2);
        EditText edtPassword = findViewById(R.id.edtPassword2);
        Button btnAccept = findViewById(R.id.btnAccept);
        Button btnCancel = findViewById(R.id.btnCancel);

        // Accept button to create account and return data
        btnAccept.setOnClickListener(v -> {
            try {
                // Create JSON object with account details
                JSONObject accountObject = new JSONObject();
                accountObject.put("firstname", edtFirstname.getText().toString());
                accountObject.put("lastname", edtLastname.getText().toString());
                accountObject.put("email", edtEmail.getText().toString());
                accountObject.put("phone", edtPhone.getText().toString());
                accountObject.put("username", edtUsername2.getText().toString());
                accountObject.put("password", edtPassword2.getText().toString());

                // Pass the JSON object back to LoginActivity
                Intent resultIntent = new Intent();
                resultIntent.putExtra("accountData", accountObject.toString());
                setResult(RESULT_OK, resultIntent);
                finish();  // cerrar AccountActivity y retonar
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
}


