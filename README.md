# java知识点

## float以及位运算
```java
    float f = 1 << 24;
    System.out.println(f == f + 1);
```
上面代码输出结果为true，float的有效bit位23个，其他比特位用于指数和符号。当整数大于23bit的时候表示会出现误差。
取余操作尽量使用第一种(速度快)，取余的值不是2的n次幂的时候使用第二种
1.i & 0x3f;
2.i % 64;