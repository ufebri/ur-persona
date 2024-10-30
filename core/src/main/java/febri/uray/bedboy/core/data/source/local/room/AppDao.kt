package febri.uray.bedboy.core.data.source.local.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import febri.uray.bedboy.core.data.source.local.entity.RegEntity
import febri.uray.bedboy.core.data.source.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface AppDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNewUser(entity: UserEntity)

    @Query("SELECT * FROM user_entity WHERE user_username = :userName")
    fun getUser(userName: String): Flow<UserEntity>

    @Query("Select * FROM user_entity WHERE user_id = :id")
    fun getUserID(id: Int): Flow<UserEntity>

    @Update
    fun updateUser(mUser: UserEntity)

    @Delete
    fun deleteUser(mUser: UserEntity)

    @Query("SELECT * FROM user_entity")
    fun getAllHistory(): Flow<List<UserEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertNewID(entity: RegEntity)

    @Query("SELECT * FROM reg_entity ORDER BY timestamp DESC")
    fun getAllRegID(): Flow<List<RegEntity>>
}