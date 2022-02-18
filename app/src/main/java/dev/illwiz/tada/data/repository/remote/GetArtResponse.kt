package dev.illwiz.tada.data.repository.remote


import com.google.gson.annotations.SerializedName

data class GetArtResponse(
    @SerializedName("artObject")
    val artObject: ArtObject,
    @SerializedName("artObjectPage")
    val artObjectPage: ArtObjectPage,
    @SerializedName("elapsedMilliseconds")
    val elapsedMilliseconds: Int
) {
    data class ArtObject(
        @SerializedName("acquisition")
        val acquisition: Acquisition,
        @SerializedName("artistRole")
        val artistRole: Any,
        @SerializedName("associations")
        val associations: List<Any>,
        @SerializedName("catRefRPK")
        val catRefRPK: List<Any>,
        @SerializedName("classification")
        val classification: Classification,
        @SerializedName("colors")
        val colors: List<Color>,
        @SerializedName("colorsWithNormalization")
        val colorsWithNormalization: List<ColorsWithNormalization>,
        @SerializedName("copyrightHolder")
        val copyrightHolder: Any,
        @SerializedName("dating")
        val dating: Dating,
        @SerializedName("description")
        val description: String,
        @SerializedName("dimensions")
        val dimensions: List<Dimension>,
        @SerializedName("documentation")
        val documentation: List<String>,
        @SerializedName("exhibitions")
        val exhibitions: List<Any>,
        @SerializedName("hasImage")
        val hasImage: Boolean,
        @SerializedName("historicalPersons")
        val historicalPersons: List<String>,
        @SerializedName("id")
        val id: String,
        @SerializedName("inscriptions")
        val inscriptions: List<Any>,
        @SerializedName("label")
        val label: Label,
        @SerializedName("labelText")
        val labelText: Any,
        @SerializedName("language")
        val language: String,
        @SerializedName("links")
        val links: Links,
        @SerializedName("location")
        val location: String,
        @SerializedName("longTitle")
        val longTitle: String,
        @SerializedName("makers")
        val makers: List<Any>,
        @SerializedName("materials")
        val materials: List<String>,
        @SerializedName("normalized32Colors")
        val normalized32Colors: List<Normalized32Color>,
        @SerializedName("normalizedColors")
        val normalizedColors: List<NormalizedColor>,
        @SerializedName("objectCollection")
        val objectCollection: List<String>,
        @SerializedName("objectNumber")
        val objectNumber: String,
        @SerializedName("objectTypes")
        val objectTypes: List<String>,
        @SerializedName("physicalMedium")
        val physicalMedium: String,
        @SerializedName("physicalProperties")
        val physicalProperties: List<Any>,
        @SerializedName("plaqueDescriptionDutch")
        val plaqueDescriptionDutch: String,
        @SerializedName("plaqueDescriptionEnglish")
        val plaqueDescriptionEnglish: String,
        @SerializedName("principalMaker")
        val principalMaker: String,
        @SerializedName("principalMakers")
        val principalMakers: List<PrincipalMaker>,
        @SerializedName("principalOrFirstMaker")
        val principalOrFirstMaker: String,
        @SerializedName("priref")
        val priref: String,
        @SerializedName("productionPlaces")
        val productionPlaces: List<String>,
        @SerializedName("scLabelLine")
        val scLabelLine: String,
        @SerializedName("showImage")
        val showImage: Boolean,
        @SerializedName("subTitle")
        val subTitle: String,
        @SerializedName("techniques")
        val techniques: List<Any>,
        @SerializedName("title")
        val title: String,
        @SerializedName("titles")
        val titles: List<String>,
        @SerializedName("webImage")
        val webImage: WebImage
    ) {
        data class Acquisition(
            @SerializedName("creditLine")
            val creditLine: String,
            @SerializedName("date")
            val date: String,
            @SerializedName("method")
            val method: String
        )

        data class Classification(
            @SerializedName("events")
            val events: List<Any>,
            @SerializedName("iconClassDescription")
            val iconClassDescription: List<String>,
            @SerializedName("iconClassIdentifier")
            val iconClassIdentifier: List<String>,
            @SerializedName("motifs")
            val motifs: List<Any>,
            @SerializedName("objectNumbers")
            val objectNumbers: List<String>,
            @SerializedName("people")
            val people: List<String>,
            @SerializedName("periods")
            val periods: List<Any>,
            @SerializedName("places")
            val places: List<String>
        )

        data class Color(
            @SerializedName("hex")
            val hex: String,
            @SerializedName("percentage")
            val percentage: Int
        )

        data class ColorsWithNormalization(
            @SerializedName("normalizedHex")
            val normalizedHex: String,
            @SerializedName("originalHex")
            val originalHex: String
        )

        data class Dating(
            @SerializedName("period")
            val period: Int,
            @SerializedName("presentingDate")
            val presentingDate: String,
            @SerializedName("sortingDate")
            val sortingDate: Int,
            @SerializedName("yearEarly")
            val yearEarly: Int,
            @SerializedName("yearLate")
            val yearLate: Int
        )

        data class Dimension(
            @SerializedName("part")
            val part: Any,
            @SerializedName("type")
            val type: String,
            @SerializedName("unit")
            val unit: String,
            @SerializedName("value")
            val value: String
        )

        data class Label(
            @SerializedName("date")
            val date: String,
            @SerializedName("description")
            val description: String,
            @SerializedName("makerLine")
            val makerLine: String,
            @SerializedName("notes")
            val notes: String,
            @SerializedName("title")
            val title: String
        )

        data class Links(
            @SerializedName("search")
            val search: String
        )

        data class Normalized32Color(
            @SerializedName("hex")
            val hex: String,
            @SerializedName("percentage")
            val percentage: Int
        )

        data class NormalizedColor(
            @SerializedName("hex")
            val hex: String,
            @SerializedName("percentage")
            val percentage: Int
        )

        data class PrincipalMaker(
            @SerializedName("biography")
            val biography: Any,
            @SerializedName("dateOfBirth")
            val dateOfBirth: String,
            @SerializedName("dateOfBirthPrecision")
            val dateOfBirthPrecision: Any,
            @SerializedName("dateOfDeath")
            val dateOfDeath: String,
            @SerializedName("dateOfDeathPrecision")
            val dateOfDeathPrecision: Any,
            @SerializedName("labelDesc")
            val labelDesc: String,
            @SerializedName("name")
            val name: String,
            @SerializedName("nationality")
            val nationality: Any,
            @SerializedName("occupation")
            val occupation: List<String>,
            @SerializedName("placeOfBirth")
            val placeOfBirth: String,
            @SerializedName("placeOfDeath")
            val placeOfDeath: String,
            @SerializedName("productionPlaces")
            val productionPlaces: List<String>,
            @SerializedName("qualification")
            val qualification: Any,
            @SerializedName("roles")
            val roles: List<String>,
            @SerializedName("unFixedName")
            val unFixedName: String
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

    data class ArtObjectPage(
        @SerializedName("adlibOverrides")
        val adlibOverrides: AdlibOverrides,
        @SerializedName("audioFile1")
        val audioFile1: Any,
        @SerializedName("audioFileLabel1")
        val audioFileLabel1: Any,
        @SerializedName("audioFileLabel2")
        val audioFileLabel2: Any,
        @SerializedName("createdOn")
        val createdOn: String,
        @SerializedName("id")
        val id: String,
        @SerializedName("lang")
        val lang: String,
        @SerializedName("objectNumber")
        val objectNumber: String,
        @SerializedName("plaqueDescription")
        val plaqueDescription: String,
        @SerializedName("similarPages")
        val similarPages: List<Any>,
        @SerializedName("tags")
        val tags: List<Any>,
        @SerializedName("updatedOn")
        val updatedOn: String
    ) {
        data class AdlibOverrides(
            @SerializedName("etiketText")
            val etiketText: Any,
            @SerializedName("maker")
            val maker: Any,
            @SerializedName("titel")
            val titel: Any
        )
    }
}