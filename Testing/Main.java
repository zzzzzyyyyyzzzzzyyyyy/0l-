
public class Main{
	public static void main(String[] args){
		String letters = "abcdefghijklmnopqrstuvwxyz"; 
		String phrase = "This is a phrase!";

		System.out.println("The following shows the letter frequencies for the phrase");

    /* your code here */
        phrase = phrase.toLowerCase();
        int count = 0;
        for(int i = 0; i<letters.length();i++){
            count = 0;
            for(int j = 0; j<phrase.length();j++){ 
                if(phrase.substring(j,j+1).equals(letters.substring(i,i+1))){
                    count += 1;
                }
            }      
           System.out.println(letters.substring(i,i+1) + ": " + String.valueOf(count));            
        }
	}
}

