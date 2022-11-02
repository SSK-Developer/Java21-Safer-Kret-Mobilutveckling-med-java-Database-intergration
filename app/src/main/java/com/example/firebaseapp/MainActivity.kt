package com.example.firebaseapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.firebaseapp.R
import com.example.firebaseapp.LoginFragment
import com.example.firebaseapp.SignupFragment

class MainActivity : AppCompatActivity() {

    lateinit var loginBtn: Button
    lateinit var signupBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loginBtn = findViewById(R.id.mainLoginBtn)
        signupBtn = findViewById(R.id.mainSignupBtn)
       // loginBtn.setOnClickListener(View.OnClickListener { replaceFragment(LoginFragment()) })
        loginBtn.setOnClickListener (){ replaceFragment(LoginFragment() ) }
        signupBtn.setOnClickListener (){ replaceFragment(SignupFragment() ) }
        //signupBtn.setOnClickListener(View.OnClickListener { replaceFragment(SignupFragment()) })
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment).addToBackStack(null)
        fragmentTransaction.commit()
    }
}