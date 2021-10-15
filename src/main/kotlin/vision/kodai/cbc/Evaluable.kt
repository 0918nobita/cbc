package vision.kodai.cbc

sealed interface Evaluable<out T> {
    fun eval(): T
}
