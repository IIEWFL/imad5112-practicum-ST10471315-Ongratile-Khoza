package com.example.musicplaylistmanager.utils

import com.example.musicplaylistmanager.data.Song

/**
 * A singleton object to manage the list of songs in the application.
 * This acts as a simple in-memory data store for the purpose of this assignment.
 * In a real application, this would typically involve a database (like Room, SQLite, or Firestore)
 * or a network API.
 */
object SongDataManager {
    // A mutable list (effectively an array for this context) to store Song objects.
    // Initialized with 4 predefined songs as per requirements.
    val songs: MutableList<Song> = mutableListOf(
        Song(
            id = 1,
            title = "Bohemian Rhapsody",
            artist = "Queen",
            rating = 5,
            comment = "An absolute masterpiece, timeless and iconic.",
            artistBio = "Queen is a British rock band formed in London in 1970. Their classic line-up was Freddie Mercury (lead vocals, piano), Brian May (guitar, vocals), Roger Taylor (drums, vocals), and John Deacon (bass guitar). Their early works were influenced by progressive rock, hard rock, and heavy metal, but the band gradually ventured into more conventional and radio-friendly works by incorporating further styles, such as arena rock and pop rock."
        ),
        Song(
            id = 2,
            title = "Hotel California",
            artist = "Eagles",
            rating = 4,
            comment = "Great guitar solos and storytelling lyrics.",
            artistBio = "The Eagles are an American rock band formed in Los Angeles in 1971. With five number-one singles and six Grammy Awards, the Eagles were one of the most successful musical acts of the 1970s. Their album 'Hotel California' is one of the best-selling albums of all time."
        ),
        Song(
            id = 3,
            title = "Blinding Lights",
            artist = "The Weeknd",
            rating = 3,
            comment = "Catchy synth-pop, good for driving.",
            artistBio = "Abel Makkonen Tesfaye (born 1990), known professionally as The Weeknd, is a Canadian singer, songwriter, and record producer. He is known for his musical versatility and dark lyrical themes. 'Blinding Lights' is one of his most commercially successful songs, breaking several records."
        ),
        Song(
            id = 4,
            title = "Stairway to Heaven",
            artist = "Led Zeppelin",
            rating = 5,
            comment = "A legendary rock anthem, truly epic.",
            artistBio = "Led Zeppelin were an English rock band formed in London in 1968. The group comprised vocalist Robert Plant, guitarist Jimmy Page, bassist/keyboardist John Paul Jones, and drummer John Bonham. Their heavy, guitar-driven sound, rooted in blues and psychedelia, has earned them recognition as one of the pioneers of hard rock and heavy metal."
        )
    )

    /**
     * Retrieves a song by its unique ID.
     * @param id The ID of the song to retrieve.
     * @return The [Song] object if found, otherwise null.
     */
    fun getSongById(id: Int): Song? {
        // Using a loop to find the song, demonstrating iteration over the list (array)
        for (song in songs) {
            if (song.id == id) {
                return song
            }
        }
        return null // Song not found
    }

    /**
     * Updates the details of an existing song.
     * @param updatedSong The [Song] object containing the updated details.
     */
    fun updateSong(updatedSong: Song) {
        val index = songs.indexOfFirst { it.id == updatedSong.id }
        if (index != -1) {
            // Update the song in the list (array)
            songs[index] = updatedSong
        }
    }
}