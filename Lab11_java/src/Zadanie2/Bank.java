package Zadanie2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class Bank {
    private ArrayList<Client> clients;
    private final int time;

    public Bank(int numberOfClients, int time) {
        this.time = time;
        clients = new ArrayList<>();
        HashSet<Integer> accountNumbers = new HashSet<>();
        Random random = new Random();

        while (accountNumbers.size() < numberOfClients) {
            int accountNumber = random.nextInt(Integer.MAX_VALUE);
            accountNumbers.add(accountNumber);
        }

        for (int accountNumber : accountNumbers) {
            clients.add(new Client(accountNumber));
        }
    }

    public void run() {
        clients.forEach(Thread::start);
        try {
            Thread.sleep(time);
        } catch (InterruptedException ignored) {
        }
        clients.forEach(Thread::interrupt);

        for (Client client : clients) {
            System.out.println("Client " + client.getAccountNumber() + " balance: " + client.getBalance());
        }
    }

    private class Client extends Thread {
        private final int accountNumber;
        private int balance;
        private final static int DEFAULT_BALANCE = 1000;

        public Client(int accountNumber) {
            this.accountNumber = accountNumber;
            this.balance = DEFAULT_BALANCE;
        }

        public Client(int accountNumber, int balance) {
            this.accountNumber = accountNumber;
            this.balance = balance;
        }

        public void run() {
            while (true) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    return;
                }

                Random random = new Random();
                int option = random.nextInt(3);
                int value = random.nextInt(DEFAULT_BALANCE / 10, DEFAULT_BALANCE);

                if (option == 0) {
                    deposit(value);
                } else if (option == 1) {
                    withdraw(value);
                } else {
                    transferToRandom(value);
                }
            }
        }

        private synchronized boolean changeBalance(int value) {
            if (balance + value < 0) {
                return false;
            }
            balance += value;
            return true;
        }

        public boolean deposit(int value) {
            if (value < 0) {
                return false;
            }

            return changeBalance(value);
        }

        public boolean withdraw(int value) {
            if (value < 0) {
                return false;
            }

            return changeBalance(-value);
        }

        public void transferToRandom(int value) {
            if (balance < value) {
                return;
            }

            if (clients.size() < 2) {
                return;
            }

            Random random = new Random();
            Client client = null;
            while (client == null || client == this) {
                client = clients.get(random.nextInt(clients.size()));
            }

            if (changeBalance(-value)) {
                client.changeBalance(value);
            }
        }

        public int getBalance() {
            return balance;
        }

        public int getAccountNumber() {
            return accountNumber;
        }
    }
}


