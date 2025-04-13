import javax.swing.*;
import java.util.ArrayList;


//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        MyHashMap<Integer,String> test = new MyHashMap<>();

        test.put(232332, "Катя");
        test.put(27662332, "Олег");
        test.put(2323362, "Катя");
        test.put(2332, "Игорь");
        System.out.println(test.getSize());
        System.out.println(test.get(2332));

         test.remove(2332);
        System.out.println( "remove " + test.get(2332));
    }
}