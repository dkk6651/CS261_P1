import java.util.*;
import java.io.*;
import java.io.IOException;


public class Stable {

    List<String> men = null;
    List<String> women = null;
    Map<String, List<String>> menRanking = null;
    Map<String, List<String>> womenRanking = null;

    public static void main(String[] args){
        new Stable(args[0], args[1]);
    }

    public Stable(String input, String output){
        BufferedReader fileReader = null;

        men = new ArrayList<String>();
        women = new ArrayList<String>();
        menRanking = new HashMap<String, List<String>>();
        womenRanking = new HashMap<String, List<String>>();

        try{
            String line = null;
            String[] lineArray = null;
            int count;

            fileReader = new BufferedReader(new FileReader(input));
            while((line = fileReader.readLine()) != null){
                lineArray = line.trim().split("\\s+");
                if(lineArray.length == 1){
                    count = Integer.parseInt(lineArray[0]);
                    continue;
                }
                String current = lineArray[0];

                List<String> prefList = Arrays.asList(Arrays.copyOfRange(lineArray, 1, lineArray.length));

                if(current.contains("m")){
                    men.add(current);
                    menRanking.put(current, prefList);
                }
                if(current.contains("w")){
                    women.add(current);
                    womenRanking.put(current, prefList);
                }

            }

        } catch(IOException e){
            e.printStackTrace();
        } finally {
            try{
                if(fileReader != null){
                    fileReader.close();
                }
                System.out.println(men);
                System.out.println(women);
                for(String k : men){
                    System.out.println(menRanking.get(k));
                }
                for(String k : women){
                    System.out.println(womenRanking.get(k));
                }
                Map<String, String> matches = matching();
                for(Map.Entry<String, String> matching : matches.entrySet()){
                    System.out.println(matching.getKey() + " " + matching.getValue());
                }
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    private Map<String, String> matching(){
        Map<String, String> matches = new TreeMap<String, String>();
        List<String> freeMen = new LinkedList<String>(men);

        while(!freeMen.isEmpty()){
            String current = freeMen.remove(0);
            List<String> currentPref= menRanking.get(current);
            for(String woman : currentPref){
                woman = 'w' + woman;
                if(!matches.containsValue(woman)){
                    matches.put(current, woman);
                }
                else{
                    List<String> womenPref = womenRanking.get(woman);

                }
            }
        }

        return matches;
    }
}
