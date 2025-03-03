package util;
import project1.*;

import project1.AccountDatabase;

public class Sort {


    public static void ListSort(List<Account> accountList, char key) {
        int n = accountList.size();
        for (int i = 0; i < n - 1; i++) {
            int min = i;
            for (int j = i + 1; j < n; j++) {
                if (compare(accountList.get(j), accountList.get(min), key) < 0) {
                    min = j;
                }
            }
                //swapping
            if (min != i) {Account temp = accountList.get(i);
                    accountList.set(i, accountList.get(min));
                    accountList.set(min, temp);
                }
            }
        }


        public static void AccountSort(AccountDatabase Database, char key) {
            List<Account> accountList = new List<>();
            for (int i = 0; i < Database.size(); i++) {
                accountList.add(Database.get(i));
            }
            ListSort(accountList, key); //sorting
            for (int i = 0; i < Database.size(); i++) {
                Database.set(i, accountList.get(i));
            }
        }


    private static int compare(Account firstAcc, Account secondAcc, char key) {
        switch (key) {
            case 'B':
                String branchA = firstAcc.getNumber().getBranch().getCounty() + firstAcc.getNumber().getBranch().getBranchName();
                String branchB = secondAcc.getNumber().getBranch().getCounty() + secondAcc.getNumber().getBranch().getBranchName();
                return branchA.compareTo(branchB);

            case 'H':
                int holderCompare = firstAcc.getHolder().compareTo(secondAcc.getHolder());
                if (holderCompare != 0) return holderCompare;
                return firstAcc.getNumber().compareTo(secondAcc.getNumber());

            case 'T':
                int typeCompare = firstAcc.getNumber().getAccountType().getCode().compareTo(secondAcc.getNumber().getAccountType().getCode());
                if (typeCompare != 0) return typeCompare;
                return firstAcc.getNumber().compareTo(secondAcc.getNumber());

            default:
                throw new IllegalArgumentException("Invalid sorting key: " + key);
        }
    }
}
