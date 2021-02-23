package com.example.superpiano

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


class FullTunePianoKey : Fragment() {

    private lateinit var note: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            note = it.getString("NOTE") ?: "?"

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_full_tune_piano_key, container, false)
    }

    companion object {

        @JvmStatic
        fun newInstance(note:String) =
            FullTunePianoKey().apply {
                arguments = Bundle().apply {
                    putString("NOTE", note)
                }
            }
    }
}