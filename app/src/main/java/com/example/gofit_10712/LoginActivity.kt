package com.example.gofit_10712

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window.Callback
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.gofit_10712.database.RetrofitClient
import com.example.gofit_10712.databinding.ActivityLoginBinding
import com.example.gofit_10712.model.responseLogin
import retrofit2.Call
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private var  binding: ActivityLoginBinding? = null
    private var email : String = ""
    private var pass : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        binding!!.btnlogin.setOnClickListener {
            email = binding!!.etEmail.text.toString()
            pass = binding!!.etPassword.text.toString()

            when{
                email == ""->{
                    binding!!.etEmail.error = "Email tidak boleh kosong"
                }
                pass == ""->{
                    binding!!.etPassword.error = "Password tidak boleh kosong"
                }
                else ->{
                    binding!!.loading.visibility = View.VISIBLE
                    getData()
                }
            }
        }
    }

    private fun getData(){
        val api = RetrofitClient().getInstance()
        api.login(email, pass).enqueue(object : retrofit2.Callback<responseLogin> {
            override fun onResponse(call: Call<responseLogin>, response: Response<responseLogin>) {
                if(response.isSuccessful){
                    if(response.body()?.response == true){
                        binding!!.loading.visibility = View.GONE
                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        finish()
                    }else{
                        binding!!.loading.visibility = View.GONE
                        Toast.makeText(
                            this@LoginActivity,
                            "Login gagal, Silahkan periksa kembali email dan password anda!",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }else{
                    Toast.makeText(
                        this@LoginActivity,
                        "Login gagal, Terjadi kesalahan!",
                        Toast.LENGTH_LONG
                    ).show()
                }

            }

            override fun onFailure(call: Call<responseLogin>, t: Throwable) {
                Log.e("Pesan error", "${t.message}")
            }
        })
    }

}