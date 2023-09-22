# Sift4J

Sift4J is a technique to extract semantic fragments from the test classes. 

## How Sift4J Works 

Sift4J uses a set of _predefined_ rules to extract the types of information fragments identified in our research paper
by relying on common strategies and conventions. Sift4J also provides a flexible framework that allows user to define an open-ended
collection of arbitrary _user-defined_ rules. 

## Overall Design 
![](img/RuleEngineDesign.png)

## Rule Type
![](img/RuleType.png)
## User-Defined Rule Template

Sift4J provides three User-Defined rule templates to help user invoke user-specific rules.  

### Grammar Rule Template 

Grammar Rule is designed to capture applied grammar structure within the test method name. 

An Example of invoking Grammar Rule. The convention is composed by a sequence of semantic information fragments split by a space. 
The pattern is composed by a sequence of phrase labels from Stanford POS Tagger split by a space. The following example indicates that if the test method name follows 
the grammar structure NP following by a VP, then the NP is considered as the Expected Result and the VP is considered as the Focal Method.
```
name: "Grammar Rule 1"
priority: 1
description: "check grammar pattern"
label: "GrammarPatternRule"
convention: "ExpectedResult FocalMethod"
pattern: "NP VP"

```
### Naming Convention Rule Template 

An example of invoking Naming Convention Rule.
```
name: "naming convention 2"
priority: 2
description: "second naming convention rule"
label: "NamingConventionRule"
convention: "testScenarioWithState"
REGEX: "test(\\w+)With(\\w+)"
```
### Edge Checking Rule Template

This rule is used to specify the meaning of the value assigned to an object when the object is initialized.  
An example of invoking Edge Checking Rule
```
name: "Range Check Rule 1"
priority: 4
description: "check if adult student is created or not"
label: "RangeCheckRule"
object: "student"
position: 2
lowBoundInclusive: 1
hiBoundInclusive: 18
fragment: "State"
val: "NotAdult"
```