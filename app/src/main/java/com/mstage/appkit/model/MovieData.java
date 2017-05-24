package com.mstage.appkit.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Function;

/**
 * Created by Khang NT on 5/10/17.
 * Email: khang.neon.1997@gmail.com
 */

public class MovieData {
    private String title;
    private String landscapeImage;

    public static Function<String, List<MovieData>> movieDataListTransformer() {
        return s -> {
            JSONArray jsonArray = new JSONArray(s);
            List<MovieData> result = new ArrayList<>();
            Function<JSONObject, MovieData> movieDataTransformer = movieDataTransformer();
            for (int i = 0; i < jsonArray.length(); i++) {
                result.add(movieDataTransformer.apply(jsonArray.getJSONObject(i)));
            }
            return result;
        };
    }

    public static Function<JSONObject, MovieData> movieDataTransformer() {
        return json -> new MovieData(json.getString("h1"), json.getString("landscape_image"));
    }

    public MovieData(String title, String landscapeImage) {
        this.title = title;
        this.landscapeImage = landscapeImage;
    }

    public String getTitle() {
        return title;
    }

    public String getLandscapeImage() {
        return landscapeImage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MovieData movieData = (MovieData) o;

        if (title != null ? !title.equals(movieData.title) : movieData.title != null) return false;
        return landscapeImage != null ? landscapeImage.equals(movieData.landscapeImage) : movieData.landscapeImage == null;

    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (landscapeImage != null ? landscapeImage.hashCode() : 0);
        return result;
    }
}
