package vision.kodai.cbc.platform

import vision.kodai.cbc.TypeSpec

sealed interface Platform {
    val typeSpec : TypeSpec

    object X64 : Platform {
        override val typeSpec = TypeSpec(1, 4, 8, 8)
    }
}
