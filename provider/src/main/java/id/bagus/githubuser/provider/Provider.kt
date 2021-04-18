package id.bagus.githubuser.provider

import android.database.Cursor
import android.net.Uri

object Provider {
    private const val AUTHORITY = "id.bagus.githubuser.provider"
    private const val SCHEME = "content"
    private const val TABLE_NAME = "Favorite"

    val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
        .authority(AUTHORITY)
        .appendPath(TABLE_NAME)
        .build()

    fun mapCursorToArrayList(cursor: Cursor?): ArrayList<FavoriteData> {
        val favList = ArrayList<FavoriteData>()

        cursor?.apply {
            while (moveToNext()) {
                val login = getString(getColumnIndexOrThrow("login"))
                val avatarUrl = getString(getColumnIndexOrThrow("imgUri"))
                favList.add(FavoriteData(login = login, avatarUrl = avatarUrl))
            }
        }
        return favList
    }
}