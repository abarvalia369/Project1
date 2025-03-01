package project1;

import util.Date;

/**
 * A class that implements a linked list data structure to hold a list of closed accounts.
 * Uses an AccountNode(class, private or public) for nodes of the linked list.
 *
 * @author arpeet barvalia, jonathan john
 */
public class Archive {
    private AccountNode first; //the head node of the linked list

    /**
     * Defines an Account Node as a node for the linked list.
     *
     */
    public static class AccountNode{
        private Account account;
        private AccountNode next;
        private Date close;

        public AccountNode(Account account, Date close){
            this.account = account;
            this.next = null;
            this.close = close;
        }

        public Date getCloseDate() {
            return close;
        }

    }

    public AccountNode getFirst(){
        return first;
    }

    /**
     * Method that adds an account to the front of the linked list.
     *
     * @param account the account to add to the front of the linked list.
     */
    public void add(Account account, Date close) {  //add to front of linked list
        AccountNode node = new AccountNode(account, close);
        node.next = first;
        first = node;
    }

    /**
     * Method to print the linked list in order of most recently to least recently closed.
     *
     */
    public void print() {               //print the linked list
        System.out.println( "*List of closed accounts in the archive." );
        AccountNode node = first;
        while( node != null ){
            if(node.account != null) {
                node.account.setBalance(0.00);
                System.out.println(node.account.toString());
            }
            node = node.next;
        }
        System.out.println( "*end of list." );
    }









}