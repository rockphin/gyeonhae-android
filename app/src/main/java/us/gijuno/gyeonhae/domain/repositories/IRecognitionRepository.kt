package us.gijuno.gyeonhae.domain.repositories

import androidx.annotation.StringRes
import io.reactivex.rxjava3.core.Single

interface IRecognitionRepository<T> {

    @StringRes
    fun getRecognitionStringResource(): Int

    fun getRecognition(input: T): Single<String>
}
