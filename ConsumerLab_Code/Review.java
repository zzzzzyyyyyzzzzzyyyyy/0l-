

import java.util.Scanner;
import java.io.File;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Random;
import java.io.*;

/**
 * Class that contains helper methods for the Review Lab
 **/
public class Review {

  private static HashMap<String, Double> sentiment = new HashMap<String, Double>();
  private static ArrayList<String> posAdjectives = new ArrayList<String>();
  private static ArrayList<String> negAdjectives = new ArrayList<String>();
 
  private static final String SPACE = " ";
  public static void main(String[] args){
    String review = textToString("/workspace/0l-/ConsumerLab_Code/reviews.txt");
    double goodBad = totalSentiment(review);
    //System.out.println(goodBad);
    //System.out.println(starRating(goodBad));
    
    try{
      FileWriter myWriter = new FileWriter("/workspace/0l-/ConsumerLab_Code/negativeAdjectives.txt");
      
      sentiment.forEach((key, value) -> {
        if(value < 0.0){
          try{
          myWriter.write(key + "," + value + "\n");
          }catch(Exception e){
            e.printStackTrace();
          }
        }
      });
      myWriter.close(); 
    }catch(Exception e){
      e.printStackTrace();
    }
    
    try{
      FileWriter myWriter2 = new FileWriter("/workspace/0l-/ConsumerLab_Code/positiveAdjectives.txt");
      
      sentiment.forEach((key, value) -> {
        if(value > 0.0){
          try{
          myWriter2.write(key + "," + value + "\n");
          }catch(Exception e){
            e.printStackTrace();
          }
        }
      });
      myWriter2.close(); 
    }catch(Exception e){
      e.printStackTrace();
    }


    String textie = fakeReview(review);

    
  }

  static{
    try {
      Scanner input = new Scanner(new File("/workspace/0l-/ConsumerLab_Code/cleanSentiment.csv"));
      while(input.hasNextLine()){
        String[] temp = input.nextLine().split(",");
        sentiment.put(temp[0],Double.parseDouble(temp[1]));
        //System.out.println("added "+ temp[0]+", "+temp[1]);
      }
      input.close();
    }
    catch(Exception e){
      System.out.println("Error reading or parsing cleanSentiment.csv");
    }
  
  //read in the positive adjectives in postiveAdjectives.txt
     try {
      Scanner input = new Scanner(new File("/workspace/0l-/ConsumerLab_Code/positiveAdjectives.txt"));
      while(input.hasNextLine()){
        String temp = input.nextLine().trim();
        //System.out.println(temp);
        posAdjectives.add(temp);
      }
      input.close();
    }
    catch(Exception e){
      System.out.println("Error reading or parsing postitiveAdjectives.txt\n" + e);
    }   
 
  //read in the negative adjectives in negativeAdjectives.txt
     try {
      Scanner input = new Scanner(new File("/workspace/0l-/ConsumerLab_Code/negativeAdjectives.txt"));
      while(input.hasNextLine()){
        negAdjectives.add(input.nextLine().trim());
      }
      input.close();
    }
    catch(Exception e){
      System.out.println("Error reading or parsing negativeAdjectives.txt");
    }   

  }
  
  /** 
   * returns a string containing all of the text in fileName (including punctuation), 
   * with words separated by a single space 
   */
  public static String textToString( String fileName )
  {  
    String temp = "";
    try {
      Scanner input = new Scanner(new File(fileName));
      
      //add 'words' in the file to the string, separated by a single space
      while(input.hasNext()){
        temp = temp + input.next() + " ";
      }
      input.close();
      
    }
    catch(Exception e){
      System.out.println("Unable to locate " + fileName);
    }
    //make sure to remove any additional space that may have been added at the end of the string.
    return temp.trim();
  }
  
  /**
   * @returns the sentiment value of word as a number between -1 (very negative) to 1 (very positive sentiment) 
   */
  public static double sentimentVal( String word )
  {
    try
    {
      return sentiment.get(word.toLowerCase());
    }
    catch(Exception e)
    {
      return 0;
    }
  }
  
  /**
   * Returns the ending punctuation of a string, or the empty string if there is none 
   */
  public static String getPunctuation( String word )
  { 
    String punc = "";
    for(int i=word.length()-1; i >= 0; i--){
      if(!Character.isLetterOrDigit(word.charAt(i))){
        punc = punc + word.charAt(i);
      } else {
        return punc;
      }
    }
    return punc;
  }

      /**
   * Returns the word after removing any beginning or ending punctuation
   */
  public static String removePunctuation( String word )
  {
    while(word.length() > 0 && !Character.isAlphabetic(word.charAt(0)))
    {
      word = word.substring(1);
    }
    while(word.length() > 0 && !Character.isAlphabetic(word.charAt(word.length()-1)))
    {
      word = word.substring(0, word.length()-1);
    }
    
    return word;
  }
 
  /** 
   * Randomly picks a positive adjective from the positiveAdjectives.txt file and returns it.
   */
  public static String randomPositiveAdj()
  {
    int index = (int)(Math.random() * posAdjectives.size());
    return posAdjectives.get(index);
  }
  
  /** 
   * Randomly picks a negative adjective from the negativeAdjectives.txt file and returns it.
   */
  public static String randomNegativeAdj()
  {
    int index = (int)(Math.random() * negAdjectives.size());
    return negAdjectives.get(index);
    
  }
  
  /** 
   * Randomly picks a positive or negative adjective and returns it.
   */
  public static String randomAdjective()
  {
    boolean positive = Math.random() < .5;
    if(positive){
      return randomPositiveAdj();
    } else {
      return randomNegativeAdj();
    }
  }

  public static double totalSentiment(String filename){
    String word = "";
    double value = 0.0;
    //System.out.println(filename);
    for(int i = 0; i < filename.length(); i++){
      //System.out.println(filename.substring(i,i+1));
      if(!filename.substring(i,i+1).equals(" ")){ 
        word += filename.substring(i,i+1);
      }
      else if(filename.substring(i,i+1).equals(" ")){     
        //word = getPunctuation(word);
        word = removePunctuation(word);
        //System.out.println(word);
        value += sentimentVal(word);
        word = "";
      }
    }
    return value;
  }

  public static int starRating(double value){
    if(value <= -10){
      return 0;
    }
    else if(value <= 0){
      return 1;
    }
    else if(value <= 10){
      return 2;
    }
    else if(value <= 20){
      return 3;
    }
    else if(value <= 30){
      return 4;
    }
    else{
      return 5;
    }
  }

  public static String fakeReview(String filename){ //makes bad reviews good
    String adj = "";
    int j = 0;
    Scanner scan = new Scanner("/workspace/0l-/ConsumerLab_Code/negativeAdjectives.txt");

    for(int i = 0; i < filename.length(); i++){
      if(filename.substring(i,i+1).equals("*")){
        adj = "";
        j = i;
        while(!filename.substring(j,j+1).equals(" ")){
          adj += filename.substring(j,j+1);
          j += 1;
        }

        System.out.println(adj);
        adj = adj.substring()

        //if adj in txt file
        boolean flag = false;
        int count = 0;
        while(scan.hasNextLine()) {
          String line = scan.nextLine();
          //System.out.println(line);
          if(line.indexOf(adj)!=-1) {
              flag = true;
              count = count+1;
          }
        }
        if(flag == true){
          filename.replace(adj, randomPositiveAdj());
          flag = false;
        }
      }
    }
    System.out.println(filename);

    return "";
  }

}
