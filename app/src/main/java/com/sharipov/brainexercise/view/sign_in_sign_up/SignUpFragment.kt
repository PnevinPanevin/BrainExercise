package com.sharipov.brainexercise.view.sign_in_sign_up


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sharipov.brainexercise.R

class SignUpFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
//            .apply {
//                signInButton.setOnClickListener {
//                    findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)
//                }
//            }
    }
}
