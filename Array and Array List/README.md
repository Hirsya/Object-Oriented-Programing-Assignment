# Array and Array List Assignment

A simple bank account application in Java, built as part of the **Object-Oriented Programming (PBO)** course. This assignment focuses on using an **ArrayList** as an in-memory data store and applying **encapsulation**, with a Swing GUI that navigates between screens using a `CardLayout`. `Akun` is the data model, while `Bank` is the GUI application that drives it.

## Project Structure

| File | Role |
|------|------|
| `Akun.java` | Account model. Holds the account data and provides the static operations to register and log in. |
| `Bank.java` | Swing GUI application: login, register, and dashboard screens plus deposit/withdraw logic. |

## How to Run

```bash
javac *.java
java Bank
```

## Class Details

### `Akun.java`
The data model for a single bank account. Also acts as the account store through a static `ArrayList`.

- **Variables**
  - `private String nama` — account holder's name.
  - `private double uang` — current balance.
  - `private String password` — account password.
  - `private int transaksi` — number of transactions performed.
  - `private static ArrayList<Akun> daftar` — the shared list that holds every registered account. `static` because all accounts belong to the same bank, so the list is shared across all instances.
- **Constructors**
  - `Akun()` — default constructor; sets the name to `"Belum mengisi nama"`, balance to `0`, and password to empty.
  - `Akun(String nama, double uang, String password)` — full constructor used when registering a new account.
- **Getters / Setters**
  - `getNama()`, `getUang()`, `getPassword()`, `getTransaksi()` — read the fields.
  - `setNama(String)`, `setPassword(String)` — update name and password.
- **Methods**
  - `deposit(double uang)` — adds to the balance and increments the transaction count.
  - `tarikUang(double uang)` — subtracts from the balance and increments the transaction count.
  - `static boolean daftar(String nama, double uang, String pass)` — creates a new account and adds it to `daftar`. Returns `false` if the name is already registered.
  - `static Akun login(String nama, String pass)` — returns the matching account, or `null` if the name/password is wrong.

### `Bank.java`
The Swing GUI application. It builds three screens and switches between them with a `CardLayout`.

- **Variables**
  - `private final CardLayout cardLayout` / `private final JPanel cards` — the container that holds and switches between the screens.
  - `private Akun aktif` — the currently logged-in account; `null` when nobody is logged in.
  - `private JTextField loginNama, regNama, regUang` — input fields for login and registration.
  - `private JPasswordField loginPass, regPass` — password fields for login and registration.
  - `private JLabel dashNama, dashUang, dashTransaksi` — labels that display data on the dashboard.
- **Entry point**
  - `main(String[])` — calls `SwingUtilities.invokeLater` so the GUI is created on the Event Dispatch Thread.
  - `start()` — creates the three screens (`login`, `register`, `dashboard`) and shows the main `JFrame` ("Bank Sederhana", 400×300, centered).
- **Screens**
  - `panel(String judul, JPanel isi)` — helper that wraps a screen with a title and padding.
  - `buatLogin()` — login form. On success it stores the account in `aktif` and refreshes the dashboard; the "Daftar" button switches to the register screen.
  - `buatDaftar()` — registration form. Validates that all fields are filled and the initial balance is a non-negative number, then calls `Akun.daftar(...)`.
  - `buatDasbor()` — dashboard showing the account name, balance, and transaction count, with Deposit, Withdraw, and Logout buttons.
- **Logic**
  - `transaksi(boolean isDeposit)` — reads an amount from an input dialog, validates it is a positive number, checks for sufficient balance when withdrawing, and calls `deposit`/`tarikUang`.
  - `refreshDasbor()` — updates the dashboard labels and switches to the dashboard screen.

## Notes on Approach

- **ArrayList as the store**: all accounts live in a single `static ArrayList<Akun>`, so data persists for the lifetime of the program without a database.
- **Encapsulation**: `Akun` fields are `private` and only exposed through getters/setters, so data is only changed through controlled methods.
- **Static members**: `daftar` and the `daftar`/`login` methods are static because they operate on the whole collection rather than a single account.
- **GUI navigation**: `CardLayout` is used to switch between login, register, and dashboard without opening new windows.
- **Input validation**: registration and transactions reject empty fields, non-numeric input, negative amounts, duplicate names, and withdrawals larger than the balance.
- **Transaction tracking**: every deposit and withdrawal increments `transaksi`, which the dashboard displays.
