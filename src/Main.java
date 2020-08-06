import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class Main {
    public static void main(String args[]) throws IOException {
        String csvPath = "C:\\Users\\tomba\\Downloads\\788816_1355729_compressed_iot_telemetry_data.csv\\iot_telemetry_data.csv";
        String row = "";
        int i = 0;
        String[] data
        BufferedReader csvReader = new BufferedReader(new FileReader(csvPath));
        while ((row = csvReader.readLine()) != null) {
            i++;
            data = row.split(",");
        }
        
        System.out.println(data);


    }

}
