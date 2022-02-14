package org.d3if4077.mobpro2

import  androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.InternalCoroutinesApi
import org.d3if4077.mobpro.MainDialog
import org.d3if4077.mobpro2.data.Mahasiswa
import org.d3if4077.mobpro2.data.MahasiswaDb
import org.d3if4077.mobpro2.databinding.ActivityMainBinding

@InternalCoroutinesApi
class MainActivity : AppCompatActivity(), MainDialog.DialogListener {

        private lateinit var binding: ActivityMainBinding
        private lateinit var myAdapter: MainAdapter
        private var actionMode: ActionMode? = null

        private val viewModel: MainViewModel by lazy {
            val dataSource = MahasiswaDb.getInstance().dao
            val factory = MainViewModelFactory(dataSource)
            ViewModelProvider(this, factory).get(MainViewModel::class.java)
        }

        private val handler = object : MainAdapter.ClickHandler {
            override fun onClick(position: Int, mahasiswa: Mahasiswa) {
                if (actionMode != null) {
                    myAdapter.toggleSelection(position)
                    if (myAdapter.getSelection().isEmpty())
                        actionMode?.finish()
                    else
                        actionMode?.invalidate()
                    return
                }

                val message = getString(R.string.mahasiswa_klik, mahasiswa.nama)
                Toast.makeText(this@MainActivity, message, Toast.LENGTH_LONG).show()
            }

            override fun onLongClick(position: Int): Boolean {
                if (actionMode != null) return false

                myAdapter.toggleSelection(position)
                actionMode = startActionMode(actionModeCallback)
                return true
            }
        }

        private val actionModeCallback = object : ActionMode.Callback {
            override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
                if (item?.itemId == R.id.menu_delete) {
                    deleteData()
                    return true
                }
                return false
            }

            override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                mode?.menuInflater?.inflate(R.menu.main_mode, menu)
                return true
            }

            override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                mode?.title = myAdapter.getSelection().size.toString()
                return true
            }

            override fun onDestroyActionMode(mode: ActionMode?) {
                actionMode = null
                myAdapter.resetSelection()
            }
        }

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)

            binding.fab.setOnClickListener {
                MainDialog().show(supportFragmentManager, "MainDialog")
            }

            myAdapter = MainAdapter(handler)
            with(binding.recyclerView) {
                addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
                setHasFixedSize(true)
                adapter = myAdapter
            }

            viewModel.data.observe(this, {
                myAdapter.submitList(it)
                binding.emptyView.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE
            })
        }

        override fun processDialog(mahasiswa: Mahasiswa) {
            viewModel.insertData(mahasiswa)
        }

        private fun deleteData() = AlertDialog.Builder(this).apply {
            setMessage(R.string.pesan_hapus)
            setPositiveButton(R.string.hapus) { _, _ ->
                viewModel.deleteData(myAdapter.getSelection())
                actionMode?.finish()
            }
            setNegativeButton(R.string.batal) { dialog, _ ->
                dialog.cancel()
                actionMode?.finish()
            }
            show()
        }
    }