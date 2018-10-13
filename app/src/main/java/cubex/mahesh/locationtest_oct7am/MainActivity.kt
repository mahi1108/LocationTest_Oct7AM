package cubex.mahesh.locationtest_oct7am

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Vibrator
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var status = ContextCompat.checkSelfPermission(
                this@MainActivity,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
        if(status==PackageManager.PERMISSION_GRANTED) {
            getLocation()
        }else{
            ActivityCompat.requestPermissions(
                    this@MainActivity,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION),
                    123)
        }
                // vibrator logic
        button.setOnClickListener {
            var vib = getSystemService(Context.VIBRATOR_SERVICE)
                                        as Vibrator
            vib.vibrate(10000)
        }


    }

    override fun onRequestPermissionsResult(requestCode: Int,
                       permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode,
                                                                permissions, grantResults)
         if(grantResults[0]==PackageManager.PERMISSION_GRANTED)
         {
             getLocation()
         }
    }

    @SuppressLint("MissingPermission")
    fun  getLocation( )
    {
        var lManager = getSystemService(Context.LOCATION_SERVICE)
                                                                                        as LocationManager
       /*  LocationManager lManager = (LocationManager)
        getSystemService(Context.LOCATION_SERVICE) */

        lManager.getLastKnownLocation(
                LocationManager.NETWORK_PROVIDER)
        lManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                1000.toLong(),1.toFloat(),
                object : LocationListener {
                    override fun onLocationChanged(p0: Location?) {
                                textView.text = p0!!.latitude.toString()
                                textView2.text = p0!!.longitude.toString()
                                lManager.removeUpdates(this)
                    }
                    override fun onProviderEnabled(p0: String?) {
                    }
                    override fun onProviderDisabled(p0: String?) {
                    }
                    override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {
                    }
        })
    }
}
