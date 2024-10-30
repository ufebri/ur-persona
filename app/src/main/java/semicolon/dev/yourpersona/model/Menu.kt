package semicolon.dev.yourpersona.model

data class Menu(
    val idMenu: Int,
    val nameMenu: String
)

object MenuMapper {

    const val CEK_KHODAM = 1

    val generateMenuList: List<Menu> = listOf(
        Menu(CEK_KHODAM, "Cek Khodam")
    )
}
