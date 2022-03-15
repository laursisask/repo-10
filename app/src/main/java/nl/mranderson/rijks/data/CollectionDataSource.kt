package nl.mranderson.rijks.data

import nl.mranderson.rijks.domain.model.Art

interface CollectionDataSource {

    suspend fun getCollection(page : Int, loadSize: Int): List<Art>

}