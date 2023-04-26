package com.example.teamup.ui.me

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.teamup.MainActivity
import com.example.teamup.databinding.FragmentMeBinding

class MeFragment : Fragment() {

    private var _binding: FragmentMeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(MeViewModel::class.java)

        _binding = FragmentMeBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textAbc
//        notificationsViewModel.text.observe(viewLifecycleOwner) {
//            textView.text =it
//        }

        binding.bgMe.setOnClickListener{
            Toast.makeText(MainActivity(),"12132",Toast.LENGTH_SHORT).show()
        }





        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}