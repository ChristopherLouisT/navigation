package paba.meet2.uts.c14230232.meet10

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.edit
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
    private lateinit var boughtSP: SharedPreferences

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

        val gson = Gson()
        val type = object : TypeToken<ArrayList<dcBahan>>() {}.type

        cartSP = requireContext().getSharedPreferences("cartSP", MODE_PRIVATE)
        val isiCartSP = cartSP.getString("dt_cart", null)

        boughtSP = requireContext().getSharedPreferences("boughtSP", MODE_PRIVATE)
        val isiBoughtSP = boughtSP.getString("dt_bought", null)

        val listCart: ArrayList<dcBahan> =
            if (isiCartSP != null) gson.fromJson(isiCartSP, type)
            else arrayListOf()

        val boughtList: ArrayList<dcBahan> =
            if (isiBoughtSP != null) gson.fromJson(isiBoughtSP, type)
            else arrayListOf()


        tampilkanCart(listCart)
    }

    private fun tampilkanCart(listCart: ArrayList<dcBahan>) {
        rvCart.layoutManager = LinearLayoutManager(requireContext())
        rvCart.adapter = adapterBahan(
            listCart,
            {selectedItem -> buyOneItem(selectedItem, listCart)},
            "Checkout Item"
            )
    }

    fun buyOneItem(item: dcBahan, listCart: ArrayList<dcBahan>) {
        val gson = Gson()

        val isiBought = boughtSP.getString("dt_bought", null)
        val type = object : TypeToken<ArrayList<dcBahan>>() {}.type

        val boughtList: ArrayList<dcBahan> =
            if (isiBought != null) gson.fromJson(isiBought, type)
            else arrayListOf()

        boughtList.add(item)

        boughtSP.edit {
            putString("dt_bought", gson.toJson(boughtList))
        }

        listCart.remove(item)
        cartSP.edit {
            putString("dt_cart", gson.toJson(listCart))
        }

        Log.d("DEBUG_SP", "Saving BoughtSP: " + gson.toJson(boughtList))
        Log.d("DEBUG_SP", "Read Back: " + boughtSP.getString("dt_bought", "NULL"))

        Toast.makeText(requireContext(), "Added: ${item.nama}", Toast.LENGTH_SHORT).show()
        rvCart.adapter?.notifyDataSetChanged()
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