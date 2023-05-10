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
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RegisterFragment extends Fragment {

    TextInputEditText etRegFullName;
    TextInputEditText etRegMobile;
    TextInputEditText etRegDate;
    TextInputEditText etRegEmail;
    TextInputEditText etRegPassword;
    Button btnSignUpSubmit;
    FirebaseAuth mAuth;
    Date date = null;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private static final String KEY_FULLNAME = "fullName";
    private static final String KEY_MOBILE = "mobile";
    private static final String KEY_BIRTHDATE = "birthdate";
    private static final String KEY_EMAIL = "emial";

    FirebaseFirestore usersdb = FirebaseFirestore.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        etRegFullName = view.findViewById(R.id.etRegFullName);
        etRegMobile = view.findViewById(R.id.etRegMobile);
        etRegDate = view.findViewById(R.id.etRegDate);
        etRegEmail = view.findViewById(R.id.etRegEmail);
        etRegPassword = view.findViewById(R.id.etRegPass);
        btnSignUpSubmit = view.findViewById(R.id.btnSignUpSubmit);
        mAuth = FirebaseAuth.getInstance();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        btnSignUpSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mAuth.createUserWithEmailAndPassword(etRegEmail.getText().toString(), etRegPassword.getText().toString())
                        .addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task)
                            {

                                if (etRegFullName.getText().toString() != null
                                        && etRegMobile.getText().toString() != null &&
                                etRegDate.getText().toString() != null){


                                    if(task.isSuccessful())
                                    {

                                        try {
                                            date = dateFormat.parse(date)
                                        }



                                        // Sign in success, update UI with the signed-in user's information
//                                    FirebaseUser user = mAuth.getCurrentUser();
//                                    if(user != null)
//                                        Toast.makeText(RegisterActivity.this,"Authentication Succeeded",Toast.LENGTH_SHORT).show();

                                        Map<String , Object> userMap= new HashMap<>();
                                        User user = new User(etRegFullName.getText().toString(), etRegMobile.getText().toString() , etRegDate.getText().toString() ,etRegEmail.getText().toString());

                                        userMap.put(KEY_FULLNAME,etRegFullName.getText().toString());
                                        userMap.put(KEY_MOBILE,etRegMobile.getText().toString());
                                        userMap.put(KEY_EMAIL,etRegEmail.getText().toString());


                                        usersdb.collection("usersdb").document(mAuth.getCurrentUser().getUid()).set(user);

                                        Intent intent = new Intent(requireContext(), MainActivity.class);
                                        intent.putExtra("user", user);
                                        startActivity(intent);

                                    }

                                    else
                                    {
                                        Toast.makeText(requireContext(),"Authentication Failed",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        });
            }
        });


    }

}