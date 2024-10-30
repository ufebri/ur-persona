package febri.uray.bedboy.core.domain.usecase

import febri.uray.bedboy.core.data.Resource
import febri.uray.bedboy.core.domain.model.RegFire
import febri.uray.bedboy.core.domain.model.User
import febri.uray.bedboy.core.domain.repository.IAppRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AppInteractor @Inject constructor(private var appRepository: IAppRepository) :
    AppUseCase {

    override fun getKhodam(username: String, khodamName: String): Flow<Resource<User>> =
        appRepository.getKhodam(username, khodamName)

    override fun getListHistory(): Flow<List<User>> = appRepository.getHistory()

    override fun insertFireID(id: String, timestamp: String) =
        appRepository.insertFireID(id, timestamp)

    override fun getListFireID(): Flow<List<RegFire>> = appRepository.getListFireID()

}