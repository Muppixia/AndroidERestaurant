package fr.isen.turcotti.androiderestaurant.cart


import android.view.LayoutInflater

import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.isen.turcotti.androiderestaurant.R
import fr.isen.turcotti.androiderestaurant.databinding.ActivityCartAdapterBinding
import fr.isen.turcotti.androiderestaurant.cart.ItemCart


class CartAdapter(val itemCart: ArrayList<ItemCart>, val clickListener: (ItemCart) -> Unit)
    :RecyclerView.Adapter<CartAdapter.CartViewHolder>(){
    private lateinit var binding: ActivityCartAdapterBinding

    inner class CartViewHolder (binding: ActivityCartAdapterBinding) : RecyclerView.ViewHolder(binding.root) {
        val itemImageView: ImageView = binding.itemImageView
        val itemTextView: TextView = binding.itemTextView
        val nbProductView: TextView = binding.nbProductView
        val priceView: TextView = binding.priceView
        val deleteButton: ImageView = binding.deleteButton

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        binding = ActivityCartAdapterBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item = itemCart[position]
        if (item.cartItem.name_fr.length > 13) {
            holder.itemTextView.text = item.cartItem.name_fr.subSequence(0,13)
        }
        else {
            holder.itemTextView.text = item.cartItem.name_fr
        }
        holder.nbProductView.text = item.quantity.toString()
        var price = item.cartItem.prices[0].price.toFloat() * item.quantity
        holder.priceView.text = price.toString()

        val url = item.cartItem.images[0]
        Picasso.get().load(url.ifEmpty { null }).fit().centerCrop()
            .placeholder(R.drawable.ic_launcher_foreground)
            .error(R.drawable.ic_launcher_background)
            .into(holder.itemImageView);

        holder.deleteButton.setOnClickListener {
            itemCart.remove(itemCart[position])
            notifyItemRemoved(position)
            clickListener(item)
        }
    }

    override fun getItemCount(): Int {
        return itemCart.size
    }
}