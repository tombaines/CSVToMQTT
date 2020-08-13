import org.eclipse.paho.client.mqttv3.MqttException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

//b8:27:eb:bf:9d:51
//00:0f:00:70:91:0a
//1c:bf:ce:15:ec:4d

public class Main {
    public static void main(String args[]) throws IOException, MqttException, InterruptedException {
        //MQTT Variables
        String csvPath = "/home/thomasbaines/Desktop/CSVtoMQTTtest/iot_telemetry_data.csv";
        String broker = "tcp://192.168.1.249:1883";
        String topic = "test";
        String clientID = "IoTSensor1";
        String content = "";
        int qos = 2;
        int lineNumber = 1;
        String row = "";

        //Constructors
        BufferedReader csvReader = new BufferedReader(new FileReader(csvPath));
        MQTTController controller = new MQTTController(csvPath, broker, topic, clientID, qos);

        //Reads each line of the CSV file and converts it to MQTT message.
        while ((row = csvReader.readLine()) != null) {
            if (row.contains("1c:bf:ce:15:ec:4d")){
                String[] data = row.split(",");
                content = lineNumber + "," + data[8].replaceAll("^\"|\"$", "");
                controller.sendMessage(content, controller.getClient() );
                lineNumber++;
                TimeUnit.MILLISECONDS.sleep(70);
            }
        }
        controller.disconnectClient(controller.getClient());
        System.exit(0);
    }



}
