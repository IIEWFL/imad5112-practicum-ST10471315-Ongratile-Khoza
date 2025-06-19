package com.example.musicplaylistmanager.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Represents a single song in the music playlist manager.
 * This data class is Parcelable, allowing its instances to be passed directly between Activities
 * via Intents.
 *
 * @property id Unique identifier for the song.
 * @property title The title of the song.
 * @property artist The artist(s) of the song.
 * @property rating The user's rating for the song (1-5).
 * @property comment Any user-added comments about the song.
 * @property artistBio A brief biography or notes about the artist.
 */
@Parcelize
data class Song(
    val id: Int,
    var title: String,
    var artist: String,
    var rating: Int, // User rating, typically 1-5
    var comment: String,
    var artistBio: String
) : Parcelable