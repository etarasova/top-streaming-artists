# Top Streaming Artists

This repository was created for the purpose of learning data structures at Brooklyn College's Department of CIS.

This program reads in a CSV file that contains the top streamed music artists during certain weeks from Spotify chart. As an example, I used a global chart from January 17, 2020 to January 24, 2020. To create a chart I chose a HashMap over a Linked List because it allows for almost instant access to a value using a key. I used the artist name as a key, and artist's songs count as a value. 

The "Artist_Ratings.csv" file produced by the program contains a list of artist to rating mappings ordered by highest ratings. The second file named "Atrist_Sorted.csv" a list of artist to rating mappings ordered by artist names alphabetically.

## Dependencies

* [Java 8](https://docs.oracle.com/javase/8/docs/api/index.html)
* [OpenCSV](http://opencsv.sourceforge.net/)
* [IntelliJ Idea](https://www.jetbrains.com/idea/)

Java 8 is used here because it's the department's officially supported language and version.
OpenCSV is a library which is used to read a CSV file (spotify chart in this case) and write data to CSV files (output of this program).
I used IntelliJ Idea as my IDE.

## Folder Structure

* Code is saved into the `src` folder.
* Data is saved into the `data` folder.
* Output files are saved into the `output` folder.
* The required libraries are in the `lib` folder.