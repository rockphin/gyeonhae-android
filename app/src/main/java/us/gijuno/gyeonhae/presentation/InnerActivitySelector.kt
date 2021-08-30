package us.gijuno.gyeonhae.presentation

private var result: String = ""
fun InnerActivitySelector(index: Int): String {
    when(index) {
        0 -> result = "recognize"
        1 -> result = "convenience"
        2 -> result = "setting"
        3 -> result = "guide"
    }

    return result
}
