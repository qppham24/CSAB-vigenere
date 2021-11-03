// Phuong Pham
// 9/15/2021
// a program designed based on the principles of Vignenere's cipher

import java.util.*;

public class VigenereProgram {
   public static void main (String [] args) {
      String key = promtForKeyword();
      String p = promtForMessage();
      Encrypt output = new Encrypt(key,p);
      output.assigningNumber(key);
      System.out.println(output.encryptMessage());
   }
   
   // promt the user to enter a keyword
   public static String promtForKeyword() {
      Scanner scan = new Scanner(System.in);
      Keyword key;
      do {
         System.out.println("Enter a Keyword: ");
         key = new Keyword(scan.nextLine());
      } while(!key.checkKeyword());
      return key.toString();
   }
   
   // promt the user to enter a message to encrypt 
   public static String promtForMessage() {
      Scanner scan = new Scanner(System.in);
      System.out.println("Enter the message you want to encrypt: ");
      String p = scan.nextLine();
      return p;
   }
}

class Keyword {
   String k;
   char[] arr;
   
   public Keyword(String k) {
      this.k = k;
      arr = k.toCharArray();
   }
   
   public String toString() {
      return k;
   }
   
   // check if the keyword that the user entered meets the requirements to be a keyword
   public boolean checkKeyword() {
      String specials = ".,?!;:\"(){}[]<>";
      char[] puncts = specials.toCharArray();
      for (char ch:arr) {
         if (Character.isSpaceChar(ch)) { //no space
            return false;
         }
         if (Character.isDigit(ch)) { //no number
            return false;
         }
         Character a = ch;
         for (char punct:puncts) { //no special characres
            Character b = punct;
            if (a.equals(b)) {
               return false;
            }
         }
      }
      return true;
   }
}

class Encrypt {
   String alp = "abcdefghijklmnopqrstuvwxyz ,.?!0123456789";
   char[] alphabet = alp.toCharArray();
   String k; //contains keyword
   String p; //contains message to be encrypted
   String output = "";
   
   public Encrypt(String k, String p) {
      this.k = k;
      this.p = p;
   }
   
   // break down a string into an array of integers representing each character of the string 
   public int[] assigningNumber(String s) {
      char[] chArr = s.toCharArray();
      int[] intArr = new int[s.length()];
      for (int a=0; a<chArr.length; a++) {
         String ch = Character.toString(chArr[a]);
         for (int b=0; b<alphabet.length; b++) {
            String letter = Character.toString(alphabet[b]);
            if(ch.compareToIgnoreCase(letter)==0) {
               intArr[a] = b;
            }
         }
      }
      return intArr;
   }
   
   // encrypt the message according to the keyword
   public String encryptMessage() {
      int[] kArr = assigningNumber(k);
      int[] pArr = assigningNumber(p);

      int j = 0; //contains the index of current character in the keyword
      
      for (int i=0; i<pArr.length; i++) {
         if (pArr[i]>25) { //special characters, remain unchanged
            output += alp.substring(pArr[i],pArr[i]+1);
         }
         else if (j<kArr.length) {
            int ci = (pArr[i]+kArr[j])%26;
            output += alp.substring(ci, ci+1);
            j++;
         } else {
            j = 0; //going back to the first character of the keyword
            int ci = (pArr[i]+kArr[j])%26;
            output += alp.substring(ci, ci+1);
            j++;
         }
      }
      output = capitalization();
      return output;
   }
   
   // preserve the capitalization of the message after finish encrypting
   public String capitalization() {
      for (int i=0; i<p.length(); i++) {
         Character a = p.charAt(i);
         Character b = output.charAt(i);
         if (Character.isUpperCase(a)) {
            output = output.substring(0,i) + Character.toString(Character.toUpperCase(b)) + output.substring(i+1);
         }
      }
      return output;
   }
}