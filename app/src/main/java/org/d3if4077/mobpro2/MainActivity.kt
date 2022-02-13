package org.d3if4077.mobpro2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.GenericTransitionOptions.with
import com.bumptech.glide.Glide
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_main.*
import org.d3if4077.mobpro2.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {



    private val contract = FirebaseAuthUIActivityResultContract()
    private val signInLauncher = registerForActivityResult(contract) { }
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.login.setOnClickListener { mulaiLogin() }
        viewModel.authState.observe(this, { updateUI(it) })

    }

    private fun updateUI(user: FirebaseUser?) = with(binding) {
        if (user == null) {
           tvNama.visibility = View.GONE
            imageView.visibility = View.GONE
            login.text = getString(R.string.login)
        }
        else {
            tvNama.text = user.displayName
            Glide.with(this@MainActivity).load(user.photoUrl).into(imageView)
            tvNama.visibility = View.VISIBLE
            imageView.visibility = View.VISIBLE
            login.text = getString(R.string.logout)
        }
    }

    private fun mulaiLogin() {
        val providers = arrayListOf(AuthUI.IdpConfig.GoogleBuilder().build())
        val intent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build()
        signInLauncher.launch(intent)
    }

}