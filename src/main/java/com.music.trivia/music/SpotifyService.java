package com.music.trivia.music;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.extern.slf4j.Slf4j;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.requests.data.search.simplified.SearchTracksRequest;

import javax.sound.midi.Track;
import java.io.IOException;

@Slf4j
public class SpotifyService {
    private final SpotifyApi spotifyApi;
    private static SpotifyService instance;

    private SpotifyService() {
        try {
            // Load environment variables
            Dotenv dotenv = Dotenv.configure()
                    .ignoreIfMissing()
                    .ignoreIfMalformed()
                    .load();

            String clientId = dotenv.get("SPOTIFY_CLIENT_ID");
            String clientSecret = dotenv.get("SPOTIFY_CLIENT_SECRET");

            if (clientId == null || clientSecret == null) {
                throw new IllegalStateException("Spotify credentials not found in environment variables");
            }

            spotifyApi = new SpotifyApi.Builder()
                    .setClientId(clientId)
                    .setClientSecret(clientSecret)
                    .build();

            // Get access token
            spotifyApi.clientCredentials().build().execute();
            log.info("Spotify API initialized successfully");

        } catch (Exception e) {
            log.error("Error initializing Spotify API", e);
            throw new RuntimeException("Failed to initialize Spotify API", e);
        }
    }

    public static SpotifyService getInstance() {
        if (instance == null) {
            instance = new SpotifyService();
        }
        return instance;
    }

    public Track getRandomTrack() {
        try {
            SearchTracksRequest searchTracksRequest = spotifyApi.searchTracks("year:2000-2023")
                    .limit(1)
                    .offset((int) (Math.random() * 100))
                    .build();

            Track[] tracks = searchTracksRequest.execute().getItems();
            if (tracks.length > 0) {
                log.info("Fetched track: {} by {}", tracks[0].getName(), tracks[0].getArtists()[0].getName());
                return tracks[0];
            }
            return null;
        } catch (IOException | SpotifyWebApiException e) {
            log.error("Error fetching track", e);
            return null;
        }
    }

    public String getTrackPreviewUrl(String trackId) {
        try {
            Track track = spotifyApi.getTrack(trackId).build().execute();
            String previewUrl = track.getPreviewUrl();
            log.info("Preview URL for track {}: {}", trackId, previewUrl != null ? "available" : "not available");
            return previewUrl;
        } catch (Exception e) {
            log.error("Error getting preview URL for track: {}", trackId, e);
            return null;
        }
    }
}
