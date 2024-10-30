package febri.uray.bedboy.core.data.source.remote.network

import febri.uray.bedboy.core.data.source.remote.response.RequestGemini
import febri.uray.bedboy.core.data.source.remote.response.Responses
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("models/gemini-pro:generateContent")
    suspend fun getKhodam(
        @Body userRequest: RequestGemini
    ): Responses
}