package id.bagus.githubuser.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FavoriteDatas(
    var data : ArrayList<FavoriteDataSave>? = null
) : Parcelable

@Entity
@Parcelize
data class FavoriteDataSave(
    @PrimaryKey(autoGenerate = true)
    var id : Int? = null,

    @ColumnInfo(name = "login")
    var login : String? = null,

    @ColumnInfo(name = "imgUri")
    var avatarUrl : String? = null
) : Parcelable
