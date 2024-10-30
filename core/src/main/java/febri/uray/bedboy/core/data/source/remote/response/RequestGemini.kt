package febri.uray.bedboy.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class RequestGemini(
    @SerializedName("contents") var contents: ArrayList<Contents> = arrayListOf()
)

data class Contents(
    @SerializedName("role") var role: String? = null,
    @SerializedName("parts") var parts: ArrayList<Parts> = arrayListOf()
)