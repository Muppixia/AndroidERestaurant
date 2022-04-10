package fr.isen.turcotti.androiderestaurant.ble

import android.bluetooth.BluetoothGattCharacteristic
import android.icu.text.CaseMap
import android.widget.ExpandableListAdapter
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup

class BLEService(var name: String, var characteristics: MutableList<BluetoothGattCharacteristic>):
        ExpandableGroup<BluetoothGattCharacteristic>(name, characteristics){
}