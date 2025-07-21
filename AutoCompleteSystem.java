// Time Complexity : insert = O(l), search =  O(l)
// Space Complexity : O(n*l)
// Did this code successfully run on Leetcode : no(no premium)
// Three line explanation of solution in plain english: use a hashmap to maintain freq of sentences. Construct trie for all
/// input sentenes. At each character, maintain top 3 sentences
import java.util.*;
public class AutoCompleteSystem {
    class TrieNode{
        TrieNode children[];
        List<String> li;
        TrieNode(){
            children = new TrieNode[100];
            li = new ArrayList<>();
        }
    }
    HashMap<String, Integer> map;
    TrieNode root;
    String input;
    public AutoCompleteSystem(String[] sentences, int[] times) {
        root = new TrieNode();
        input = "";
        map = new HashMap<>();
        for(int i=0;i<sentences.length;i++){
            map.put(sentences[i], times[i]);
            insert(sentences[i]);
        }
    }
    public void insert(String sentence){
        TrieNode curr = root;
        for(int i=0;i<sentence.length();i++){
            char ch = sentence.charAt(i);
            if(curr.children[ch-' ']==null){
                curr.children[ch-' '] = new TrieNode();
            }
            curr = curr.children[ch-' '];
            List<String> li = curr.li;
            if(!li.contains(sentence)){
                li.add(sentence);
            }
            //System.out.println(li);
            Collections.sort(li, (a,b)->{
                int countA = map.get(a);
                int countB = map.get(b);
                if(countA==countB){
                    return a.compareTo(b);
                }
                return countB-countA;
            });
            if(li.size()>3){
                li.remove(3);
            }
        }
    }
    TrieNode curr;
    public List<String> search(char c){
        if(curr==null){
            curr = root;
        }
        if(curr.children[c-' ']==null){
            return new ArrayList<>();
        }
        List<String> li = curr.children[c-' '].li;
        curr = curr.children[c-' '];
        return li;
    }
    public List<String> input(char c) {
        this.input = this.input + c;
        if(c=='#'){
            String ip = this.input.substring(0, this.input.length()-1);
            map.put(ip, map.getOrDefault(ip, 0)+1);
            insert(ip);
            return new ArrayList<String>();
        }
        else{
            return search(c);
        }

    }
    public static void main(String args[]){
        String sentences[] = new String[]{"i love you", "island", "iroman", "i love leetcode"};
        int counts[] = new int[]{5, 3, 2, 2};
        AutoCompleteSystem obj = new AutoCompleteSystem(sentences, counts);

        System.out.println(obj.input('i')); // return ["i love you", "island", "i love leetcode"]. There are four sentences that have prefix "i". Among them, "ironman" and "i love leetcode" have same hot degree. Since ' ' has ASCII code 32 and 'r' has ASCII code 114, "i love leetcode" should be in front of "ironman". Also we only need to output top 3 hot sentences, so "ironman" will be ignored.
        System.out.println(obj.input(' ')); // return ["i love you", "i love leetcode"]. There are only two sentences that have prefix "i ".
        System.out.println(obj.input('a')); // return []. There are no sentences that have prefix "i a".
        System.out.println(obj.input('#')); // return []. The user finished the input, the sentence "i a" should be saved as a historical sentence in system. And the following input will be counted as a new search.
    }
}
