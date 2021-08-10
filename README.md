# Scala Quest
Project for PPS course of the year 2020/2021.

## Description
Scala Quest is a textual adventure game inspired by the series of children's gamebooks “choose your own adventure”.  

# Requirements
To play Scala Quest the following software are needed:

- Scala version 2.12.8
- sbt version 1.5.5

# Compilation
To compile the sources into a runnable jar use the command:
``` shell
sbt assembly
```

# Test

To run all the tests use the command:
``` shell
sbt test
```
# Coverage

To verify the coverage of the application run all the tests with coverage enabled:
``` shell
sbt clean coverage test
```
Then generate the reports:
``` shell
sbt coverageReport
```

# Download
Download the latest Jar file from this repository's [releases page](https://github.com/Tale152/PPS-20-pps-sq/releases).

# Usage
For Windows/Unix systems:

Start runnable Jar with the command:
```shell
java -jar /path/to/PPS-20-scala-quest-assembly-x.y.z-SNAPSHOT.jar
```

# Authors
Developed for academic purpose by:
- Filaseta Angelo (<angelo.filaseta@studio.unibo.it>) - [AngeloFilaseta](https://github.com/AngeloFilaseta) on GitHub
- Talmi Alessandro (<alessandro.talmi@studio.unibo.it>) - [Tale152](https://github.com/Tale152) on GitHub
- Tronetti Elisa (<elisa.tronetti@studio.unibo.it>) - [ElisaTronetti](https://github.com/ElisaTronetti) on GitHub
- Sanchi Piero (<piero.sanchi@studio.unibo.it>) - [PieroSanchi](https://github.com/PieroSanchi) on GitHub