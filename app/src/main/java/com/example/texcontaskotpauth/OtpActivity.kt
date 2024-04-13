package com.example.texcontaskotpauth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import com.example.texcontaskotpauth.databinding.ActivityOtpBinding
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import java.util.concurrent.TimeUnit

class OtpActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOtpBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var verifyBtn: Button
    private lateinit var inputOTP1: EditText
    private lateinit var inputOTP2: EditText
    private lateinit var inputOTP3: EditText
    private lateinit var inputOTP4: EditText
    private lateinit var inputOTP5: EditText
    private lateinit var inputOTP6: EditText
    private lateinit var progressBar: ProgressBar

    private lateinit var OTP: String
    private lateinit var phoneNumber: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)


        auth = FirebaseAuth.getInstance()
         progressBar = binding.otpProgressBar
         verifyBtn = binding.verifyOTPBtn
         inputOTP1 = binding.otpEditText1
         inputOTP2 = binding.otpEditText2
         inputOTP3 = binding.otpEditText3
         inputOTP4 = binding.otpEditText4
         inputOTP5 = binding.otpEditText5
         inputOTP6 = binding.otpEditText6

        OTP = intent.getStringExtra("OTP").toString()
        phoneNumber = intent.getStringExtra("phoneNumber")!!

        progressBar.visibility=View.INVISIBLE
        addTextChangeListener()

        verifyBtn.setOnClickListener {
            //collect otp from all the edit texts
            val typedOtp = (inputOTP1.text.toString()+ inputOTP2.text.toString()+ inputOTP3.text.toString()+ inputOTP4.text.toString()+
                    inputOTP5.text.toString()+inputOTP6.text.toString())
            if (typedOtp.isNotEmpty()){
                if(typedOtp.length==6){
                    val credential: PhoneAuthCredential = PhoneAuthProvider.getCredential(OTP, typedOtp)
                    progressBar.visibility = View.VISIBLE
                    signInWithPhoneAuthCredential(credential)
                }
                else{
                    Toast.makeText(this, "Please enter correct OTP", Toast.LENGTH_SHORT).show()
                }
            }
            else{
                Toast.makeText(this, "Please enter OTP", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun sendToMain(){
        startActivity(Intent(this,MainActivity::class.java))
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    progressBar.visibility =View.VISIBLE
                    Toast.makeText(this, "Login Successfully", Toast.LENGTH_SHORT).show()
                    sendToMain()
                } else {
                    // Sign in failed, display a message and update the UI
                    Log.d("Tag","signInWithPhoneAuthCredential: ${task.exception.toString()}")

                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                    }
                    // Update UI
                }
            }
    }

    private fun addTextChangeListener() {
        inputOTP1.addTextChangedListener(EditTextWatcher(inputOTP1))
        inputOTP2.addTextChangedListener(EditTextWatcher(inputOTP2))
        inputOTP3.addTextChangedListener(EditTextWatcher(inputOTP3))
        inputOTP4.addTextChangedListener(EditTextWatcher(inputOTP4))
        inputOTP5.addTextChangedListener(EditTextWatcher(inputOTP5))
        inputOTP6.addTextChangedListener(EditTextWatcher(inputOTP6))
    }


    inner class EditTextWatcher(private val view: View) :TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun afterTextChanged(p0: Editable?) {
            val text = p0.toString()
            when(view.id){
                R.id.otpEditText1 ->if (text.length==1) inputOTP2.requestFocus()
                R.id.otpEditText2 ->if (text.length==1) inputOTP3.requestFocus() else if (text.isEmpty()) inputOTP1.requestFocus()
                R.id.otpEditText3 ->if (text.length==1) inputOTP4.requestFocus() else if (text.isEmpty()) inputOTP2.requestFocus()
                R.id.otpEditText4 ->if (text.length==1) inputOTP5.requestFocus() else if (text.isEmpty()) inputOTP3.requestFocus()
                R.id.otpEditText5 ->if (text.length==1) inputOTP6.requestFocus() else if (text.isEmpty()) inputOTP4.requestFocus()
                R.id.otpEditText6 -> if(text.isEmpty()) inputOTP5.requestFocus()
            }
        }
    }
}