# NettyHeartBeatMonitor
A Netty implementation to improve my original service heart beat monitor

## Intention
The request was coming from a interview long time ago. At the time I was trying to solve the problem with out any framework.
The result with astonishingly bad. However, I still got the phone call from the company afterwards. You can see from the 
[old implementation repo](https://github.com/YiYeHuang/NettyHeartBeatMonitor/tree/master/OldIoImplementation/src/main/java). The problem includes:
- using old java io library, each call is blocking.
- launching a thread per client, over 1000 client will pretty much kill the process
- The central service has a hug loop and keep pinging the service, which is not every efficient
- Client accessibility is bad
- Multi-threading ability is a joke

## New Implementation Structure
      client service -                                                                        | spring   | 
                      |                               --------------                          | boot     |
      client service  |>--- schedule heart beat ---> | Netty Server |--> | Hazelcast Store|> -| rest     | <----user
                      |                               --------------                          | endpoint |
      client service -                                                                        | access   |

This implementation also uses Netty framework to deal with the networking work and the non-blocking io. This free up the 
threads and the resource to deal with more throughput. [Hazelcast](https://github.com/hazelcast/hazelcast) key value store is also used here for persistent in
distributed environment. On the top, the access endpoint is represented using Spring Boot framework.


### Improvement

- Netty Framework prevent the blocking call
- The Netty eventloop avoid too many threads being created
- Using Java schedule service to schedule the heart beat rate, to avoid busy pinging.
- Hazelcast key value store to make this design scalable.
- Easy access with rest api

## How to Test

- Build the project
- Launch the [monitor server](https://github.com/YiYeHuang/NettyHeartBeatMonitor/blob/master/MainServer/src/main/java/monitorservice/server/ServerApplication.java). This will start hazelcast -> netty -> Spring
- Launch any numbers of [client service](https://github.com/YiYeHuang/NettyHeartBeatMonitor/blob/master/EchoService/src/main/java/ServiceLauncher.java) you want. 
- access with localhost:8080/services for all current connected services
- access with localhost:8080/monitor/<service name> for cpu and memory information
