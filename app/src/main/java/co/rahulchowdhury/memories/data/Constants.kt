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
        const val FIRST_LOAD_SIZE = 180
        const val PAGE_SIZE = 90
    }

    object Gallery {
        const val COLUMNS = 3
        const val VIEW_CACHE_SIZE = 30
    }

}
