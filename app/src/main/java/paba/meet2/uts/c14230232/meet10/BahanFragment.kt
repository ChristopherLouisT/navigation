package paba.meet2.uts.c14230232.meet10

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.GestureDetector
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import paba.meet2.uts.c14230232.meet10.databinding.ActivityMainBinding
import paba.meet2.uts.c14230232.meet10.databinding.FragmentBahanBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BahanFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BahanFragment : Fragment() {
    private var binding : FragmentBahanBinding? = null
    private var param1: String? = null
    private var param2: String? = null
    var data = mutableListOf<String>()

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
        binding = FragmentBahanBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        data.addAll(listOf("Bahan1", "Bahan2", "Bahan3", "Bahan4", "Bahan5"))

        val lvAdapter  = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, data)
        binding!!.lv1.adapter = lvAdapter

        binding!!.btnTambah.setOnClickListener {
            val namaBahan = binding!!.inputBahan.text.toString().trim()
            val kategori = binding!!.inputKategori.text.toString().trim()

            if (namaBahan.isEmpty() || kategori.isEmpty()) {
                Toast.makeText(requireContext(), "Isi semua field dulu!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val item = "$namaBahan - $kategori"
            data.add(item)
            lvAdapter.notifyDataSetChanged()

            // Kosongkan setelah tambah
            binding!!.inputBahan.text.clear()
            binding!!.inputKategori.text.clear()
        }

        val gestureDetector = GestureDetector(
            requireContext(), object: GestureDetector.SimpleOnGestureListener() {
                override fun onDoubleTap(e: MotionEvent): Boolean {
                    val position = binding!!.lv1.pointToPosition(e.x.toInt(), e.y.toInt())
                    if (position != ListView.INVALID_POSITION) {
                        val selectedItem = data[position]
                        showActionDialog(position, selectedItem, data, lvAdapter)
                    }
                    return true
                }
            }
        )

        binding!!.lv1.setOnTouchListener { _, event ->
            gestureDetector.onTouchEvent(event)
            false
        }
    }

    private fun showUpdateDialog(
        position: Int,
        oldValue: String,
        data:MutableList<String>,
        adapter: ArrayAdapter<String>
    ) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Update Kategori")
        builder.setMessage("Pilih tindakan yang ingin dilakukan")

        val parts = oldValue.split(" - ")
        val namaBahan = parts[0]
        val kategoriLama = if (parts.size > 1) parts[1] else ""

        val layout = LinearLayout(requireContext())
        layout.orientation = LinearLayout.VERTICAL
        layout.setPadding(50, 40, 50, 10)

        val tvOld = TextView(requireContext())
        tvOld.text = "Kategori lama: $kategoriLama"
        tvOld.textSize = 16f

        val etNew = EditText(requireContext())
        etNew.hint = "Masukkan data baru"
        etNew.setText(kategoriLama)

        layout.addView(tvOld)
        layout.addView(etNew)

        builder.setView(layout)

        builder.setPositiveButton("Simpan") { dialog, _ ->
            val newCategory = etNew.text.toString().trim()
            if (newCategory.isNotEmpty()) {
                val newValue = "$namaBahan - $newCategory"
                data[position] = newValue
                adapter.notifyDataSetChanged()
                Toast.makeText(requireContext(), "Kategori Diperbarui Menjadi: $newCategory", Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(requireContext(), "Data baru tidak boleh kosong!", Toast.LENGTH_SHORT).show()
            }
            dialog.dismiss()
        }

        builder.setNegativeButton("Batal") { dialog, _ ->
            dialog.dismiss()
        }

        builder.create().show()
    }

    private fun showActionDialog (
        position: Int,
        selectedItem: String,
        data:MutableList<String>,
        adapter: ArrayAdapter<String>
    ) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("ITEM $selectedItem")
        builder.setMessage("Pilih tindakan yang ingin dilakukan")


        builder.setPositiveButton("update") {_, _ ->
            showUpdateDialog(position, selectedItem, data, adapter)
        }
        builder.setNegativeButton("Hapus") {_, _ ->
            data.removeAt(position)
            adapter.notifyDataSetChanged()
            Toast.makeText(requireContext(), "Hapus Item $selectedItem", Toast.LENGTH_SHORT).show()
        }
        builder.setNeutralButton("Batal") {dialog, _ ->
            dialog.dismiss()
        }

        builder.create().show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BahanFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}