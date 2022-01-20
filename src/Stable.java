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
        Map<String, String> matches = null;
        int count = 0;
        StringBuilder temp = new StringBuilder();

        try{
            String line = null;
            String[] lineArray = null;

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
//                System.out.println(men);
//                System.out.println(women);
//                for(String k : men){
//                    System.out.println(menRanking.get(k));
//                }
//                for(String k : women){
//                    System.out.println(womenRanking.get(k));
//                }
                matches = matching();
//                for(Map.Entry<String, String> matching : matches.entrySet()){
//                    System.out.println(matching.getKey() + " " + matching.getValue());
//                }
            } catch (IOException e){
                e.printStackTrace();
            }

            temp.append(count).append('\n');

            for(String m : men){
                temp.append(m);
                for(String i : menRanking.get(m)){
                    temp.append(' ').append(i);
                }
                temp.append('\n');
            }

            for(String w : women){
                temp.append(w);
                for(String i : womenRanking.get(w)){
                    temp.append(' ').append(i);
                }
                temp.append('\n');
            }

            assert matches != null;
            for(Map.Entry<String, String> matching : matches.entrySet()){
                temp.append(matching.getKey()).append(" ").append(matching.getValue()).append('\n');
            }
        }

        try{
            File outFile = new File(output);
            if(outFile.createNewFile()){
                System.out.println("New File Created");
            }

            FileWriter writer = new FileWriter(output);
            writer.write(String.valueOf(temp));
            writer.close();

        } catch(IOException e){
            e.printStackTrace();
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
                    String other = getKey(matches, woman);
                    if(womenPref.indexOf(current.replace("m", "")) < womenPref.indexOf(other.replace("m", ""))){
                        matches.remove(other, woman);
                        matches.put(current, woman);
                        freeMen.add(other);
                    }else{
                        continue;
                    }
                }
                break;
            }
        }

        return matches;
    }

    public String getKey(Map<String, String> map, String value){
        for(Map.Entry<String, String> entry : map.entrySet()){
            if(entry.getValue().equals(value)){
                return entry.getKey();
            }
        }
        return null;
    }
}
