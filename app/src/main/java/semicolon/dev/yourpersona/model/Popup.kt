package semicolon.dev.yourpersona.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Popup(
    var title: String? = null,
    var message: String? = null,
    var linkImage: String? = null,
    var content: ContentPopup? = null
) : Parcelable

@Parcelize
data class ContentPopup(
    @SerializedName("cta_banner")
    var ctaBanner: String? = null,

    @SerializedName("cta_category")
    var ctaCategory: String? = null,

    @SerializedName("cta_menu")
    var ctaMenu: String? = null,

    @SerializedName("cta_detail_page")
    var ctaDetailPage: String? = null,

    @SerializedName("cta_period")
    var ctaPeriod: String? = null

) : Parcelable