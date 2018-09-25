package com.toldas.sampleapplication.ui.edit


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.toldas.sampleapplication.R
import com.toldas.sampleapplication.data.model.MapLocation
import com.toldas.sampleapplication.di.Injectable

class EditFragment : Fragment(), Injectable {

    companion object {
        private const val OBJECT = "ID"
    }

    private lateinit var mapLocation: MapLocation

    internal fun newInstance(mapLocation: MapLocation): EditFragment {
        val fragment = EditFragment()
        val args = Bundle()
        args.putParcelable(OBJECT, mapLocation)
        fragment.arguments = args
        return fragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mapLocation = arguments!!.getParcelable(OBJECT)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit, container, false)
    }


}
