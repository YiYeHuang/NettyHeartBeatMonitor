package Monitor.monitorCaller.client;

import Monitor.monitorCaller.request.ServiceStatus;

import java.io.ObjectInputStream;

public class ResponseReader extends Object implements Runnable
{
    protected MonitorCaller client;
    protected ObjectInputStream inStream;
    protected Thread myThread;

    public ResponseReader(MonitorCaller client, ObjectInputStream inStream)
    {
        this.client = client;
        this.inStream = inStream;
    }

    public void run()
    {
        while (true)
        {
            try
            {
                ServiceStatus message = (ServiceStatus) inStream.readObject();

                System.out.println(client.getName() + " monitor on " + message.toString());
            }
            catch (Exception e)
            {
                client.disconnect();
            }
        }
    }

    public void start()
    {
        myThread = new Thread(this);
        myThread.start();
    }

    public void stop()
    {
        myThread.stop();
        myThread = null;
    }
}
