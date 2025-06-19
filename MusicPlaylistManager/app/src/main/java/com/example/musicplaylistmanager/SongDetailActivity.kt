package com.example.musicplaylistmanager.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.musicplaylistmanager.R // Import R for accessing resources like layouts and strings
import com.example.musicplaylistmanager.data.Song
import com.example.musicplaylistmanager.utils.SongDataManager

/**
 * Activity to display and allow editing of a song's details (rating and comments).
 * Provides navigation to the extended artist view.
 */
class SongDetailActivity : AppCompatActivity() {

    private lateinit var tvSongTitle: TextView
    private lateinit var tvArtistName: TextView
    private lateinit var tvCurrentRating: TextView
    private lateinit var tvCurrentComments: TextView
    private lateinit var etNewRating: EditText
    private lateinit var etNewComment: EditText
    private lateinit var btnUpdateSong: Button
    private lateinit var btnViewArtistInfo: Button

    // Holds the current song object being displayed/edited
    private var currentSong: Song? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song_detail) // Set the layout for this activity

        // Initialize UI elements using findViewById
        tvSongTitle = findViewById(R.id.tv_song_title)
        tvArtistName = findViewById(R.id.tv_artist_name)
        tvCurrentRating = findViewById(R.id.tv_current_rating)
        tvCurrentComments = findViewById(R.id.tv_current_comments)
        etNewRating = findViewById(R.id.et_new_rating)
        etNewComment = findViewById(R.id.et_new_comment)
        btnUpdateSong = findViewById(R.id.btn_update_song)
        btnViewArtistInfo = findViewById(R.id.btn_view_artist_info)

        // Retrieve the Song object passed from MainActivity
        // Using getParcelableExtra with a nullable cast for safety
        currentSong = intent.getParcelableExtra("selected_song") as? Song

        if (currentSong == null) {
            // Handle error if no song was passed (e.g., direct launch or broken intent)
            Toast.makeText(this, "Error: No song data received!", Toast.LENGTH_LONG).show()
            finish() // Close the activity if no data
            return
        }

        // Display the initial song details
        displaySongDetails()

        // Set up click listener for the "Update Song" button
        btnUpdateSong.setOnClickListener {
            updateSongDetails()
        }

        // Set up click listener for the "View Artist Info" button
        btnViewArtistInfo.setOnClickListener {
            navigateToArtistInfo()
        }
    }

    /**
     * Populates the UI fields with the details of the [currentSong].
     */
    private fun displaySongDetails() {
        currentSong?.let { song ->
            tvSongTitle.text = song.title
            tvArtistName.text = song.artist
            tvCurrentRating.text = getString(R.string.current_rating) + " ${song.rating}/5"
            tvCurrentComments.text = getString(R.string.comments) + " ${song.comment}"
            etNewRating.setText("") // Clear input field for new rating
            etNewComment.setText("") // Clear input field for new comment
        }
    }

    /**
     * Handles the update logic for song rating and comments.
     * Includes error handling as per the assignment requirements.
     */
    private fun updateSongDetails() {
        currentSong?.let { song ->
            val newRatingStr = etNewRating.text.toString().trim()
            val newComment = etNewComment.text.toString().trim()

            var ratingInputValid = true
            var parsedRating = song.rating // Default to current rating if input is invalid/empty

            // Validate and parse new rating
            if (newRatingStr.isNotEmpty()) {
                try {
                    val tempRating = newRatingStr.toInt()
                    if (tempRating in 1..5) {
                        parsedRating = tempRating
                        Toast.makeText(this, "Rating updated successfully!", Toast.LENGTH_SHORT).show()
                    } else {
                        // Error handling: Rating out of range (1-5)
                        ratingInputValid = false
                        showErrorDialog("Invalid Rating", "Rating must be between 1 and 5. Applying the invalid value ($tempRating) anyway as per requirements.")
                        // As per requirement, apply the incorrect action:
                        parsedRating = tempRating // Store the invalid rating
                    }
                } catch (e: NumberFormatException) {
                    // Error handling: Non-numeric rating input
                    ratingInputValid = false
                    showErrorDialog("Invalid Input", "Rating must be a number. Applying 0 anyway as per requirements.")
                    // As per requirement, apply the incorrect action:
                    parsedRating = 0 // Store 0 for non-numeric input
                }
            }
            // If newRatingStr is empty, parsedRating remains the original song.rating, which is valid.

            // Update the song object
            song.rating = parsedRating
            if (newComment.isNotEmpty()) {
                song.comment = newComment
                Toast.makeText(this, "Comment updated.", Toast.LENGTH_SHORT).show()
            } else if (etNewComment.text.toString().isNotBlank()) {
                // If the user explicitly cleared the comment by typing something then deleting it
                song.comment = ""
                Toast.makeText(this, "Comment cleared.", Toast.LENGTH_SHORT).show()
            }
            // If newComment is empty initially, and user doesn't type anything, comment remains unchanged.

            // Update the song in the global data manager (our 'array' source)
            SongDataManager.updateSong(song)

            // Refresh the displayed details on this screen
            displaySongDetails()
        }
    }

    /**
     * Displays a custom AlertDialog for error messages.
     * @param title The title of the dialog.
     * @param message The message to display.
     */
    private fun showErrorDialog(title: String, message: String) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    /**
     * Navigates to the SongExtendedViewActivity to display artist information.
     */
    private fun navigateToArtistInfo() {
        currentSong?.let { song ->
            val intent = Intent(this, SongExtendedViewActivity::class.java).apply {
                // Pass the Parcelable Song object to the extended view activity
                putExtra("selected_song", song)
            }
            startActivity(intent)
        }
    }
}