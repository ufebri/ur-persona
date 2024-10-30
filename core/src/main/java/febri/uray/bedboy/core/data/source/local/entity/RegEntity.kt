package febri.uray.bedboy.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reg_entity")
data class RegEntity(

    @PrimaryKey
    @ColumnInfo("firebase_id")
    var firebaseID: String,

    @ColumnInfo("timestamp")
    var timestamp: String? = null
)