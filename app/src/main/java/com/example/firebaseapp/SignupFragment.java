package com.example.firebaseapp;

import android.content.Intent;
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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SignupFragment extends Fragment {


    View view;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Map<String, Object> user = new HashMap<>();
    EditText signupUsername, signupPassword;
    Button signupBtn;
    ArrayList<String> users = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_signup, container, false);
        addUsersToList();
        signupUsername = view.findViewById(R.id.signupUsername);
        signupPassword = view.findViewById(R.id.signupPassword);
        signupBtn = view.findViewById(R.id.signupBtn);
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("TAG", "Username input =>" + signupUsername.getText().toString().trim());
                Log.d("TAG", "Password input =>" + signupPassword.getText().toString().trim());
                //signupUsername.getText().toString(), signupPassword.getText().toString()

                if((signupUsername.getText().toString().trim().equals("") || signupPassword.getText().toString().trim().equals(""))){
                    Toast.makeText(getActivity(), "Wrong format on username or password", Toast.LENGTH_SHORT).show();
                    Log.d("TAG", "Wrong format on username or password");
                }
                else {
                    if (checkAndAddUser(signupUsername.getText().toString())) {
                        Toast.makeText(getActivity(), "User already exists", Toast.LENGTH_SHORT).show();
                        Log.d("TAG", "User already exists");
                        users.clear();
                    } else {
                        addUser(signupUsername.getText().toString().trim(), signupPassword.getText().toString().trim());
                        Toast.makeText(getActivity(), "User created", Toast.LENGTH_SHORT).show();
                        Log.d("TAG", "User created");
                        users.clear();
                    }
                }
            }
        });

        return view;
    }

    public void addUser(String name, String pass) {
        user.clear();

        // Create a new user with name & password
        user.put("name", name);
        user.put("password", pass);

        // Add a new document with a generated ID
        db.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error adding document", e);
                    }
                });
    }

    public boolean checkAndAddUser(String Username) {

        //get Users
        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                users.add(String.valueOf(document.get("name")));
                            }
                        }
                        Log.d("TAG", "----------------------------------");
                    }
                });
        int i = 1;
        for (String item : users) {
            Log.d("TAG", "User " + i++ +": " + item);

            if (Username.equals(item)) {
                return true;
            }
        }
        return false;
    }

    public void addUsersToList() {

        //get Users
        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                users.add(String.valueOf(document.get("name")));// add into arraylist
                            }

                        }
                    }
                });
    }


    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }

    /*
    user.clear();
                                    // Create a new user with name & password
                                    user.put("name", name);
                                    user.put("password", pass);
                                    // Add a new document with a generated ID
                                    db.collection("users")
                                            .add(user)
                                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                @Override
                                                public void onSuccess(DocumentReference documentReference) {
                                                    Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId());
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.w("TAG", "Error adding document", e);
                                                }
                                            });
     */

}