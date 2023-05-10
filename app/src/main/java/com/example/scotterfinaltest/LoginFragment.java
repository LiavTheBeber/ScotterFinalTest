package com.example.scotterfinaltest;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginFragment extends Fragment {
    TextInputEditText etLogInEmail;
    TextInputEditText etLogInPassword;
    Button btnLogInSubmit;
    FirebaseAuth mAuth;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        etLogInEmail = view.findViewById(R.id.etLoginEmail);
        etLogInPassword = view.findViewById(R.id.etLoginPass);
        btnLogInSubmit = view.findViewById(R.id.btnLoginSubmit);
        mAuth = FirebaseAuth.getInstance();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        btnLogInSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mAuth.signInWithEmailAndPassword(etLogInEmail.getText().toString(), etLogInPassword.getText().toString())
                        .addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task)
                            {
                                if(task.isSuccessful())
                                {
                                    // Sign in success, update UI with the signed-in user's information
//                                    FirebaseUser user = mAuth.getCurrentUser();
//                                    if(user != null)
//                                        Toast.makeText(LoginActivity.this,"User Is Logged In",Toast.LENGTH_SHORT).show();

                                    //User user = new User(etLogInEmail.getText().toString(), etLogInPassword.getText().toString());
                                    Intent intent = new Intent(requireContext(),MainActivity.class);
                                    //intent.putExtra("user", user);
                                    startActivity(intent);
                                }

                                else
                                {
                                    Toast.makeText(requireContext(), "Sign In Failed", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
            }
        });

    }

}