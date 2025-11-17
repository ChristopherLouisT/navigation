package paba.meet2.uts.c14230232.meet10
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso


class adapterBahan(private val dataList: List<dcBahan>,
                   private val onBuyClick: ((dcBahan) -> Unit)? = null,
                    private val buttonText: String = "Add to Cart"
) : RecyclerView.Adapter<adapterBahan.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvBahan: TextView = itemView.findViewById(R.id.tvBahan)
        val tvKategori: TextView = itemView.findViewById(R.id.tvKategori)
        val tvGambar: ImageView = itemView.findViewById(R.id.tvGambar)
        val btnOneItem: Button = itemView.findViewById(R.id.btnOneItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.bahan_recycler, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        holder.tvBahan.text = item.nama
        holder.tvKategori.text = item.kategori

        holder.btnOneItem.text = buttonText

        holder.btnOneItem.setOnClickListener {
            onBuyClick?.invoke(item)
        }
        Picasso.get().load(item.foto).resize(100,100).into(holder.tvGambar)
    }

    override fun getItemCount(): Int = dataList.size
}