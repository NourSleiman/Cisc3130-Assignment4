import java.util.*;
import java.io.*;

public class MovieGenres {
  public static void main(String[] args) throws Exception {
    ArrayList <String> years = new ArrayList<>();
    HashMap<String, Integer> map = new HashMap<>();
    HashMap<String, Integer> fiveMap = new HashMap<>();
    
    String file = "movies.csv";
    read(file, map, fiveMap, years);
    
    //uses collections framework to sort the elements in ascending order
    Collections.sort(years);
    
    HashMap<String, Integer> sorted = descendingSort(map);
    HashMap<String, Integer> fiveMapSorted = descendingSort(fiveMap);
    
    //prints the general amount of movies per genre
    PrintWriter pw = new PrintWriter("AllMovieGenres.txt");
    for(String genre : sorted.keySet()) {
      pw.println(genre + " " + sorted.get(genre));
    } 
    pw.close();
    
    //prints the general amount fo movies per genre within the most recent 5 years
    PrintWriter writer = new PrintWriter("Movies5Years.txt");
    writer.println("Most recent five years: 2014-2018");
    for(String genre : fiveMapSorted.keySet()) {
      writer.println(genre + " " + fiveMapSorted.get(genre));
    }
    writer.close();
    
    Averages(sorted); 
    
    //prints the amount of movies per genre per year
    PrintWriter p = new PrintWriter("GenrePerYear.txt");
    for(String year : years){
      HashMap<String, Integer> perYear = readPerYear(file, year);
      p.println("For the year of " + year);
      for(String genre : perYear.keySet()) {
        p.print(genre + " " + perYear.get(genre) + "  |  ");
    }
    p.println();
    } 
    p.close();
  }
  
  //reads the csv file to determine the genre frequency for map and does the same within a range for fiveMap
  public static void read(String file, HashMap<String, Integer> map, HashMap<String, Integer> fiveMap, ArrayList<String> list) throws Exception {
    BufferedReader br = new BufferedReader(new FileReader(file));
    String description = br.readLine();
    for(int i = 1; i < 9743; i++){
      String data = br.readLine();
      String[]line = data.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)"); 
      String[]genres = line[2].split("\\|");
      
      //year
      //"0" refers to no year available
      String whole = line[1].trim();
      String year = "0";
      if(whole.lastIndexOf(")") > 0) {
        int location = whole.lastIndexOf(")");
        year = whole.substring(location - 4, location);
      }
      
      //does not input duplicate values into the arrayList
      if(!list.contains(year)) {
        list.add(year);
      }
      
      for(int j = 0; j < genres.length; j++){
        if(!map.containsKey(genres[j])){
          Integer one = new Integer(1);
          map.put(genres[j], one);
        } else {
          Integer add = new Integer(map.get(genres[j])+1);
          map.put(genres[j], add);
        }
        if((Integer.parseInt(year) <= 2018) && (Integer.parseInt(year) > 2013)) {
            if(!fiveMap.containsKey(genres[j])) {
              Integer one = new Integer(1);
              fiveMap.put(genres[j], one);
            } else{
              Integer plus = new Integer(fiveMap.get(genres[j]) + 1);
              fiveMap.put(genres[j], plus);
            }
        }
      }
    }
    br.close();
  }
  
  public static HashMap<String, Integer> descendingSort(HashMap<String, Integer> map) 
 { 
  // Create a list from elements of HashMap 
  List<Map.Entry<String, Integer> > list = 
   new LinkedList<Map.Entry<String, Integer> >(map.entrySet()); 

  // Sort the list 
  Collections.sort(list, new Comparator<Map.Entry<String, Integer> >() { 
   public int compare(Map.Entry<String, Integer> o1, 
       Map.Entry<String, Integer> o2) 
   { 
    return -(o1.getValue()).compareTo(o2.getValue()); 
   } 
  }); 
  
  // put data from sorted list to hashmap 
  HashMap<String, Integer> temp = new LinkedHashMap<String, Integer>(); 
  for (Map.Entry<String, Integer> aa : list) { 
   temp.put(aa.getKey(), aa.getValue()); 
  } 
  return temp; 
 } 
  
  //divides the total count of genre appearances by how many genres there are
  public static void Averages(HashMap<String, Integer> map) throws Exception{
    PrintWriter w = new PrintWriter("MovieAverages.txt");
    int numGenres = 0;
    int total = 0;
    int average = 0;
    for(String genre : map.keySet()) {
      numGenres++;
      total += map.get(genre);
    }
    average = total/numGenres;
    
    //prints how each genre compares to the average
    w.println("Average number of movies per genre " + average);
    for(String genre : map.keySet()) {
      if(map.get(genre) < average) {
        w.println(genre + " has less than average movies: " + map.get(genre));
      } else if(map.get(genre) > average) {
        w.println(genre + " has more than average movies: " + map.get(genre));
      }
    }
    w.close();
  }
  
  //creates and returns a hashmap for each genre for each year
  //the years from the arraylist are compared with each year parsed from the file. 
  //If they match, they are added to the appropriate genre also being read
  public static HashMap<String, Integer> readPerYear(String file, String year) throws Exception {
   // PrintWriter p = new PrintWriter("GenrePerYear.txt");
    HashMap<String, Integer> map = new HashMap<>();
    String searchYear = year;
    BufferedReader br = new BufferedReader(new FileReader(file));
    String description = br.readLine();
    for(int i = 1; i < 9743; i++){
      String data = br.readLine();
      String[]line = data.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)"); 
      String[]genres = line[2].split("\\|");
      
      //year
      String whole = line[1].trim();
      String currentYear = "0";
      if(whole.lastIndexOf(")") > 0) {
        int location = whole.lastIndexOf(")");
        currentYear = whole.substring(location - 4, location);
      }
      
      if(searchYear.equals(currentYear)) {
        for(int j = 0; j < genres.length; j++){
          if(!map.containsKey(genres[j])){
            Integer one = new Integer(1);
            map.put(genres[j], one);
          } else {
            Integer add = new Integer(map.get(genres[j])+1);
            map.put(genres[j], add);
          }
        }
      }
    }
    br.close();
    return map;
  }
}
