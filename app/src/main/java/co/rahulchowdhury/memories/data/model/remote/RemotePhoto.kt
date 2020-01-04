package co.rahulchowdhury.memories.data.model.remote

import co.rahulchowdhury.memories.data.model.local.Photo

data class RemotePhoto(
    val login: Login,
    val picture: Picture
)

fun RemotePhoto.toPhoto() = Photo(
    // We cannot rely on Random Users API to provide a
    // unique ID. That's why we are concatenating the
    // current time with the provided UUID to make it
    // unique. Without this step DiffUtil will work
    // improperly when calculating list data diff.
    uuid = "${login.uuid}_${System.currentTimeMillis()}",
    thumbnailUrl = picture.thumbnail,
    originalUrl = picture.large
)
