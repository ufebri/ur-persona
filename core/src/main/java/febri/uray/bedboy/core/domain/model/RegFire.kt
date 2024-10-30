package febri.uray.bedboy.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RegFire(
    var firebaseID: String? = null,
    var timestamp: String? = null
) : Parcelable