package us.gijuno.gyeonhae.domain.repositories

import androidx.annotation.StringRes
import io.reactivex.rxjava3.core.Single

/**
 * A base repository for recognition.
 *
 * @param T the type of inputs to describe.
 */
interface IRecognitionRepository<T> {

    /**
     * Get the string resource of the repository's name.
     *
     * *Note: This will be localized by [getString][android.content.Context#getString(int)]
     * and be displayed at the top bar.*
     *
     * @return String resource id of the repository.
     */
    @StringRes
    fun toStringResource(): Int

    /**
     * Get the recognized description of the given input. It can contain network calls.
     * @param input a specific type of data to describe.
     * @return the description of the given input with [Single][io.reactivex.rxjava3.core.Single].
     */
    fun describe(input: T): Single<String>
}
