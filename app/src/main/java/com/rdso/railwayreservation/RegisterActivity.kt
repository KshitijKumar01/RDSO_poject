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
import com.rdso.railwayreservation.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding : ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        binding.tvLogin.setOnClickListener{
            onBackPressed()
        }

        binding.btnRegister.setOnClickListener{
            when{
                TextUtils.isEmpty(binding.etRegisterEmail.text.toString().trim{it <= ' '})->{
                    Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show()
                }

                TextUtils.isEmpty(binding.etRegisterPassword.text.toString().trim{it <= ' '})->{
                    Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show()
                }

                else ->{
                    val email : String = binding.etRegisterEmail.text.toString().trim { it <= ' ' }
                    val password : String = binding.etRegisterPassword.text.toString().trim { it <= ' ' }

                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(
                            OnCompleteListener<AuthResult> {task ->
                                if(task.isSuccessful){
                                    val firebaseUser : FirebaseUser = task.result!!.user!!
                                    Toast.makeText(this, "User registered successfully", Toast.LENGTH_SHORT).show()

                                    val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    intent.putExtra("user_id", firebaseUser.uid)
                                    intent.putExtra("email_id", email)
                                    startActivity(intent)
                                    finish()
                                }else{
                                    Toast.makeText(this, task.exception!!.message.toString(), Toast.LENGTH_SHORT).show()
                                }
                            })
                }
            }
        }
    }
}