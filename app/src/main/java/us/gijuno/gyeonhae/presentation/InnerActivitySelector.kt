package us.gijuno.gyeonhae.presentation

fun InnerActivitySelector(index: Int): String {
    return when (index) {
        0 -> "recognize"
        1 -> "convenience"
        2 -> "setting"
        3 -> "guide"
        else -> ""
    }
}
