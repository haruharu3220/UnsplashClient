package com.example.unsplashclient.domain.use_case

import com.example.unsplashclient.common.NetworkResponse
import com.example.unsplashclient.data.remote.toPhotos
import com.example.unsplashclient.domain.model.Photo
import com.example.unsplashclient.domain.repository.PhotoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.http.Query
import javax.inject.Inject

class SearchPhotosUseCase @Inject constructor(
    private val repository: PhotoRepository,
){
    //UseCaseは1クラスで一つの機能を持つ

    //invokeメソッドは、クラスインスタンスを関数のように呼び出すことができる特別なメソッドです。この場合、SearchPhotosUseCaseインスタンスに対してクエリ文字列を渡して呼び出すと、invokeメソッドが実行されます。
    //このメソッドは、文字列のクエリを受け取り、Flow<NetworkResponse<List<Photo>>>を返します。Flowは非同期データストリームを表し、複数の値を順次発行することができます
    operator fun invoke(query: String): Flow<NetworkResponse<List<Photo>>> = flow{
        try{
            //emitメソッドは、Flowの中で値を発行するために使います。この例では、NetworkResponseのインスタンスを発行しています。
            emit(NetworkResponse.Loading<List<Photo>>())
            //次に、リポジトリから写真データを取得し、それをNetworkResponse.Successとして発行します。
            val photos = repository.searchPhotos(query).toPhotos()
            emit(NetworkResponse.Success<List<Photo>>(photos))
        } catch (e: Exception){
            emit(NetworkResponse.Failure<List<Photo>>(e.message.toString()))
        }





    }
}