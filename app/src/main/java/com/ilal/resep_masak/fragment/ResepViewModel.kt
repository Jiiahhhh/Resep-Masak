package com.ilal.resep_masak.fragment

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.ilal.resep_masak.adapter.ResepAdapter
import com.ilal.resep_masak.model.ResepModel
import com.ilal.resep_masak.model.Result
import com.ilal.resep_masak.retrofit.ResepClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ResepViewModel : ViewModel(){

    interface OnAdapterListener {
        fun onClick(item: Result)
    }

    fun getDataFromApi(recyclerView: RecyclerView, listener: OnAdapterListener){
        ResepClient.INSTANCE.getDataResep()
            .enqueue(object : Callback<ResepModel> {
                override fun onFailure(call: Call<ResepModel>, t: Throwable) {
                    Log.d("ResepViewModel", "onFailure")
                }

                override fun onResponse(call: Call<ResepModel>, response: Response<ResepModel>) {
                    val data = response.body()?.results
                    recyclerView.adapter = ResepAdapter(data as ArrayList<Result>, object : ResepAdapter.OnAdapterListener {
                        override fun onClick(item: Result) {
                            listener.onClick(item)
                        }

                    })
                }
            })
    }
}