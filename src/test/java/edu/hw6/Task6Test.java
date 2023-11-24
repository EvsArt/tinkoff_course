package edu.hw6;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class Task6Test {

    Task6 task6 = new Task6();

    @Test
    void testGettingBusyPorts() {
        List<Task6.PortInfo> info = task6.getBusyPortsAndProtocols();
        assertThat(info.size()).isPositive();
    }

    @Test
    void printBusyPortsAndProtocols() {
        System.out.println("Protocol\t\tPort\t\tService");
        task6.getBusyPortsAndProtocols()
            .forEach(it ->
                System.out.printf("%s\t\t\t\t%d\t\t\t%s\n", it.protocol().name, it.port(), it.service())
            );

    }
}
