package com.sharipov.brainexercise.view.sign_in_sign_up


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.sharipov.brainexercise.R
import kotlinx.android.synthetic.main.fragment_sign_up.view.*

class SignInFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
//            .apply {
//                signUpButton.setOnClickListener {
//                    findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
//                }
//            }
    }
}
