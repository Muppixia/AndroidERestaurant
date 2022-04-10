package fr.isen.turcotti.androiderestaurant.ble

import android.bluetooth.BluetoothGattCharacteristic
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder
import fr.isen.turcotti.androiderestaurant.R

class BLEServiceAdapter(bleService: MutableList<BLEService>) :
    ExpandableRecyclerViewAdapter<BLEServiceAdapter.ServiceViewHolder, BLEServiceAdapter.CharacteristicViewHolder>(bleService){

    class ServiceViewHolder(itemView: View): GroupViewHolder(itemView) {
        val serviceName: TextView = itemView.findViewById(R.id.serviceName)
        val serviceUUID: TextView = itemView.findViewById(R.id.serviceUUID)
        private val serviceArrow: View = itemView.findViewById(R.id.serviceArrow)
    }

    class CharacteristicViewHolder(itemView: View): ChildViewHolder(itemView) {
        val characteristicName: TextView = itemView.findViewById(R.id.characteristicName)
        val characteristicUUID: TextView = itemView.findViewById(R.id.characteristicUUID)
        val characteristicReadAction: Button = itemView.findViewById(R.id.readAction)
        val characteristicWriteAction: Button = itemView.findViewById(R.id.writeAction)
    }

    override fun onCreateGroupViewHolder(parent: ViewGroup, viewType: Int): ServiceViewHolder =
        ServiceViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.activity_bleservice, parent, false) )


    override fun onCreateChildViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CharacteristicViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_blecharacteristic, parent, false)
        return CharacteristicViewHolder(itemView)
    }

    override fun onBindChildViewHolder(
        holder: CharacteristicViewHolder,
        flatPosition: Int,
        group: ExpandableGroup<*>,
        childIndex: Int
    ) {
        val characteristic = group.items[childIndex] as BluetoothGattCharacteristic
        holder.characteristicName.text = characteristic.uuid.toString()
    }

    override fun onBindGroupViewHolder(
        holder: ServiceViewHolder,
        flatPosition: Int,
        group: ExpandableGroup<*>
    ) {
        holder.serviceName.text = group.title
    }
}