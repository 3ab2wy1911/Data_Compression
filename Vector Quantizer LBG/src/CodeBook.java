

import java.util.Arrays;
import java.util.Objects;

public class CodeBook extends Vector{
    String code ;

    public CodeBook (Vector vector){
        super(vector.getWidth(), vector.getHeight(), vector.getPixels());
        code = "" ;
    }
    public CodeBook(int width, int height, Pixel[][] pixels) {
        super(width, height, pixels);
        code = "" ;
    }
    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "CodeBook{\n" +
                "\t" + super.toString() +
                "\n}";
    }
}