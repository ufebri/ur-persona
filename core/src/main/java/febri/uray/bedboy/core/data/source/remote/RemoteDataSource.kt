package febri.uray.bedboy.core.data.source.remote

import android.util.Log
import febri.uray.bedboy.core.data.source.remote.network.ApiResponse
import febri.uray.bedboy.core.data.source.remote.network.ApiService
import febri.uray.bedboy.core.data.source.remote.response.Responses
import febri.uray.bedboy.core.util.DataMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(
    private val apiService: ApiService
) {
    val TAG = "RemoteDataSource"

    suspend fun getKhodam(prompt: String): Flow<ApiResponse<Responses>> {
        return flow {
            val response = apiService.getKhodam(DataMapper.mapRequestKhodam(prompt))
            try {
                emit(ApiResponse.Success(response))
            } catch (e: Exception) {
                emit(ApiResponse.Error("error"))
                Log.e(TAG, "getKhodam: $e")
            }
        }.flowOn(Dispatchers.IO)
    }
}

