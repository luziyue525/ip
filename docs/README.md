# Zsiggy User Guide

![Zsiggy GUI](Ui.png)

Zsiggy is a task-management chatbot that helps you keep track of todos, deadlines, and events using simple text commands.

Zsiggy may complain a little while helping you, but your tasks will still get organised.

## Quick Start

1. Launch Zsiggy.
2. Enter a command.
3. Zsiggy will respond and update your task list where applicable.

Commands are **case-sensitive**.

---

## Viewing all tasks

Use:

```text
list
```

Zsiggy displays all currently saved tasks.

Example response:

```text
Fine. Here's what you've dumped on me:
1. [T][ ] read book
2. [D][ ] submit assignment (by: Sep 20 2026)
```

---

## Adding a todo

Use:

```text
todo DESCRIPTION
```

Example:

```text
todo read book
```

Example response:

```text
Got it. Added to your never-ending pile:
[T][ ] read book
```

A description is required.

If you enter `todo` without a description, Zsiggy will respond:

```text
Oi. A todo needs an actual description. I can't organise invisible tasks.
```

---

## Adding a todo with a shortcut

You can use:

```text
t DESCRIPTION
```

as a shorter form of:

```text
todo DESCRIPTION
```

Example:

```text
t drink water
```

This behaves the same as:

```text
todo drink water
```

The description is still required.

---

## Adding a deadline

Use:

```text
deadline DESCRIPTION /by YYYY-MM-DD
```

Example:

```text
deadline submit assignment /by 2026-09-20
```

Example response:

```text
Tick-tock. Added this ticking time bomb:
[D][ ] submit assignment (by: Sep 20 2026)
```

Dates must be entered using:

```text
YYYY-MM-DD
```

For example:

```text
2026-09-20
```

If the date does not exist or is not in a valid format, such as:

```text
deadline submit assignment /by 2026-02-30
```

Zsiggy responds:

```text
Oi. That's not a real date. Use YYYY-MM-DD.
```

If the required `/by` format is missing, Zsiggy provides the correct command format.

---

## Adding an event

Use:

```text
event DESCRIPTION /from YYYY-MM-DD /to YYYY-MM-DD
```

Example:

```text
event tutorial /from 2026-09-20 /to 2026-09-21
```

Example response:

```text
Locked it into your schedule:
[E][ ] tutorial (from: Sep 20 2026 to: Sep 21 2026)
```

A description, start date, and end date are required.

Dates must use the `YYYY-MM-DD` format.

If an invalid date is entered, Zsiggy responds:

```text
Oi. That's not a real date. Use YYYY-MM-DD.
```

The event also cannot end before it starts.

For example:

```text
event holiday /from 2026-09-30 /to 2026-09-20
```

produces:

```text
Oi. Time travel again? The event can't end before it starts.
```

A same-day event is allowed.

---

## Marking a task as done

Use:

```text
mark INDEX
```

Example:

```text
mark 1
```

Example response:

```text
Wait, you actually finished something? Wonders never cease.
Marked this one done:
[T][X] read book
```

`INDEX` refers to the task number shown by the `list` command.

---

## Marking a task as not done

Use:

```text
unmark INDEX
```

Example:

```text
unmark 1
```

Example response:

```text
Caught you faking it, huh?
Whatever, it's unmarked now:
[T][ ] read book
```

---

## Deleting a task

Use:

```text
delete INDEX
```

Example:

```text
delete 1
```

Zsiggy removes the specified task and reports how many tasks remain.

Example response:

```text
Finally, one less thing cluttering your life:
[T][ ] read book
Now you've got 2 task(s) left.
```

---

## Finding tasks

Use:

```text
find KEYWORD
```

Example:

```text
find book
```

Zsiggy displays tasks containing the given keyword.

Example response:

```text
Fine. Here are the matching tasks:
1. [T][ ] read book
```

A keyword is required.

If none is provided:

```text
find
```

Zsiggy responds:

```text
Oi. You want me to find... what exactly? Give me a keyword.
```

---

## Invalid task numbers

Commands such as `mark`, `unmark`, and `delete` require an existing task number.

If the specified task does not exist, Zsiggy responds:

```text
Oi. That task doesn't exist.
```

---

## Unknown commands

If Zsiggy does not recognise a command, it responds:

```text
Oi. That's not a command I understand.
```

---

## Exiting Zsiggy

Use:

```text
bye
```

Zsiggy responds:

```text
Hmph. Bye. Go drink your green milk tea.
```

---

## Saving tasks

Zsiggy automatically saves your tasks when you:

* add a task;
* mark or unmark a task;
* delete a task.

Saved tasks are loaded again when Zsiggy is restarted.

---

## Command Summary

| Action                    | Command                                             |
| ------------------------- | --------------------------------------------------- |
| View all tasks            | `list`                                              |
| Add a todo                | `todo DESCRIPTION`                                  |
| Add a todo using shortcut | `t DESCRIPTION`                                     |
| Add a deadline            | `deadline DESCRIPTION /by YYYY-MM-DD`               |
| Add an event              | `event DESCRIPTION /from YYYY-MM-DD /to YYYY-MM-DD` |
| Mark task as done         | `mark INDEX`                                        |
| Mark task as not done     | `unmark INDEX`                                      |
| Delete task               | `delete INDEX`                                      |
| Find tasks                | `find KEYWORD`                                      |
| Exit Zsiggy               | `bye`                                               |
