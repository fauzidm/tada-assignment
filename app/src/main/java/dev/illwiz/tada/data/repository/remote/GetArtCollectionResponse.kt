package dev.illwiz.tada.data.repository.remote

import com.google.gson.annotations.SerializedName

data class GetArtCollectionResponse(
    @SerializedName("artObjects")
    val artObjects: List<ArtObject>,
    @SerializedName("count")
    val count: Int,
    @SerializedName("countFacets")
    val countFacets: CountFacets,
    @SerializedName("elapsedMilliseconds")
    val elapsedMilliseconds: Int,
    @SerializedName("facets")
    val facets: List<Facet>
) {
    data class ArtObject(
        @SerializedName("hasImage")
        val hasImage: Boolean,
        @SerializedName("headerImage")
        val headerImage: HeaderImage,
        @SerializedName("id")
        val id: String,
        @SerializedName("links")
        val links: Links,
        @SerializedName("longTitle")
        val longTitle: String,
        @SerializedName("objectNumber")
        val objectNumber: String,
        @SerializedName("permitDownload")
        val permitDownload: Boolean,
        @SerializedName("principalOrFirstMaker")
        val principalOrFirstMaker: String,
        @SerializedName("productionPlaces")
        val productionPlaces: List<String>,
        @SerializedName("showImage")
        val showImage: Boolean,
        @SerializedName("title")
        val title: String,
        @SerializedName("webImage")
        val webImage: WebImage
    ) {
        data class HeaderImage(
            @SerializedName("guid")
            val guid: String,
            @SerializedName("height")
            val height: Int,
            @SerializedName("offsetPercentageX")
            val offsetPercentageX: Int,
            @SerializedName("offsetPercentageY")
            val offsetPercentageY: Int,
            @SerializedName("url")
            val url: String,
            @SerializedName("width")
            val width: Int
        )

        data class Links(
            @SerializedName("self")
            val self: String,
            @SerializedName("web")
            val web: String
        )

        data class WebImage(
            @SerializedName("guid")
            val guid: String,
            @SerializedName("height")
            val height: Int,
            @SerializedName("offsetPercentageX")
            val offsetPercentageX: Int,
            @SerializedName("offsetPercentageY")
            val offsetPercentageY: Int,
            @SerializedName("url")
            val url: String,
            @SerializedName("width")
            val width: Int
        )
    }

    data class CountFacets(
        @SerializedName("hasimage")
        val hasimage: Int,
        @SerializedName("ondisplay")
        val ondisplay: Int
    )

    data class Facet(
        @SerializedName("facets")
        val facets: List<FacetDetail>,
        @SerializedName("name")
        val name: String,
        @SerializedName("otherTerms")
        val otherTerms: Int,
        @SerializedName("prettyName")
        val prettyName: Int
    ) {
        data class FacetDetail(
            @SerializedName("key")
            val key: String,
            @SerializedName("value")
            val value: Int
        )
    }
}