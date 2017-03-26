# Fourmatting
Advanced Java formatting library.

## Usage
```java
StringFourmatter fourmatter = new StringFourmatter();
fourmatter.format("Hello {}!", "World"); // Hello World!
fourmatter.format("Random number between 1 and 3: [{0}|{1}|{2}]", "1", "2", "3");
```

## Format Examples
`Hello [World|Java|Programming]!`  
`My name is {}.`  
`{1} has interacted with {0}. {0} was interacted with by {1}.`  
`Randomly chosen argument: [{0}|{1}|{2}|{3}]`  
`Weighted probabilities: [50%|[25%|[8.33%|8.33%|8.33%]]]`  
`Inferred placeholder indices: {}, {1}, {}, {3}`