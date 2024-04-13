package com.example.texcontaskotpauth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.texcontaskotpauth.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private  lateinit var auth: FirebaseAuth
    private lateinit var signoutBtn :Button
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        signoutBtn =binding.signOutbtn

        signoutBtn.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this, PhoneAuthActivity::class.java))
        }

    }
}