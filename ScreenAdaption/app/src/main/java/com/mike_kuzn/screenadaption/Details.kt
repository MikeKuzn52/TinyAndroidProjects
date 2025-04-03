package com.mike_kuzn.screenadaption

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class Details : Fragment() {

    fun getPosition(): Int {
        return requireArguments().getInt("position", 0)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.details, container, false).apply {
            findViewById<TextView>(R.id.Text).text =
                resources.getStringArray(R.array.content)[getPosition()]
        }
    }

    companion object {
        fun newInstance(pos: Int): Details {
            val details = Details()
            val args = Bundle()
            args.putInt("position", pos)
            details.setArguments(args)
            return details
        }
    }

}