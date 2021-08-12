# Util

Contains useful utils from work and other projects.

## Arthas Util

Can directly run arthas command in unit tests.

Configuration:
Put `arthas-util-config.properties` under classpath, following are default settings:

```properties
# java path
javaPath=java
# arthas boot jar path
arthasBootJarPath=../arthas-boot.jar
# buffer size to read and process arthas output
bufferSize=8192
```
