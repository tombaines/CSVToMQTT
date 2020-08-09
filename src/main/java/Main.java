import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeUnit;



public class Main {
    public static void main(String args[]) throws IOException, MqttException, InterruptedException {
        //MQTT Variables
        String csvPath = "/home/thomasbaines/Desktop/CSVtoMQTTtest/iot_telemetry_data.csv";
        String broker = "tcp://192.168.1.249:1883";
        String topic = "test";
        String clientId = "JavaSample";
        String content = "";
        int qos = 2;

        //Message Variables
        String row = "";
        int readingNumber = 0;
        String deviceNumber = "0";

        //Constructors
        MemoryPersistence persistence = new MemoryPersistence();
        MqttClient sampleClient = new MqttClient(broker, clientId, persistence);
        BufferedReader csvReader = new BufferedReader(new FileReader(csvPath));
        MqttConnectOptions connOpts = new MqttConnectOptions();

        //Connects to MQTT Server
        connOpts.setCleanSession(true);
        System.out.println("Connecting to broker: "+broker);
        sampleClient.connect(connOpts);
        System.out.println("Connected");

        //Reads each line of the CSV file and converts it to MQTT message.
        while ((row = csvReader.readLine()) != null) {
            TimeUnit.MILLISECONDS.sleep(500);
            String[] data = row.split(",");

            //checks which device sent that reading.
            if (data[1].contains("b8:27:eb:bf:9d:51")){
                deviceNumber = "1";
            } else if (data[1].contains("00:0f:00:70:91:0a")){
                deviceNumber = "2";
            } else if (data[1].contains("1c:bf:ce:15:ec:4d")){
                deviceNumber = "3";
            } else {
                deviceNumber = "Device not found.";
            }

            //Formats message
            System.out.println("TS: " + readingNumber + " Device: " + deviceNumber +  " Temp: " + data[8]);
            content= readingNumber + "," + deviceNumber + "," + data[8];
            System.out.println("Publishing message: "+content + " " + data[1]);
            MqttMessage message = new MqttMessage(content.getBytes());
            message.setQos(qos);
            sampleClient.publish(topic, message);
            System.out.println("Message published");
            readingNumber++;

        }

        sampleClient.disconnect();
        System.out.println("Disconnected");
        System.exit(0);
    }



}
