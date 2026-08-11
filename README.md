# RailwayProjectStructured

This is a structured version of the original console railway project. The
original `src` directory remains unchanged.

## Responsibilities

- `Application` starts the program.
- `RailwaySystem` owns and initializes shared data.
- `model` contains domain objects.
- `structure` contains custom data structures.
- `service` contains business rules.
- `menu` handles console input and output.

## Compile and run

From this directory in PowerShell, compile all Java files into an `out`
directory, then run:

```powershell
New-Item -ItemType Directory -Path out -Force
javac -d out (Get-ChildItem src -Recurse -Filter *.java).FullName
java -cp out railway.Application
```

The project requires a JDK. A JRE by itself is not sufficient to compile it.
