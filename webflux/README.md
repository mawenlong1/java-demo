参考：https://blog.csdn.net/get_set/article/details/79480233

docker pull mongo:3.2
docker run -p 27017:27017 -v $PWD/db/mongo:/data/db -d mongo:3.2

http://localhost:8080/user/ 保存
{
    "id": "5a9504a167646d057051e229",
    "username": "zhangsan",
    "name": "张三",
    "phone": "18610861861",
    "birthday": "1991/1/1"
}

根据用户名查询（METHOD:GET URL:http://localhost:8080/user/zhangsan）

查询全部（METHOD:GET URL:http://localhost:8080/user）

METHOD:DELETE URL:http://localhost:8080/user/zhangsan），返回值为1

ab -k -n 500 -c 10 http://localhost:7070/user
-n 表示请求数，-c 表示并发数.-t 表示多少s内并发和请求
ab -n 100 -c 10 http://www.baidu.com/s
```
This is ApacheBench, Version 2.3 <$Revision: 1807734 $> 
Copyright 1996 Adam Twiss, Zeus Technology Ltd, http://www.zeustech.net/
Licensed to The Apache Software Foundation, http://www.apache.org/

Benchmarking www.baidu.com (be patient).....done


Server Software:        BWS/1.1   
##服务器软件和版本
Server Hostname:        www.baidu.com  
##请求的地址/域名
Server Port:            80   
##端口

Document Path:          /s  
##请求的路径
Document Length:        112435 bytes  
##页面数据/返回的数据量

Concurrency Level:      10   
##并发数
Time taken for tests:   4.764 seconds  
##共使用了多少时间 
Complete requests:      100  
##请求数 
Failed requests:        99  
##失败请求  百度为什么失败这么多，应该是百度做了防范  
   (Connect: 0, Receive: 0, Length: 99, Exceptions: 0)
Total transferred:      11342771 bytes  
##总共传输字节数，包含http的头信息等 
HTML transferred:       11247622 bytes  
##html字节数，实际的页面传递字节数 
Requests per second:    20.99 [#/sec] (mean) 
 ##每秒多少请求，这个是非常重要的参数数值，服务器的吞吐量 
Time per request:       476.427 [ms] (mean)   
##用户平均请求等待时间 
Time per request:       47.643 [ms] (mean, across all concurrent requests)  
##服务器平均处理时间，也就是服务器吞吐量的倒数 
Transfer rate:          2325.00 [Kbytes/sec] received
 ##每秒获取的数据长度

Connection Times (ms)
              min  mean[+/-sd] median   max
Connect:       22   41  12.4     39      82
##连接的最小时间，平均值，中值，最大值
Processing:   113  386 211.1    330    1246
##处理时间
Waiting:       25   80  43.9     73     266
##等待时间
Total:        152  427 210.1    373    1283
##合计时间

Percentage of the requests served within a certain time (ms)
  50%    373   
## 50%的请求在373ms内返回 
  66%    400   
## 60%的请求在400ms内返回 
  75%    426
  80%    465
  90%    761
  95%    930
  98%   1192
  99%   1283
 100%   1283 (longest request)
```

1、打开注册表：regedit
2、HKEY_LOCAL_MACHINE\SYSTEM\CurrentControlSet\ Services\TCPIP\Parameters
3、新建 DWORD值，name：TcpTimedWaitDe，value：30（十进制） –> 设置为30秒，默认是240秒
4、新建 DWORD值，name：MaxUserPort，value：65534（十进制） –> 设置最大连接数65534
5、重启系统