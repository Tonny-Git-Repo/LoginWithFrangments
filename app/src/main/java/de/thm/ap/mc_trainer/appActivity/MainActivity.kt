package de.thm.ap.mc_trainer.appActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import de.thm.ap.mc_trainer.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}