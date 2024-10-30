package febri.uray.bedboy.core.data.source

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import febri.uray.bedboy.core.data.NetworkBoundResource
import febri.uray.bedboy.core.data.Resource
import febri.uray.bedboy.core.data.source.local.LocalDataSource
import febri.uray.bedboy.core.data.source.local.entity.RegEntity
import febri.uray.bedboy.core.data.source.remote.RemoteDataSource
import febri.uray.bedboy.core.data.source.remote.network.ApiResponse
import febri.uray.bedboy.core.data.source.remote.response.Responses
import febri.uray.bedboy.core.domain.model.RegFire
import febri.uray.bedboy.core.domain.model.User
import febri.uray.bedboy.core.domain.repository.IAppRepository
import febri.uray.bedboy.core.util.AppExecutors
import febri.uray.bedboy.core.util.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class AppRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors,
    private val dataStore: DataStore<Preferences>
) : IAppRepository {

    override fun getKhodam(userName: String, khodamName: String): Flow<Resource<User>> =
        object : NetworkBoundResource<User, Responses>() {
            override fun loadFromDB(): Flow<User> {
                return localDataSource.getUserData(userName)
                    .map { DataMapper.mapEntitiesToUserDomain(it) }
            }

            override suspend fun createCall(): Flow<ApiResponse<Responses>> =
                remoteDataSource.getKhodam(DataMapper.mapPrompt(userName, khodamName))

            override suspend fun saveCallResult(data: Responses) {
                val mData = DataMapper.mapResponseToEntities(userName, khodamName, data)
                appExecutors.diskIO().execute { localDataSource.insertNewUser(mData) }
            }

            override fun shouldFetch(data: User?): Boolean = true

        }.asFlow()


    override fun getHistory(): Flow<List<User>> =
        localDataSource.getAllHistory().map { DataMapper.mapEntitiesToUsersDomain(it) }

    override fun insertFireID(id: String, timestamp: String) = appExecutors.diskIO().execute {
        localDataSource.insertFireID(RegEntity(id, timestamp))
    }

    override fun getListFireID(): Flow<List<RegFire>> {
        return localDataSource.getAllFireID().map { DataMapper.mapEntitiesToFireIDDomain(it) }
    }

}