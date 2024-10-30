package semicolon.dev.yourpersona.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import febri.uray.bedboy.core.domain.usecase.AppUseCase
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val useCase: AppUseCase) : ViewModel() {

    fun insertFire(fireID: String) {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val date = Date(System.currentTimeMillis())
        val formattedDate = dateFormat.format(date)

        useCase.insertFireID(fireID, formattedDate)
    }

    val listFire = useCase.getListFireID().asLiveData()
}