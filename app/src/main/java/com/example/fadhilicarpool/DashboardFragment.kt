package com.example.fadhilicarpool

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import kotlinx.android.synthetic.main.fragment_dashboard.*

/**
 * A simple [Fragment] subclass.
 */
class DashboardFragment : Fragment() {

    lateinit var ridesFragment: RidesFragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity!!.title = "My Dashboard"

        cardDrive.setOnClickListener {
            startActivity(Intent(activity, DriverMapActivity::class.java))
        }

        cardRide.setOnClickListener {
            val intent = Intent(activity, PassengerMapActivity::class.java)
            activity!!.startActivity(intent)
        }

        cardHistory.setOnClickListener {
            ridesFragment = RidesFragment()
            fragmentManager!!
                .beginTransaction()
                .replace(R.id.fragment_container, ridesFragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit()
        }

        cardMap.setOnClickListener {
            startActivity(Intent(activity, FadhiliMapsActivity::class.java))
        }
    }

}
