package org.d3if4077.mobpro2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.d3if4077.mobpro2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_maps) {
            startActivity(Intent(this, MapsActivity::class.java))
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}