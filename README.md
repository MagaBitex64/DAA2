# DAA2


Repository scaffold for Assignment 2 — Insertion Sort implementation with metrics, CLI benchmark runner, unit tests, and analysis report template.

## Structure
```
assignment2-insertionsort/
├── src/
│ ├── main/java/
│ │ ├── algorithms/InsertionSort.java
│ │ ├── metrics/PerformanceTracker.java
│ │ └── cli/BenchmarkRunner.java
│ └── test/java/
│ └── algorithms/InsertionSortTest.java
├── docs/
│ ├── analysis-report.pdf
│ └── performance-plots/
├── README.md
└── pom.xml
```

## Build & Run
- Build: `mvn clean package`
- Run tests: `mvn test`
- Run benchmark CLI: `java -jar target/assignment2-insertionsort-1.0.jar --help` or run via `mvn exec:java` if you prefer.
