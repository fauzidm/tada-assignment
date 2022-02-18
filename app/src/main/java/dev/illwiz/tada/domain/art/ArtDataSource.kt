package dev.illwiz.tada.domain.art

import dev.illwiz.tada.domain.BaseDataSource

abstract class ArtDataSource: BaseDataSource() {

    abstract suspend fun getAllArt(page:Int,pageSize:Int):List<Art>

    abstract suspend fun getArtByObjectNumber(objectNumber:String):Art
}