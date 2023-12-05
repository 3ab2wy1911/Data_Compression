import com.LZ77.Manager;

public class Main {
    public static void main(String[] args) {
        Manager manger = new Manager ();
        manger.Display();
    }
    /*
    Test Cases :
    ABBBACBABCACAACBABCA
    <0,0,A>, <0,0,B>, <1,2,A>, <0,0,C>, <3,2,B>, <4,1,A>, <2,2,A>, <9,5,A>
    ABAABABABABABABABABABA
    <0,0,A>, <0,0,B>, <2,1,A>, <3,2,B>, <2,14,A>
    ABCABCABCABC
    <0,0,A>, <0,0,B>, <0,0,C>, <3,8,C>
    ABAABABAABBBBBBBBBBBBA
    <0,0,A>, <0,0,B>, <2,1,A>, <3,2,B>, <5,3,B>, <1,10,A>
    aacaacabcabaaac
    <0,0,a>, <1,1,c>, <3,4,b>, <3,3,a>, <1,2,c>
        */
}
