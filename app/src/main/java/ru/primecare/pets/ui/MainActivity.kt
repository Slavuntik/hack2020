package ru.primecare.pets.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.primecare.pets.data.DataProvider
import ru.primecare.pets.databinding.ActivityMainBinding
import ru.primecare.pets.ui.adapters.PetsListAdapter
import ru.primecare.pets.ui.heplers.PermissionsManager

class MainActivity : AppCompatActivity() {

    lateinit var binding:ActivityMainBinding
    lateinit var adapter:PetsListAdapter
    val dataProvider = DataProvider()
    lateinit var permissionsManager:PermissionsManager
    val scannerRequsetCode = 15

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        permissionsManager = PermissionsManager(this)
        adapter = PetsListAdapter(mutableListOf())
        binding.petsList.adapter = adapter
        adapter.onItemClick = {
            val formIntent = Intent(this, PetFormActivity::class.java)
            formIntent.putExtra("cardNumber", it.cardNumber)
            formIntent.putExtra("petId", it.petId)
            startActivity(formIntent)
        }
        binding.scanner.setOnClickListener {
            startActivityForResult(Intent(this, ScannerActivity::class.java), scannerRequsetCode)
        }
        binding.profile.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
        lifecycleScope.launch{
            loadData()
        }

        binding.searchField.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                search(binding.searchField.text.toString())
                val imm: InputMethodManager = v.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(v.windowToken, 0)
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }

    }

    suspend fun loadData(){
        adapter.data.addAll(dataProvider.getPetList())
        withContext(Dispatchers.Main){
            adapter.notifyDataSetChanged()
            binding.loading.visibility = View.GONE
        }
    }

    fun search(code: String){
        val filteredData = dataProvider.filter(code)
        adapter.data.clear()
        if(code.isEmpty() || filteredData.isEmpty()){
            binding.loading.visibility = View.VISIBLE
            binding.petsList.visibility = View.GONE
            lifecycleScope.launch {
                adapter.data.addAll(dataProvider.getPetList())
                withContext(Dispatchers.Main){
                    adapter.notifyDataSetChanged()
                    binding.loading.visibility = View.GONE
                    binding.petsList.visibility = View.VISIBLE
                }
            }
        }else{
            adapter.data.addAll(filteredData)
        }

    }
    override fun onResume() {
        super.onResume()
        if(!permissionsManager.permissionsGranted())
            permissionsManager.requestPermissions()

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(!permissionsManager.permissionsGranted()){
            permissionsManager.requestPermissions()
        }
        if(requestCode == scannerRequsetCode){
            if(data!=null){
                val code = data.getStringExtra("code")?:""
            }
        }
    }
    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<String>,
            grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionsManager.requestPermissionResult(requestCode, permissions, grantResults)
    }
}