package com.example.firebaseapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class LoginFragment extends Fragment {

    View view;
    TextView loginErrorText;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    EditText loginUsername, loginPassword;
    Button loginBtn;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_login, container, false);

        loginUsername = view.findViewById(R.id.loginUsername);
        loginPassword = view.findViewById(R.id.loginPassword);
        loginErrorText = view.findViewById(R.id.loginErrorText);
        loginBtn = view.findViewById(R.id.loginBtn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAndLoginUser(loginUsername.getText().toString(), loginPassword.getText().toString());
            }
        });

        return view;
    }

    public void checkAndLoginUser(String name, String pass) {
        //get Users
        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                Log.d("TAG", document.getId() + " => " + "name: " + document.get("name") + " password: " + document.get("password"));

                                if (name.equals(document.get("name")) && pass.equals(document.get("password"))) {
                                    Toast.makeText(getActivity(), "Logged in", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getActivity(), MainActivity3.class);
                                    intent.putExtra("userName", name);
                                    startActivity(intent);
                                }
                            }
                        }
                        Log.d("TAG", "----------------------------------");
                    }
                });
    }
}