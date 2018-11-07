package DummyService.launch;

import DummyService.dummyserver.Service;

public class LaunchDummyService
{
    public static void main(String argv[]) throws Exception
    {
        Service s1 = new Service(8989);
        Service s2 = new Service(6789);
        Service s3 = new Service(9999);
        
        Thread t1 = new Thread(s1);
        Thread t2 = new Thread(s2);
        Thread t3 = new Thread(s3);
        
        t1.start();
        t2.start();
        t3.start();
    }
}
