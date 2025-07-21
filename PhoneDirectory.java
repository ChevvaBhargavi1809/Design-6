// Time Complexity : O(1)
// Space Complexity : O(n)
// Did this code successfully run on Leetcode : no(no premium)
// Three line explanation of solution in plain english: maintain a hashset for all used numbers. Also maintain queue of all
/// available numbers. If asked for a number return the first available number, if returned add it to the end. To check if in use
/// check if in hashset
import java.util.*;
public class PhoneDirectory {
    Queue<Integer> available;
    HashSet<Integer> used;
    int max;
    PhoneDirectory(int max){
        this.available = new LinkedList<>();
        used = new HashSet<>();
        this.max = max;
        for(int i=0;i<max;i++){
            available.add(i);
        }
    }
    int get(){
        if(available.size()==0){
            return -1;
        }
        int assign = available.poll();
        used.add(assign);
        return assign;
    }
    boolean check(int number){
        return !used.contains(number);
    }
    void release(int number){
        used.remove(number);
        available.add(number);
    }

    public static void main(String args[]){
        
        PhoneDirectory phoneDirectory = new PhoneDirectory(3);

        System.out.println( phoneDirectory.get());      // It can return any available phone number. Here we assume it returns 0.
        System.out.println(phoneDirectory.get());      // Assume it returns 1.
        System.out.println(phoneDirectory.check(2));   // The number 2 is available, so return true.
        System.out.println(phoneDirectory.get());      // It returns 2, the only number that is left.
        System.out.println(phoneDirectory.check(2));   // The number 2 is no longer available, so return false.
        phoneDirectory.release(2); // Release number 2 back to the pool.
        System.out.println(phoneDirectory.check(2));   // Number 2 is available again, return true.
    }
}
