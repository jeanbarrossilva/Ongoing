package com.jeanbarrossilva.ongoing

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jeanbarrossilva.ongoing.databinding.ActivityOngoingBinding

internal class OngoingActivity: AppCompatActivity() {
    private var binding: ActivityOngoingBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOngoingBinding.inflate(layoutInflater)
        setContentView(binding?.root)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}