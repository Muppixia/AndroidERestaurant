package fr.isen.turcotti.androiderestaurant.ble

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothManager
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import fr.isen.turcotti.androiderestaurant.R
import fr.isen.turcotti.androiderestaurant.databinding.ActivityBlescanBinding


class BLEScanActivity(toString: String, characteristics: MutableList<BluetoothGattCharacteristic>) : AppCompatActivity() {

    private lateinit var binding: ActivityBlescanBinding
    private var isScanning = false

    private var adapter: BLEScanAdapter? = null

    private val bluetoothAdapter: BluetoothAdapter? by lazy {
        val bluetoothManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        bluetoothManager.adapter
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBlescanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        when {
            bluetoothAdapter?.isEnabled == true -> {
                binding.bleScanStateImg.setOnClickListener {
                    startLeScanBLEWithPermission(!isScanning)
                }
                binding.bleScanStateTitle.setOnClickListener {
                    startLeScanBLEWithPermission(!isScanning)
                }
            }
            bluetoothAdapter != null ->
                askBluetoothPermission()
            else -> {
                displayBLEUnAvailable()
            }
        }

        /*adapter = BLEScanAdapter(arrayListOf(),
            {
                val intent = Intent(this, BLEDeviceActivity::class.java)
                intent.putExtra(DEVICE_KEY, it)
                startActivity(intent)
            },
            { characteristic -> gatt.readCharacteristic(characteristic)},
            { characteristic -> writeIntoCharacteristic(gatt, characteristic)}) { characteristic, enable ->
            toggleNotificationOnCharacteristic(
                gatt,
                characteristic,
                enable
            )
        }*/
        binding.bleScanList.layoutManager = LinearLayoutManager(this)
        binding.bleScanList.adapter = adapter
    }

    override fun onStop() {
        super.onStop()
        startLeScanBLEWithPermission(false)
    }


    private fun startLeScanBLEWithPermission(enable: Boolean) {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            startLeScanBLE(enable)
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION
                ),
                ALL_PERMISSIONS_REQUEST_CODE,
            )
        }
    }

    @SuppressLint("MissingPermission")
    private fun startLeScanBLE(enable: Boolean) {
        bluetoothAdapter?.bluetoothLeScanner?.apply {
            if (enable) {
                isScanning = true
                startScan(scanCallback)
            } else {
                isScanning = false
                stopScan(scanCallback)
            }
            handlePlayPauseAction()
        }
    }


    private val scanCallback = object : ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            Log.d("BLEScanActivity", "result : ${result.device.address}, rssi : ${result.rssi}")
            adapter?.apply{
                addResultToBleList(result)
            }

        }
    }

    private fun askBluetoothPermission() {
        val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.BLUETOOTH_CONNECT
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            startActivityForResult(enableBtIntent, ENABLE_BLUETOOTH_REQUEST_CODE)
        }
    }

    private fun displayBLEUnAvailable() {
        binding.bleScanStateImg.isVisible = false
        binding.bleScanStateTitle.text = getString(R.string.ble_scan_device_error)
        binding.scanBar.isIndeterminate = false
    }

    private fun handlePlayPauseAction() {
        if (isScanning) {
            binding.bleScanStateImg.setImageResource(R.drawable.ic_pause)
            binding.bleScanStateTitle.text = getString(R.string.bleScanPause)
            binding.scanBar.isIndeterminate = true
        } else {
            binding.bleScanStateImg.setImageResource(R.drawable.ic_play)
            binding.bleScanStateTitle.text = getString(R.string.bleScanPlay)
            binding.scanBar.isIndeterminate = false
        }
    }

    companion object {
        private const val ENABLE_BLUETOOTH_REQUEST_CODE = 1
        private const val ALL_PERMISSIONS_REQUEST_CODE = 100
        const val DEVICE_KEY = "device"
    }

}
