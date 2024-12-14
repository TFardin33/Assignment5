package com.example.hello;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;

public class RegexValidation extends AppCompatActivity {

    private EditText nameInput, idInput, emailInput, phoneInput, passwordInput, confirmPassInput;
    private Button submitButton, button1;

    // FirebaseAuth instance
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regex_validation);

        // Initialize FirebaseAuth
        firebaseAuth = FirebaseAuth.getInstance();

        // Initialize UI elements
        nameInput = findViewById(R.id.name_input);
        idInput = findViewById(R.id.id_input);
        emailInput = findViewById(R.id.email_input);
        phoneInput = findViewById(R.id.phone_input);
        passwordInput = findViewById(R.id.password_input);
        confirmPassInput = findViewById(R.id.c_password_input);
        submitButton = findViewById(R.id.submit_button);
        button1 = findViewById(R.id.button_1);

        // OnClickListeners
        submitButton.setOnClickListener(v -> validateAndRegister());

        button1.setOnClickListener(v -> {
            Intent mainIntent = new Intent(RegexValidation.this, ExpandableListActivity.class);
            startActivity(mainIntent);
        });
    }

    private void validateAndRegister() {
        String name = nameInput.getText().toString();
        String id = idInput.getText().toString();
        String email = emailInput.getText().toString();
        String phone = phoneInput.getText().toString();
        String password = passwordInput.getText().toString();
        String confirmPass = confirmPassInput.getText().toString();

        // Regular expression patterns
        Pattern namePattern = Pattern.compile("[A-Z][a-z]+(?:\\s[A-Z][a-z]+)?");
        Pattern emailPattern = Pattern.compile("[a-z0-9][\\w.-]+@[\\w.-]+.[a-z]{2,}");
        Pattern phonePattern = Pattern.compile("01[356789]\\d{8}");
        Pattern passwordPattern = Pattern.compile("^(?=.*[A-Z])(?=.*[\\W]).{6,}$");
        Pattern ID_PATTERN = Pattern.compile("0182210012101\\d{3}");

        // Validation
        if (!namePattern.matcher(name).matches()) {
            nameInput.setError("Invalid name! Only letters allowed.");
            return;
        }

        if (!ID_PATTERN.matcher(id).matches()) {
            idInput.setError("ID must be in the format 0182210012101xxx, where xxx are digits.");
            return;
        }

        if (!emailPattern.matcher(email).matches()) {
            emailInput.setError("Invalid email address!");
            return;
        }

        if (!phonePattern.matcher(phone).matches()) {
            phoneInput.setError("Invalid phone number!");
            return;
        }

        if (!passwordPattern.matcher(password).matches()) {
            passwordInput.setError("Password must be at least 6 characters with 1 uppercase, 1 number, and 1 special character.");
            return;
        }

        if (!confirmPass.equals(password)) {
            confirmPassInput.setError("Password does not match!");
            return;
        }

        // Firebase Authentication
        registerUserWithFirebase(email, password);
    }

    private void registerUserWithFirebase(String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Registration successful
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        Toast.makeText(RegexValidation.this, "Registration successful! Welcome, " + user.getEmail(), Toast.LENGTH_SHORT).show();

                        // Optionally, navigate to another activity
                        Intent intent = new Intent(RegexValidation.this, ExpandableListActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // Registration failed
                        Toast.makeText(RegexValidation.this, "Registration failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
