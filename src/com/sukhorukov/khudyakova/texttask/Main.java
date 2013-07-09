package com.sukhorukov.khudyakova.texttask;

import java.io.*;
import java.util.*;

/**

 */
public class Main {
    public static void main(String[] args) throws IOException {
        //    String inFileName = args[0];
        String inFileName="tolstoj1.txt";
        String outFileName = "out.txt";

        Map<String, Integer> wordsStore = new HashMap<>();
        int wordCounter=0;
        try (InputStream in = new FileInputStream(inFileName)){
            Reader reader = new BufferedReader(new InputStreamReader(in,"cp1251"));
            int character;
            StringBuilder str= new StringBuilder("");
            while ((character = reader.read())!=-1){

                if (Character.isLetterOrDigit((char)character)){
                    str = str.append((char)character);
                }else{
                    if (!str.toString().equals("")){              // if the word is not null
                        if (!wordsStore.keySet().contains(str.toString())){                 // if the word isn't contained in map
                            wordsStore.put(str.toString(),1);
                            wordCounter=wordCounter + 1;
                        }else{
                            int count=wordsStore.get(str.toString())+1;
                            wordsStore.remove(str.toString());
                            wordsStore.put(str.toString(),count);
                            wordCounter=wordCounter + 1;
                        }
                        str.setLength(0);
                    }
                }
            }
        }


        List<Map.Entry<String,Integer>> list = new ArrayList<>(wordsStore.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String,Integer>>() {

            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                if (o1.getValue()>o2.getValue()){
                    return -1;
                }else if (o1.getValue().equals(o2.getValue())){
                    if (o1.getKey().compareTo(o2.getKey())<0) return -1;
                    else return 1;

                }else return 1;
            }
        });
        try (OutputStream out = new FileOutputStream(outFileName)){
            PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(out)) );
            writer.println("Full number of words: "+wordCounter);
            writer.printf("%1$-20s %2$7s %3$8s \n","WORD","AMOUNT","PERCENT");

            for (Map.Entry<String,Integer> listElement:list){
                writer.printf("%1$-20s %2$7d %3$8.4g \n",listElement.getKey(),listElement.getValue(),listElement.getValue()/(double)wordCounter*100.);
            }
            writer.flush();
        }



    }
}
