package co.rahulchowdhury.memories.data.model.remote

import co.rahulchowdhury.memories.data.model.local.Photo

data class RemotePhoto(
    val login: Login,
    val picture: Picture
)

fun RemotePhoto.toPhoto() = Photo(
    uuid = login.uuid,
    thumbnailUrl = picture.thumbnail,
    originalUrl = picture.large
)
