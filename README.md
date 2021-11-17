# Util

Contains useful utils from work and other projects.

## util-core

Contains useful utils:

- Package `com.geniusver.persitence`: DDD persistence helper to compare and get entity changes.
- `LocalObjectStorage`: local file system based topic-key-value like object storage. Convenient for personal automation
  projects thanks good readiness.
- `RetryHandler`: Retry operation if any exception thrown with specific retry times and intervals. `RetryHandler` is
  also capable of executing fallback logic if all retries fails.

## Persistence V2

### Repository

- Convert data objects to `Entity` and vice versa, so that we can un-couple business logic and underlying data
  structure.
- Compare new data objects with old ones stored in context and call dao methods to update or insert them. Repository
  responsible for perform some extra operations before call dao methods. For example, due to data objects are converted
  by mapstructure and there is no version field in entities, new data objects version value will be null. In this case,
  repository should copy version value from old data object to new data object.
- It is recommended to put updated data object back to context.

### Dao

Communicate with infrastructure store components (like a database). Input output object is data object (`DO`).

**Data Object**

- A data object should contains basic type data only, no E-R relationship. For an `OrderDo` can
  contains `Bigdecimal amount` but should not contains `List<OrderItemDo> orderItems`.
- A data object is the minimum operation unit, that means you should insert or update all fields in one database at one.
- `equals()` should be overided, which will be used to determine if a data object should be updated or not.

Recommend to update data object to latest state after an update operation. For example, dao will execute sql to do
update:

```sql
update t_order
set amount  = 1.00,
    version = version + 1
where order_id = 1
  and version = 1
```

After execute the sql, you should update set version=2 of data object.

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
