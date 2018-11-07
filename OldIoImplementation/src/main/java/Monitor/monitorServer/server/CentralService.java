package Monitor.monitorServer.server;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;

import Monitor.monitorServer.request.RegisterReqest;

public class CentralService
{
    private Hashtable<String, CallerService> m_clients;

    /**
     * True as turned on, false as turned off
     */
    private HashMap<String, Boolean> m_servicesStatus;

    private HashMap<String, ServiceMetadata> m_servicesMeta;

    public CentralService()
    {
        m_clients = new Hashtable<String, CallerService>();
        m_servicesStatus = new HashMap<String, Boolean>();
        m_servicesMeta = new HashMap<String, ServiceMetadata>();
    }

    public synchronized Boolean getServiceStatus(String url)
    {
        if (m_servicesStatus.containsKey(url))
        {
            return m_servicesStatus.get(url);
        }
        else
        {
            return null;
        }
    }

    public synchronized ServiceMetadata registerCaller(
        String id,
        CallerService client,
        RegisterReqest request)
    {
        if (m_clients.get(id) != null)
        {
            client.disconnect();
            return null;
        }

        // Add the new client to the table
        m_clients.put(id, client);

        String newUrl = request.getHost() +":"+ request.getPort();
        if (!m_servicesStatus.containsKey(newUrl) && !m_servicesMeta.containsKey(newUrl))
        {
            ServiceMetadata newServiceMeta = new ServiceMetadata(
                    request.getHost(),
                    request.getPort(),
                    request.getPollFreq(),
                    request.getGraceTime(),
                    request.getBlackOutStartTime(),
                    request.getBlackOutEndTime());
            m_servicesStatus.put(newUrl, Boolean.valueOf(false));
            m_servicesMeta.put(newUrl, newServiceMeta);
            return newServiceMeta;
        }
        else
        {
            return m_servicesMeta.get(newUrl);
        }
    }

    public synchronized void removeCaller(String name)
    {
        CallerService client = (CallerService) m_clients.get(name);
        if (client != null)
        {
            m_clients.remove(name);
        }
    }

    public synchronized void removeCaller(CallerService client)
    {
        Enumeration e = m_clients.keys();

        while (e.hasMoreElements())
        {
            String key = (String) e.nextElement();

            if (m_clients.get(key) == client)
            {
                m_clients.remove(key);
            }
        }
    }

    public synchronized int getCurrentUserNumber()
    {
        return m_clients.size();
    }

    public synchronized String[] getUserList()
    {
        Enumeration e = m_clients.keys();

        // Create an array to hold the m_clients names
        String[] nameList = new String[m_clients.size()];

        // Copy the user names into the nameList array
        int i = 0;
        while (e.hasMoreElements())
        {
            nameList[i++] = (String) e.nextElement();
        }

        // Return the name list
        return nameList;
    }

    public synchronized void updateStatus(String url, boolean status)
    {
        if (m_servicesStatus.containsKey(url))
        {
            m_servicesStatus.put(url, status);
        }
        else
        {
            return;
        }
    }
}
