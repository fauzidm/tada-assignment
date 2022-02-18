package dev.illwiz.tada.data.repository.art

import dev.illwiz.tada.data.repository.remote.ArtAPI
import dev.illwiz.tada.domain.art.Art
import dev.illwiz.tada.domain.art.ArtDataSource
import javax.inject.Inject

class ArtRepository @Inject constructor(
    private val artAPI: ArtAPI
):ArtDataSource() {

    override suspend fun getAllArt(page: Int, pageSize: Int): List<Art> {
        return artAPI.getArtCollection(page,pageSize).artObjects.map {
            Art(it.objectNumber,it.title,it.webImage.url)
        }
    }

    override suspend fun getArtByObjectNumber(objectNumber: String): Art {
        val artResponse = artAPI.getArt(objectNumber)
        return Art(artResponse.artObject.objectNumber,artResponse.artObject.title,artResponse.artObject.webImage.url)
    }
}