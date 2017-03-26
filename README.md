# Fourmatting
Advanced Java formatting library.

## Usage
```java
StringFourmatter fourmatter = new StringFourmatter();
fourmatter.format("Hello {}!", "World"); // Hello World!
fourmatter.format("Random number between 1 and 3: [{0}|{1}|{2}]", "1", "2", "3");
```

## Format Syntax
The Fourmatting format consists out of three different element types:

### `StringElement`
Any string. The following characters must be escaped by prepending a `\` (backslash):  
`|`,`[`,`]`,`{`,`}`

### `PlaceholderElement`
Placeholder to be replaced by a value.  
Syntax: `{}`, `{index}`

Placeholders may explicitly contain the index of the argument they're replaced with:  
`stringFourmatter.format("{1}, {0}", "Hello", "World")` returns `World, Hello`.

Using explicit indices, parameters can be used multiple times:  
`stringFourmatter.fourmat("{0}: My name is {0}, and I'm {1} years old", "Steve", "20")` returns `Steve: My name is Steve, and I'm 20 years old`.

Placeholders with and without explicit indices may also be used together:  
`stringFourmatter.format("{}: My name is {0}, and I'm {} years old", "Steve", "20")` returns the same result.

### `AlternativeElement`
An element containing multiple alternative values separated by `|`.  
Syntax: `[a|b|c]`  
By default, the `StringFourmatter` chooses a random element from the alternative values, although this behaviour can be overridden.

Alternatives may contain any number of elements, including nested elements. This allows for weighted probabilities:  
`[50%|[25%|[8.33%|8.33%|8.33%]]]`

## Custom Formatters
TODO

## Format Examples
`Hello [World|Java|Programming]!`  
`My name is {}.`  
`{1} has interacted with {0}. {0} was interacted with by {1}.`  
`Randomly chosen argument: [{0}|{1}|{2}|{3}]`  
`Weighted probabilities: [50%|[25%|[8.33%|8.33%|8.33%]]]`  
`Inferred placeholder indices: {}, {1}, {}, {3}`  