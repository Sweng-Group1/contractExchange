# Test report

## JUnit

### Coverage

![coverage](ss/coverage.png)

Lambda invocation untestable as previously disclosed.

In JCanvas:

```java
protected void paintComponent(Graphics arg0)
```

is untestable as it required a frame and window to be populated. Outside of the execution time of a JUnit test.

### Tests passing

![pass](ss/pass.png)

## Visual testing

### Line

![line](ss/line.gif)


### Rectangle

![rectangle](ss/rectangle.gif)

### Circle

![circle](ss/circle.gif)