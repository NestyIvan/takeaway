The test challenge project for themoviedb

Prerequisites
1. JDK 8 and higher
2. Account in themoviedb is created
3. The Access token is prepared and approved as described here https://developers.themoviedb.org/4/auth/user-authorization-1

In order to run tests pass the write access token and the api key of your profile

./gradlew clean test -PapiAccessToken="YOUR_ACCESS_TOKEN" -PapiKey="YOUR_API_KEY"

Test results are in the default ./build/reports folder