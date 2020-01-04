package co.rahulchowdhury.memories.data

object Constants {

    object Api {
        const val BASE_URL = "https://randomuser.me"
    }

    object Remote {
        const val SEED_KEY = "memories"
        const val FIELDS_TO_INCLUDE = "login,picture"
        const val PAGE_SIZE = 100
    }

    object Paging {
        const val FIRST_LOAD_SIZE = 270
        const val PAGE_SIZE = 90
        const val PREFETCH_DISTANCE = 180
    }

    object Gallery {
        const val COLUMNS = 4
        const val VIEW_CACHE_SIZE = 60
    }

}
