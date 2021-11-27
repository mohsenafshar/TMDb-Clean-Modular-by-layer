package ir.mohsenafshar.apps.mytmdb.domain.network




sealed class Resource<T>(val status: Status) {
    object Loading : Resource<Nothing>(Status.LOADING)

    class Success<T>(val data: T) : Resource<T>(Status.SUCCESS)

    class Fail<T>(val error: ApiError) : Resource<T>(Status.ERROR)

    companion object {
        fun loading(): Resource<Nothing> = Loading
        fun <T> success(data: T): Resource<T> = Success(data)
        fun <T> error(error: ApiError): Resource<T> = Fail(error)
    }
}