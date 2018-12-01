## compile and build 
using gradle 5

## how to build
gradle build


## how to run
gradle run -q --console=plain
or 
java -cp build/libs/rpn-0.0.2-SNAPSHOT.jar com.airwallex.assignment.calculator.rpn.Console


## genernate document
gradle javadoc 

## how to test
gradle test

test cases:

### Example 1

5 2
stack: 5 2

### Example 2

2 sqrt
stack: 1.4142135623 
clear 9 sqrt
stack: 3

### Example 3

5 2 - 
stack: 3 
3 - 
stack: 0 
clear 
stack:

### Example 4

5 4 3 2
stack: 5 4 3 2 
undo undo * 
stack: 20
5 *
stack: 100 
undo
stack: 20 5

### Example 5

7 12 2 / 
stack: 7 6 
*
stack: 42 
4 /
stack: 10.5

### Example 6

1 2 3 4 5
stack: 1 2 3 4 5 
*
stack: 1 2 3 20 
clear 3 4 - 
stack: - 1

### Example 7

1 2 3 4 5
stack: 1 2 3 4 5 
* * * *
stack: 120

### Example 8
1 2 3 * 5 + * * 6 5
operator * (position: 15): insucient parameters stack: 11
(the 6 and 5 were not pushed on to the stack due to the previous error)

