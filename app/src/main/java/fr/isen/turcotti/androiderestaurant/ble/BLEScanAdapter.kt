package fr.isen.turcotti.androiderestaurant.ble

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.bluetooth.le.ScanResult
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.isen.turcotti.androiderestaurant.R
import java.util.ArrayList

internal class BLEScanAdapter(
    private val bleList: ArrayList<ScanResult>,
    val clickListener: (BluetoothDevice ) -> Unit,
    param: (Any) -> Boolean,
    param1: (Any) -> Unit,
    param2: (Any, Any) -> Unit
) : RecyclerView.Adapter<BLEScanAdapter.BLEScanViewHolder>() {

    inner class BLEScanViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        var rssi: TextView = view.findViewById(R.id.bleRssi)
        var name: TextView = view.findViewById(R.id.bleName)
        var address: TextView = view.findViewById(R.id.bleAddress)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BLEScanViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_blescan_adapter, parent, false)
        return BLEScanViewHolder(itemView)
    }
    @SuppressLint("MissingPermission")
    override fun onBindViewHolder(holder: BLEScanViewHolder, position: Int) {
        val result = bleList[position]
        holder.rssi.text =  result.rssi.toString()
        holder.address.text = result.device.address
        holder.name.text = result.device.name

        holder.itemView.setOnClickListener{
            clickListener(result.device)
        }
    }

    fun addResultToBleList(scanResult: ScanResult){
        val indexOfResult = bleList.indexOfFirst {
            it.device.address == scanResult.device.address
        }
        if (indexOfResult != -1){
            bleList[indexOfResult] = scanResult
            notifyItemChanged(indexOfResult)
        }else{
            bleList.add(scanResult)
            notifyItemInserted(bleList.size - 1)
        }

    }

    override fun getItemCount(): Int {
        return bleList.size
    }

}