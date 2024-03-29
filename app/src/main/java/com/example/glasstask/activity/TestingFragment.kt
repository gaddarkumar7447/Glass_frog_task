package com.example.glasstask.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.example.glasstask.databinding.FragmentTestingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TestingFragment : Fragment() {
    private var _binding: FragmentTestingBinding? = null
    private val binding: FragmentTestingBinding = requireNotNull(_binding)
    private lateinit var composeView: ComposeView
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return ComposeView(requireContext()).also {
            composeView = it
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        composeView.setContent {
            FirstScreen()
        }
    }
}


@Composable
fun FirstScreen() {
    Text(text = "Gaddar kumar Chaudhary")
}