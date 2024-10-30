package febri.uray.bedboy.core.util

import febri.uray.bedboy.core.data.source.local.entity.RegEntity
import febri.uray.bedboy.core.data.source.local.entity.UserEntity
import febri.uray.bedboy.core.data.source.remote.response.Contents
import febri.uray.bedboy.core.data.source.remote.response.Parts
import febri.uray.bedboy.core.data.source.remote.response.RequestGemini
import febri.uray.bedboy.core.data.source.remote.response.Responses
import febri.uray.bedboy.core.domain.model.RegFire
import febri.uray.bedboy.core.domain.model.User
import kotlin.random.Random

object DataMapper {

    fun mapPrompt(userName: String, userKhodam: String): String =
        "Jelaskan khodam $userKhodam dalam Bahasa indonesia hanya 15 kata saja menggunakan lelucon dan berikan arti yang terlihat meyakinkan dengan mengaitkannya pada karakteristik hewan atau makhluk astral yang terkait dari nama $userName, contohnya jika khodamnya adalah Khodam kadal sakti maka contoh jawabanya kamu suka bersembunyi dengan cepat dan sangat lincah memikat hati wanita."

    fun mapRequestKhodam(prompt: String): RequestGemini =
        RequestGemini(
            arrayListOf(Contents(role = "user", parts = arrayListOf(Parts(prompt))))
        )

    fun mapEntitiesToUsersDomain(input: List<UserEntity>): List<User> {
        val mListData = ArrayList<User>()
        input.map {
            val user = User(
                userID = it.userID,
                userUsername = it.userUsername,
                userKhodam = it.userKhodam,
                userKhodamDesc = it.userKhodamDesc
            )
            mListData.add(user)
        }
        return mListData
    }

    fun mapEntitiesToUserDomain(input: UserEntity?): User = input.let {
        User(
            it?.userID ?: -1,
            it?.userUsername ?: "",
            it?.userKhodam ?: "",
            it?.userKhodamDesc ?: "",
        )
    }

    fun mapEntitiesToFireIDDomain(input: List<RegEntity>): List<RegFire> {
        val mListData = ArrayList<RegFire>()
        input.map {
            val reg = RegFire(
                firebaseID = it.firebaseID,
                timestamp = it.timestamp
            )
            mListData.add(reg)
        }
        return mListData
    }

    fun mapResponseToEntities(userName: String, userKhodam: String, data: Responses?): UserEntity =
        UserEntity(
            userID = Random.nextInt(1, 10000),
            userUsername = userName,
            userKhodam = userKhodam,
            userKhodamDesc = try {
                data?.candidates?.get(0)?.content?.parts?.get(0)?.text
                    ?: "Belum Punya, beli sekarang"
            } catch (e: Exception) {
                "Belum Punya, beli sekarang"
            }
        )
}