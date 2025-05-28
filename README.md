# Darwin Core Data Package Mermaid Visualizer

*co-written with ChatGPT.*

This is a Java-based tool that reads a Darwin Core Data Package (`datapackage.json`) and produces a [Mermaid.js](https://mermaid-js.github.io/) diagram showing foreign key relationships between tables.

## 🚀 How to Use

1. Clone the repo
2. Make sure Java is installed
3. Run the program:

```bash
javac -cp "lib/*" src/MermaidDiagramGenerator.java
java -cp ".:lib/*:src" MermaidDiagramGenerator

