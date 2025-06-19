package com.example.musicplaylistmanager.activities

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.musicplaylistmanager.R // Import R for accessing resources like layouts and strings
import com.example.musicplaylistmanager.data.Song

/**
 * Activity to calculate and display the average rating of all songs.
 * Demonstrates the use of a loop for calculation.
 */
class AverageRatingActivity : AppCompatActivity() {

    private lateinit var tvAverageRatingValue: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_average_rating) // Set the layout for this activity

        tvAverageRatingValue = findViewById(R.id.tv_average_rating_value)

        // Retrieve the list of all songs passed from MainActivity
        // Using getParcelableArrayListExtra is suitable for lists of Parcelable objects.
        val allSongs: ArrayList<Song>? = intent.getParcelableArrayListExtra("all_songs")

        if (allSongs.isNullOrEmpty()) {
            Toast.makeText(this, "No songs available to calculate average rating.", Toast.LENGTH_LONG).show()
            tvAverageRatingValue.text = "N/A"
            return
        }

        // Calculate average rating using a loop
        val averageRating = calculateAverageRating(allSongs)

        // Display the calculated average rating
        val formattedAverage = String.format("%.2f", averageRating) // Format to 2 decimal places
        tvAverageRatingValue.text = formattedAverage
    }

    /**
     * Calculates the average rating of a list of songs.
     * This function explicitly uses a loop to sum up ratings.
     *
     * @param songs The list of [Song] objects.
     * @return The average rating as a Double. Returns 0.0 if the list is empty.
     */
    private fun calculateAverageRating(songs: List<Song>): Double {
        if (songs.isEmpty()) {
            return 0.0
        }

        var sumOfRatings = 0.0 // Use Double to ensure floating-point division
        var numberOfSongsWithValidRatings = 0 // Counter for songs with ratings in the 1-5 range

        // Loop through the array (List) of songs to sum up their ratings
        for (song in songs) {
            // Validate the rating before including it in the meaningful average.
            // Even though SongDetailActivity might store invalid inputs as per assignment requirements,
            // for calculating a *meaningful* average here, we'll only consider ratings in the 1-5 range.
            // If the requirement means to average *all* stored values, even invalid ones,
            // then the 'if (song.rating in 1..5)' condition should be removed,
            // and `numberOfSongsWithValidRatings` should just be `songs.size`.
            if (song.rating in 1..5) {
                sumOfRatings += song.rating
                numberOfSongsWithValidRatings++
            }
            // If you wanted to include ALL stored ratings (even incorrect ones like 0 or 100),
            // you would change the loop to:
            // sumOfRatings += song.rating.toDouble()
            // numberOfSongsWithValidRatings++ // Or simply use songs.size at the end
        }

        // Avoid division by zero
        return if (numberOfSongsWithValidRatings > 0) {
            sumOfRatings / numberOfSongsWithValidRatings
        } else {
            0.0 // If no valid ratings were found, return 0.0
        }
    }
}