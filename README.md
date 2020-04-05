# Cisc3130-Assignment4

One csv file is read are read into different HashMaps and then sorted by largest values first.

Data is written out into the output folder in the data folder. 

"AllMovieGenres.txt" holds the data for how many movies came out for each genre collectively. One HashMap was created to hold the genre as key and the frequency of their appearances as values.

"GenrePerYear.txt" holds the data for how many movies came out for each genre each year. The data is sorted in ascending order going by the years. The year, 0, is referred to the movies that have no release year available. To find the values, the years were read into an arrayList without reading duplicate values. The years were then compared against the file and HashMaps were made for each genre for each year. 

"MovieAverages.txt" tells which genre has more or less than average movies. The average was determined by adding all the appearances of the genres and dividing it by the number of genres.

"Movies5Years.txt" holds the amount of movies released per genre in the most recent five years. Given that the latest released movie in the file was 2018, I did the most recent five years relative to the file's time. The five years included 2014-2018.

