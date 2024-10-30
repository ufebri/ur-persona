package febri.uray.bedboy.core.domain.repository

import febri.uray.bedboy.core.data.Resource
import febri.uray.bedboy.core.domain.model.RegFire
import febri.uray.bedboy.core.domain.model.User
import kotlinx.coroutines.flow.Flow


interface IAppRepository {

    fun getKhodam(userName: String, khodamName: String): Flow<Resource<User>>

    fun getHistory(): Flow<List<User>>

    fun insertFireID(id: String, timestamp: String)

    fun getListFireID(): Flow<List<RegFire>>
}