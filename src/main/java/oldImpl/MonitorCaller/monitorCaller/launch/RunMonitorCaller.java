package oldImpl.MonitorCaller.monitorCaller.launch;

import java.util.Date;
import oldImpl.MonitorCaller.monitorCaller.client.MonitorCaller;

public class RunMonitorCaller
{
    public static void main(String args[])
    {
        Date now = new Date();

        MonitorCaller newCaller1 = new MonitorCaller(
                "localhost",
                "4321",
                "caller1",
                "127.0.0.1",
                "6789",
                2000,
                3000,
                new Date(now.getTime()),
                new Date(now.getTime() + 10000));

        MonitorCaller newCaller2 = new MonitorCaller(
                "localhost",
                "4321",
                "caller2",
                "127.0.0.1",
                "8989",
                2000,
                3000,
                null,
                null);

        MonitorCaller newCaller3 = new MonitorCaller(
                "localhost",
                "4321",
                "caller3",
                "127.0.0.1",
                "9999",
                3000,
                1000,
                null,
                null);

        MonitorCaller newCaller4 = new MonitorCaller(
                "localhost",
                "4321",
                "caller4",
                "127.0.0.1",
                "6789",
                3000,
                1000,
                null,
                null);
        
        Thread t1 = new Thread(newCaller1);
        Thread t2 = new Thread(newCaller2);
        Thread t3 = new Thread(newCaller3);
        Thread t4 = new Thread(newCaller4);
        
        t1.start();
        t2.start();
        t3.start();
        t4.start();
    }
}
