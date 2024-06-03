package com.drg.shlapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.drg.shlapp.DetailSuperheroActivity.Companion.EXTRA_ID
import com.drg.shlapp.ReciclerView.SuperheroAdapter
import com.drg.shlapp.databinding.ActivityIntroBinding
import com.drg.shlapp.databinding.ActivityMainBinding
import com.drg.shlapp.retrofit.ApiService
import com.drg.shlapp.retrofit.SuperHeroDataResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var bindingIntro: ActivityIntroBinding
    private lateinit var binding: ActivityMainBinding
    private lateinit var retrofit: Retrofit
    private lateinit var adapter: SuperheroAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindingIntro = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(bindingIntro.root)

        // Dentro de tu actividad o clase
        val handler = Handler(Looper.getMainLooper())

        handler.postDelayed({
            // Tu código aquí que quieres ejecutar después de 2 segundos
            binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)

            retrofit = getRetrofit() //Inicializa el Retrofit
            initUI()
        }, 2000)
    }

    private fun initUI() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchByName(query.orEmpty())
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        //Adapter para cada Card de cada superhero
        adapter = SuperheroAdapter{ superHeroId -> navigateToDetail(superHeroId)}
        binding.rvSuperHero.setHasFixedSize(true)
        binding.rvSuperHero.layoutManager = LinearLayoutManager(this)
        binding.rvSuperHero.adapter = adapter
    }

    private fun searchByName(query: String) {

        binding.progressBar.isVisible = true

        CoroutineScope(Dispatchers.IO).launch{
            //crea una implementation de las peticiones definidas en la interfaz ApiService y llama al metodo getSuperheroes
            //Y almacena la respuesta de la peticion
            val myResponse: Response<SuperHeroDataResponse> = retrofit.create(ApiService::class.java).getSuperheroes(query)

            if (myResponse.isSuccessful) {
                Log.i("retrofit", "FUNCIONA")

                //Almacena el contenido de la respuesta con los datos de la data class SuperHeroDataResponse
                val response: SuperHeroDataResponse? = myResponse.body()

                if (response != null){
                    Log.i("retrofit response", response.toString())
                    runOnUiThread {  //Corre en el hilo principal lo tiene entre llaves
                        binding.progressBar.isVisible = false
                        adapter.updateList(response.superheros)
                    }
                }
            }
            else { Log.i("retrofit", "NO FUNCIONA") }
        }
    }

    private fun getRetrofit (): Retrofit{
        return Retrofit.Builder() //Constructor Retrofit
            .baseUrl("https://superheroapi.com/") //Url base de la API
            .addConverterFactory(GsonConverterFactory.create()) //Conversor de JSON a Kotlin
            .build() //Lo instancia
    }

    private fun navigateToDetail(id: String){
        val intent = Intent(this, DetailSuperheroActivity::class.java)
        intent.putExtra(EXTRA_ID, id)
        startActivity(intent)
    }
}