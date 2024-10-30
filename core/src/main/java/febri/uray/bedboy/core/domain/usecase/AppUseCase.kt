package febri.uray.bedboy.core.domain.usecase

import febri.uray.bedboy.core.data.Resource
import febri.uray.bedboy.core.domain.model.RegFire
import febri.uray.bedboy.core.domain.model.User
import kotlinx.coroutines.flow.Flow


interface AppUseCase {

    fun getKhodam(username: String, khodamName: String): Flow<Resource<User>>

    fun getListHistory(): Flow<List<User>>

    fun insertFireID(id: String, timestamp: String)

    fun getListFireID(): Flow<List<RegFire>>
}