package paba.meet2.uts.c14230232.meet10

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CartFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CartFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var rvCart: RecyclerView
    private lateinit var cartSP: SharedPreferences
    private var arCart = ArrayList<dcBahan>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvCart = view.findViewById<RecyclerView>(R.id.rvCart)

        val cartSP = requireContext().getSharedPreferences("cartSP", MODE_PRIVATE)
        val gson = Gson()

        val isiCartSP = cartSP.getString("dt_cart", null)
        val type = object : TypeToken<ArrayList<dcBahan>>() {}.type

        val listCart: ArrayList<dcBahan> =
            if (isiCartSP != null) gson.fromJson(isiCartSP, type)
            else arrayListOf()

        tampilkanCart(listCart)
    }

    private fun tampilkanCart(listCart: ArrayList<dcBahan>) {
        rvCart.layoutManager = LinearLayoutManager(requireContext())
        rvCart.adapter = adapterBahan(listCart)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CartFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}