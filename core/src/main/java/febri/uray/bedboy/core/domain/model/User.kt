package febri.uray.bedboy.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    var userID: Int? = null,
    var userUsername: String? = null,
    var userKhodam: String? = null,
    var userKhodamDesc: String? = null
) : Parcelable
