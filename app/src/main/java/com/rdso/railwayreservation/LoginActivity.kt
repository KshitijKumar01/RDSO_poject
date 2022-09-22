package com.rdso.railwayreservation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.rdso.railwayreservation.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        binding.tvRegister.setOnClickListener{
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogin.setOnClickListener{
            when{
                TextUtils.isEmpty(binding.etLoginEmail.text.toString().trim{it <= ' '})->{
                    Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show()
                }

                TextUtils.isEmpty(binding.etLoginPassword.text.toString().trim{it <= ' '})->{
                    Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show()
                }
                else ->{
                    val email : String = binding.etLoginEmail.text.toString().trim { it <= ' ' }
                    val password : String = binding.etLoginPassword.text.toString().trim { it <= ' ' }

                    FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(
                            OnCompleteListener<AuthResult> {task ->
                                if(task.isSuccessful){
                                    Toast.makeText(this@LoginActivity, "User registered successfully", Toast.LENGTH_SHORT).show()

                                    val intent = Intent(this ,MainActivity::class.java)
                                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    intent.putExtra("user_id", FirebaseAuth.getInstance().currentUser!!.uid)
                                    intent.putExtra("email_id", email)
                                    startActivity(intent)
                                    finish()
                                }else{
                                    Toast.makeText(this@LoginActivity, task.exception!!.message.toString(), Toast.LENGTH_SHORT).show()
                                }
                            })
                }

            }
        }
    }
}