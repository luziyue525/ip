# Week 5 regression checks

Use a fresh working directory to avoid changing existing saved tasks.
Build with Java 25: `./gradlew check shadowJar` (Windows: `gradlew.bat`).
Launch the GUI with `java -jar build/libs/duke.jar`, or the CLI with
`java -ea -cp build/libs/duke.jar zsiggy.Duke`.

| Input / action | Expected result |
| --- | --- |
| `t read book` | Adds `[T][ ]read book`. |
| `todo buy milk` | Original command still adds a todo. |
| `t`, then `t   ` | Description error; task count does not change. |
| `task read book` | Unknown-command error; no task added. |
| `list` | Both valid tasks appear in insertion order. |
| `find book` | Only `read book` appears. |
| `mark 0`, `unmark abc`, `delete 99` | Helpful errors; existing tasks unchanged. |
| `mark 1`, `unmark 1` | Completion status changes and changes back. |
| Close and restart, then `list` | Both valid tasks load from saved data. |
| `delete 1`, then `find book` | No match; deleted task stays removed. |

JUnit covers assertion contracts, search semantics, invalid indices, and
alias validation. An isolated CLI integration test also checks alias
creation, saved file contents, and reload behavior.
