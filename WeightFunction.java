import static java.lang.Math.pow;

public class WeightFunction {
    //members
    private Float epsilon;
    private Integer z;
    private String function;

    //constructors
    public WeightFunction(Float epsilon_, Integer z_, String function_){
        epsilon = epsilon_;
        z =  z_;
        function = function_;
    }

    //returns the weight
    public Float weight(Location x, Location y){
        if (function.equals("default")){
            Float answear = new Float(1);
            return Math.abs((float)(answear/(pow( x.secondNormDist(y) ,z) + epsilon)));
        }
        return new Float(0);
    }
}
