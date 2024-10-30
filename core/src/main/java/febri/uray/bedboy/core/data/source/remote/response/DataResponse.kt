package febri.uray.bedboy.core.data.source.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class DataResponse(
    @SerializedName("parts") var parts: ArrayList<Parts> = arrayListOf(),
    @SerializedName("role") var role: String? = null
) : Parcelable

@Parcelize
data class Parts(
    @SerializedName("text") var text: String? = null
) : Parcelable