import java.util.ArrayList;
import java.util.Scanner;


public class Main {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        System.out.println("please the name of the image or kd for default.(no file extension)");
        String fileName = scanner.next();
        System.out.println("please enter a value greater then 0 and smaller then 1 for epsilon (0.*)");
        String epsilonString = scanner.next();
        System.out.println("please enter an integer number for z");
        String zString = scanner.next();
        ImageGenerator imageGenerator = new ImageGenerator();
        ArrayList imageAsArray = imageGenerator.loadImageToArray(fileName);
        ArrayList imageWithHole = imageGenerator.makeAHole(imageAsArray);
        Image holeAsImage = new Image(imageWithHole);
        imageGenerator.arrayToBufferdImageAndFile(holeAsImage.getImageAsArray(),fileName + "_Hole");
        System.out.println("please enter \"aprox\" for aproximate solution or \"exact\" for the exact solution");
        String aproxOrExact = scanner.next();
        HoleFiller holeFiller = new HoleFiller(new WeightFunction(Float.parseFloat(epsilonString),Integer.parseInt(zString),"default"));
        if(aproxOrExact.equals("aprox")){
            Image holeFull = holeFiller.fillHoleAprox(new Image(imageWithHole),7);
            imageGenerator.arrayToBufferdImageAndFile(holeFull.getImageAsArray(),fileName + "_Hole_full_aproximate");
        }
        else{
            Image holeFull = holeFiller.fillHoleExact(new Image(imageWithHole));
            imageGenerator.arrayToBufferdImageAndFile(holeFull.getImageAsArray(),fileName + "_Hole_full_exact");
        }
        System.out.println("the program loaded the image, made a hole in it and then filled it.");        
        
        System.out.println("the image with the hole is found at fileName_hole.jpg.");
        System.out.println("the image with the hole filled is found at fileName_hole_full_(exact/aprox).jpg.");
    }
}
