package semicolon.dev.yourpersona.presentation.ui.khodam

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import febri.uray.bedboy.core.domain.usecase.AppUseCase
import javax.inject.Inject

@HiltViewModel
class KhodamViewModel @Inject constructor(private val useCase: AppUseCase) : ViewModel() {

    fun getKhodam(name: String, khodam: String) = useCase.getKhodam(name, khodam).asLiveData()

    val history = useCase.getListHistory().asLiveData()
}