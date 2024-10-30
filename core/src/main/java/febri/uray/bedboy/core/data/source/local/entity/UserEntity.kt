package febri.uray.bedboy.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_entity")
data class UserEntity(

    @PrimaryKey
    @ColumnInfo("user_id")
    var userID: Int? = null,

    @ColumnInfo("user_username")
    var userUsername: String? = null,

    @ColumnInfo("user_khodam")
    var userKhodam: String? = null,

    @ColumnInfo("user_khodam_desc")
    var userKhodamDesc: String? = null
)
