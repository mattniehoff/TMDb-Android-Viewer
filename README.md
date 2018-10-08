# TMDb-Android-Viewer
A simple application that uses The Movie Database (TMDb) API to display movies and their details. This work is initially being done as part of the Udacity Android Developer Nanodegree.


## Build Instructions
To build the app, you will need to provide an API Key from https://www.themoviedb.org/. 

The build will fail until you add a `TMDB_API_KEY` value to your global `gradle.properties` file. Be sure to put the key in quotations.

This value is reference in the code with `BuildConfig.TMDB_API_KEY`.
