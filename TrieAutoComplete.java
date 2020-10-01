import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.*;
class TrieNode{
    LinkedList<TrieNode> children;
    char data;
    TrieNode Parent;
    boolean isEnd;
    int freq;
    public TrieNode(char c){
        data = c;
        children = new LinkedList<TrieNode>();
        isEnd = false;
    }

    public TrieNode getChild(char c) {
        if (children != null)
            for (TrieNode eachChild : children)
                if (eachChild.data == c)
                    return eachChild;
        return null;
    }
    List<String> getWords(){
        List<String> words = new ArrayList<String >();
        if(isEnd==true)
            words.add(toString());
        
        if(children !=  null){
            for(int i=0 ; i< children.size() ; i++){
                words.addAll(children.get(i).getWords());
            }
        }
        return words;
    }

    public String toString(){
        if(Parent == null)
            return "";

        return Parent.toString() + data;
    }        
}

class Trie{
    private TrieNode root;
    public Trie(){
        root = new TrieNode(' ');
    }

    public void insert(String word){
        if(search(word) == true)
            return;
        
        TrieNode present = root;
        TrieNode previous;
        for(char c : word.toCharArray()){
            previous = present;
            TrieNode child = present.getChild(c);
            if(child != null){
                present = child;
            }
            else{
                present.children.add(new TrieNode(c));
                present  = present.getChild(c);
                present.Parent = previous;
            }
        }
        present.isEnd = true;
    }

    public boolean search(String word){
        TrieNode current = root;
        for(char c : word.toCharArray()){
            if(current.getChild(c) == null)
                return false;
            else
                current = current.getChild(c);
        }
        if(current.isEnd = true)
            return true;
        return false;    
    }
    
    public List autocomplete(String prefix){
        TrieNode lastNode = root;

        for(char c : prefix.toCharArray()){
            lastNode = lastNode.getChild(c);
            if(lastNode == null)
                return new ArrayList();
        }
        return lastNode.getWords();
    }
}  

public class TrieAutoComplete{
    public static void main(String args[]){
        try{
            Trie t = new Trie();
            FileReader fr = new FileReader("words.txt");
            Scanner inFile = new Scanner(fr);
            while(inFile.hasNext()){
                String line = inFile.nextLine();
                t.insert(line);
            }
           ; String word;
            System.out.println("ENTER PREFIX TO BE SEARCHED ");
            Scanner inp = new Scanner(System.in);
            word = inp.nextLine();
            
        //int a , b , c;
            
            List a = t.autocomplete(word);
            if (a.size() == 0)
            	System.out.println("No Suggestions..");
            int s;
            if(a.size()<6)
            	s = a.size();
            else
            	s = 6;
            for(int i=0 ; i<s ; i++)
                System.out.println(a.get(i));      
            fr.close();
            inFile.close();
        }
        catch(Exception ft){
            System.out.println("ERROR OPENING FILE");
        }
       
    }
}
