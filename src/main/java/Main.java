import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;



public class Main {
    public static void main(String args[]) throws IOException, MqttException {
        String csvPath = "/home/thomasbaines/Desktop/CSVtoMQTTtest/iot_telemetry_data.csv";
        String row = "";

        String topic = "test";
        String content = "";
        int qos = 2;
        String broker = "tcp://192.168.0.87:1883";
        String clientId = "JavaSample";
        MemoryPersistence persistence = new MemoryPersistence();

        MqttClient sampleClient = new MqttClient(broker, clientId, persistence);
        MqttConnectOptions connOpts = new MqttConnectOptions();
        connOpts.setCleanSession(true);
        System.out.println("Connecting to broker: "+broker);
        sampleClient.connect(connOpts);
        System.out.println("Connected");

        BufferedReader csvReader = new BufferedReader(new FileReader(csvPath));

        while ((row = csvReader.readLine()) != null) {
            String[] data = row.split(",");
            System.out.println("TS: " + data[0] + " Temp: " + data[8]);
            content= data[0] + "," + data[8];
            System.out.println("Publishing message: "+content);
            MqttMessage message = new MqttMessage(content.getBytes());
            message.setQos(qos);
            sampleClient.publish(topic, message);
            System.out.println("Message published");

        }

        sampleClient.disconnect();
        System.out.println("Disconnected");
        System.exit(0);
    }
}
