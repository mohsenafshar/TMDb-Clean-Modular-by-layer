package ir.mohsenafshar.apps.mytmdb.data

import com.google.gson.Gson
import ir.mohsenafshar.apps.mytmdb.domain.model.MovieItem
import  ir.mohsenafshar.apps.mytmdb.data.local.entity.MovieItem as MovieItemEntity

class Mapper(private val gson: Gson) {
    fun <T> toJsonString(t: T): String {
        return gson.toJson(t)
    }

//    fun transformToFreightReqParams(params: GetMovieListUseCase.GetMovieParams): MovieReqParams {
//        return GetMovieListUseCase.GetMovieParams(
//            pageNo = params.pageNo,
//            dateFilter = params.dateFilter
//        )
//    }

    fun domainToEntity(movieItemDomain: MovieItem): MovieItemEntity {
        return MovieItemEntity(
            movieItemDomain.id,
            movieItemDomain.adult,
            movieItemDomain.backdrop_path,
            movieItemDomain.genre_ids,
            movieItemDomain.original_language,
            movieItemDomain.original_title,
            movieItemDomain.overview,
            movieItemDomain.popularity,
            movieItemDomain.poster_path,
            movieItemDomain.releaseDate,
            movieItemDomain.title,
            movieItemDomain.video,
            movieItemDomain.vote_average,
            movieItemDomain.vote_count
        )
    }

    fun entityToDomain(movieItemEntity: MovieItemEntity): MovieItem {
        return MovieItem(
            movieItemEntity.id,
            movieItemEntity.adult,
            movieItemEntity.backdrop_path,
            movieItemEntity.genres,
            movieItemEntity.original_language,
            movieItemEntity.original_title,
            movieItemEntity.overview,
            movieItemEntity.popularity,
            movieItemEntity.poster_path,
            movieItemEntity.releaseDate,
            movieItemEntity.title,
            movieItemEntity.video,
            movieItemEntity.vote_average,
            movieItemEntity.vote_count
        )
    }
}