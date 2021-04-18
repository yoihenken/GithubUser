package id.bagus.githubuser.provider

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class FavoriteData(
    @PrimaryKey(autoGenerate = true)
    var id : Int? = null,

    @ColumnInfo(name = "login")
    var login : String? = null,

    @ColumnInfo(name = "imgUri")
    var avatarUrl : String? = null
) : Parcelable

