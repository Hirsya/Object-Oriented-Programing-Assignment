# Inheritance and Polymorphism Assignment

A geometric shape calculator in Java, built as part of the **Object-Oriented Programming (PBO)** course. This assignment focuses on **inheritance** and **polymorphism**: a common base class (`Bentuk`) defines shared behaviour, while subclasses add their own data and override the calculation/output methods. The same logic is presented through two front-ends — a console program (`Main`) and a Swing GUI (`MenuGUI`).

## Project Structure

| File | Role |
|------|------|
| `Bentuk.java` | Base class. Stores the shape's colour and provides the shared `printInfo()`. |
| `Lingkaran.java` | `Bentuk` subclass. Represents a circle and calculates its area. |
| `BujurSangkar.java` | `Bentuk` subclass. Represents a square and calculates its area. |
| `Silinder.java` | `Lingkaran` subclass. Represents a cylinder and calculates its volume. |
| `Main.java` | Console program with an interactive menu (`Scanner`). |
| `MenuGUI.java` | Swing GUI version of the calculator. |

## Class Hierarchy

```
Bentuk
├── Lingkaran
│   └── Silinder
└── BujurSangkar
```

## How to Run

Console version:

```bash
javac *.java
java Main
```

GUI version (after compiling):

```bash
java MenuGUI
```

## Class Details

### `Bentuk.java`
The base class shared by every shape. It only knows about the colour; subclasses add the geometry.

- **Variable**
  - `public String warna` — the shape's colour. It is `public` here (not encapsulated) so subclasses can read/write it directly.
- **Constructor** `Bentuk(String warna)` — sets the colour. Because there is no no-argument constructor, every subclass must call `super(warna)`.
- **Methods**
  - `getWarna()` — returns the colour.
  - `setWarna(String)` — updates the colour.
  - `printInfo()` — prints `"Bentuk berwarna <warna>"`. This is the method that subclasses override to show polymorphic behaviour.

### `Lingkaran.java`
Extends `Bentuk` to represent a circle.

- **Variable** — `private double radius`.
- **Constructor** `Lingkaran(double radius, String warna)` — calls `super(warna)` and stores the radius.
- **Methods**
  - `getRadius()` / `setRadius(double)` — read/update the radius.
  - `hitungLuas()` — returns the area using `Math.PI * radius * radius`.
  - `printInfo()` (override) — prints `"Lingkaran <warna>, Luas = <luas>"`.

### `BujurSangkar.java`
Extends `Bentuk` to represent a square.

- **Variable** — `private double sisi` (side length).
- **Constructor** `BujurSangkar(double sisi, String warna)` — calls `super(warna)` and stores the side.
- **Methods**
  - `getSisi()` / `setSisi(double)` — read/update the side length.
  - `hitungLuas()` — returns `sisi * sisi`.
  - `printInfo()` (override) — prints `"Bujursangkar berwarna <warna>, luas = <luas>"`.

### `Silinder.java`
Extends `Lingkaran` (not `Bentuk` directly), because a cylinder is a circle with a height. This shows multi-level inheritance.

- **Variable** — `private double tinggi` (height).
- **Constructor** `Silinder(double tinggi, double radius, String warna)` — calls `super(radius, warna)` to set the radius and colour, then stores the height.
- **Methods**
  - `getTinggi()` / `setTinggi(double)` — read/update the height.
  - `hitungVolume()` — reuses the inherited `hitungLuas()` and multiplies it by the height, so the circle area logic is not duplicated.
  - `printInfo()` (override) — prints `"Warna <warna>, Volume = <volume>"`.

### `Main.java`
A console program that lets the user pick a shape and compute its area/volume.

- Declares a `Bentuk bentuk` variable and assigns a `Lingkaran`, `BujurSangkar`, or `Silinder` depending on the menu choice.
- Calls `bentuk.printInfo()` on that variable, so the correct overridden version runs at runtime — this is **polymorphism** in action.
- Uses a `while` loop menu (`1` Lingkaran, `2` BujurSangkar, `3` Silinder, `0` Keluar).
- Helper methods:
  - `bacaAngka(Scanner, String prompt)` — repeatedly reads a number until valid input is given (handles `NumberFormatException`).
  - `bacaWarna(Scanner)` — reads the colour as text.

### `MenuGUI.java`
The Swing version of the calculator.

- **Variables**
  - `private JFrame frame` — the main window ("Menu Bentuk").
  - `private JComboBox<String> cbJenis` — shape selector (Lingkaran, BujurSangkar, Silinder).
  - `private JTextField tfNilai, tfTinggi, tfWarna` — inputs for the radius/side, height, and colour.
  - `private JButton btnHitung` — the calculate button.
  - `private JLabel lblTinggi` — label for the height field.
- **Methods**
  - Constructor — builds the layout with `GridBagLayout`, wires up the button and combo box listeners, and shows the window.
  - `addRow(...)` — helper that adds a label + field pair to the grid.
  - `updateTinggi()` — enables the height field only when "Silinder" is selected (a circle/square has no height).
  - `hitung()` — reads the input, creates the appropriate object, calls `hitungLuas()`/`hitungVolume()`, and shows the result in a `JOptionPane`. Invalid (non-numeric) input is caught and reported.
  - `main(String[])` — starts the GUI on the Event Dispatch Thread via `SwingUtilities.invokeLater`.

## Notes on Approach

- **Inheritance**: `Lingkaran` and `BujurSangkar` reuse `Bentuk`'s colour handling; `Silinder` reuses `Lingkaran`'s area logic. `super(...)` is always called because the base class has no no-argument constructor.
- **Polymorphism**: `Main` stores different shapes in a `Bentuk` reference and calls `printInfo()`, so the version chosen at runtime depends on the actual object type.
- **Method overriding**: each subclass overrides `printInfo()` to produce its own output while keeping the same method signature.
- **Code reuse**: `Silinder.hitungVolume()` calls the inherited `hitungLuas()` instead of recomputing the circle area, avoiding duplicated logic.
- **Two front-ends, one model**: `Main` (console) and `MenuGUI` (Swing) share the same `Bentuk`/`Lingkaran`/`BujurSangkar`/`Silinder` classes — the calculation logic is never rewritten for the GUI.
- **Input validation**: both the console (`bacaAngka`) and the GUI (`hitung`) handle non-numeric input gracefully instead of crashing.
