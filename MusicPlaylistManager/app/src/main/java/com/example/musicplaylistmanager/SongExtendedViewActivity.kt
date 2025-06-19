package com.example.musicplaylistmanager.activities

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.musicplaylistmanager.R // Import R for accessing resources like layouts and strings
import com.example.musicplaylistmanager.data.Song

/**
 * Activity to display extended information about the song's artist.
 */
class SongExtendedViewActivity : AppCompatActivity() {

    private lateinit var tvArtistNameExtended: TextView
    private lateinit var tvArtistBio: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song_extended_view) // Set the layout for this activity

        // Initialize UI elements
        tvArtistNameExtended = findViewById(R.id.tv_artist_name_extended)
        tvArtistBio = findViewById(R.id.tv_artist_bio)

        // Retrieve the Song object passed from SongDetailActivity
        // Using getParcelableExtra with a nullable cast for safety
        val song = intent.getParcelableExtra("selected_song") as? Song

        if (song == null) {
            // Handle error if no song was passed (e.g., direct launch or broken intent)
            Toast.makeText(this, "Error: No artist data received!", Toast.LENGTH_LONG).show()
            finish() // Close the activity if no data
            return
        }

        // Display artist information
        tvArtistNameExtended.text = song.artist
        tvArtistBio.text = song.artistBio
    }
}