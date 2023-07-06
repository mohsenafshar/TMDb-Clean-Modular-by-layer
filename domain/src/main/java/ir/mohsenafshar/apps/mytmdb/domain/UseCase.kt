package ir.mohsenafshar.apps.mytmdb.domain

abstract class UseCase<RESPONSE, in PARAMS> {
    abstract suspend operator fun invoke(params: PARAMS): Result<RESPONSE>
}