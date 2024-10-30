package febri.uray.bedboy.core.data.source.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Responses(
    @SerializedName("candidates") var candidates: ArrayList<Candidates> = arrayListOf()
) : Parcelable

@Parcelize
data class Candidates(
    @SerializedName("content") var content: DataResponse? = DataResponse()
) : Parcelable
