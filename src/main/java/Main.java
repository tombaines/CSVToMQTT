import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class Main {
    public static void main(String args[]) throws IOException {
        String csvPath = "C:\\Users\\tomba\\Downloads\\788816_1355729_compressed_iot_telemetry_data.csv\\iot_telemetry_data.csv";

        String row = "";
        int i = 0;

        BufferedReader csvReader = new BufferedReader(new FileReader(csvPath));
        while ((row = csvReader.readLine()) != null) {
            String[] data = row.split(",");
            System.out.println("TS: " + data[0] + " Temp: " + data[8]);
        }
    }
}
