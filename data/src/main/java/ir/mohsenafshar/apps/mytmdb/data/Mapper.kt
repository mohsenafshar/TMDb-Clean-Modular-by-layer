package ir.mohsenafshar.apps.mytmdb.data

import com.google.gson.Gson

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
}