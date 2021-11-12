# Util

Contains useful utils from work and other projects.

## util-core

Contains useful utils:

- Package `com.geniusver.persitence`: DDD persistence helper to compare and get entity changes.
- `LocalObjectStorage`: local file system based topic-key-value like object storage. Convenient for personal automation
  projects thanks good readiness.
- `RetryHandler`: Retry operation if any exception thrown with specific retry times and intervals. `RetryHandler` is
  also capable of executing fallback logic if all retries fails.

## util-arthas

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

## util-persistence-example

An example module shows how to use `com.geniusver.persitence`.

## Test

There are two type of test, unit test and integration test.

- Unit test must not reply on another external service, such as a db, redis, etc. Use mock instead. Unit test class
  should end with `Test` suffix (like `ApplicationTest`).
- Integration test will require external service and can actually write some data to external service. Integration test
  should end with `IntegrationTest` suffix (like `ApplicationIntegrationTest`).

In maven pom file, there are two profile: `unit-test` and `integration-test`

- `unit-test` profile is enable by default, which will on run unit tests.
- `integration-test` profile has to manually specific by `-P` flag, like `mvn clean test -P integration-test`. Since
  unit test should work everywhere, so when running integration test will also run unit tests.

In Intellij IDEA, unit tests and integration tests can be configured separately.

- Unit test pattern: `^(?!.*IntegrationTest.*).*$`
- Integration test pattern: `.*IntegrationTest.*$`

Note, in above configuration, unit tests will not be trigger when run integration tests. If you want to run integration
tests and unit tests together like maven, just run all tests.

## Development

### Install To Local Repository

By default, a gpg is required to sign the package so that can deploy to snapshot repository. You can disable this by:

```bash
mvn clean install -DskipTests -P !default
```
