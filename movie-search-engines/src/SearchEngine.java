/*
 * Name: Gino Angelici
 * PID:  A16779788
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Search Engine implementation.
 *
 * @author Gino Angelici
 * @since ${11/8/22}
 */
public class SearchEngine {
    private static final int movieSearch = 0;
    private static final int studioSearch = 1;
    private static final int ratingSearch = 2;
    private static final int firstActor = 2;

    /**
     * Populate BSTrees from a file
     *
     * @param movieTree  - BST to be populated with actors
     * @param studioTree - BST to be populated with studios
     * @param ratingTree - BST to be populated with ratings
     * @param fileName   - name of the input file
     * @returns false if file not found, true otherwise
     */
    public static boolean populateSearchTrees(
            BSTree<String> movieTree, BSTree<String> studioTree,
            BSTree<String> ratingTree, String fileName
    ) {
        // open and read file
        File file = new File(fileName);
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                // read 5 lines per batch:
                // movie, cast, studios, rating, trailing hyphen
                String movie = scanner.nextLine().trim();
                String cast[] = scanner.nextLine().split(" ");
                String studios[] = scanner.nextLine().split(" ");
                String rating = scanner.nextLine().trim();
                scanner.nextLine();

                makeTreeHelper(movieTree, cast, movie);
                makeTreeHelper(studioTree, studios, movie);
                makeTreeHelper(ratingTree, cast, rating);
                // populate three trees with the information you just read
                // hint: create a helper function and reuse it to build all three trees

            }
            scanner.close();
        } catch (FileNotFoundException e) {
            return false;
        }
        return true;
    }

    /**
     * Helper method to population BSTs
     *
     * @param tree  - BST to be populated with actors
     * @param key - keys to populate given BST
     * @param data - data to be assigned to keys
     */
    private static void makeTreeHelper(BSTree<String> tree, String[] key, String data){
        for (int i = 0; i < key.length; i++) {
            tree.insert(key[i]);
            tree.insertData(key[i], data);
        }
    }


    /**
     * Search a query in a BST
     *
     * @param searchTree - BST to be searched
     * @param query      - query string
     */
    public static void searchMyQuery(BSTree<String> searchTree, String query) {
        // process query
        String[] keys = query.toLowerCase().split(" ");

        // search and output intersection results
        // hint: list's addAll() and retainAll() methods could be helpful
        LinkedList<LinkedList<String>> added = new LinkedList<>();
        LinkedList<String> related = new LinkedList<>();
        if (keys.length > 1) { // if there are more than one input
            if (searchTree.findKey(keys[0])) {
                related.addAll(searchTree.findDataList(keys[0])); // adds all data of first
                                                                  // key to related
            }
            for (int i = 0; i < keys.length; i++) { // add all data to added list
                if (searchTree.findKey(keys[0])) {
                    LinkedList<String> curList = searchTree.findDataList(keys[i]);
                    added.add(curList);
                }
            }
            for (int i = 0; i < added.size(); i++) {
                related.retainAll(added.get(i)); //retain item if present in each list
            }
            print(query, related);
        }

        // search and output individual results
        // hint: list's addAll() and removeAll() methods could be helpful
        LinkedList<String> alreadyThere = new LinkedList<>();
        alreadyThere.addAll(related);
        for (int i = 0; i < keys.length; i++) { //iterate through key and adds data if new data
            LinkedList<String> added2 = new LinkedList<>();
            if (searchTree.findKey(keys[i])) {
                added2.addAll(searchTree.findDataList(keys[i]));
                added2.removeAll(alreadyThere);
            }
            alreadyThere.addAll(added2);
            LinkedList<String> addedUnique = new LinkedList<>();
            for (int j = 0; j < added2.size(); j++) { //remove duplicate ratings
                String check = added2.get(j);
                if (!addedUnique.contains(check)) { //check if rating is duplicate
                    addedUnique.add(check);
                }
            }
            if (!added2.isEmpty() || related.isEmpty()) {
                print(keys[i], addedUnique);
            }
        }
    }

    /**
     * Print output of query
     *
     * @param query     Query used to search tree
     * @param documents Output of documents from query
     */
    public static void print(String query, LinkedList<String> documents) {
        if (documents == null || documents.isEmpty())
            System.out.println("The search yielded no results for " + query);
        else {
            Object[] converted = documents.toArray();
            Arrays.sort(converted);
            System.out.println("Documents related to " + query
                    + " are: " + Arrays.toString(converted));
        }
    }

    /**
     * Main method that processes and query the given arguments
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        // initialize search trees
        BSTree<String> movieTree = new BSTree<>();
        BSTree<String> studioTree = new BSTree<>();
        BSTree<String> ratingTree = new BSTree<>();

        // process command line arguments
        String fileName = args[0];
        int searchKind = Integer.parseInt(args[1]);

        // populate search trees
        populateSearchTrees(movieTree, studioTree, ratingTree, fileName);

        // choose the right tree to query
        String query = "";
        for (int i = firstActor; i < args.length; i++) {
            if (i == args.length - 1) {
                query += args[i];
            } else {
                query += args[i] + " ";
            }
        }
        if (searchKind == movieSearch) {
            searchMyQuery(movieTree, query);
        } else if (searchKind == studioSearch) {
            searchMyQuery(studioTree, query);
        } else if (searchKind == ratingSearch) {
            searchMyQuery(ratingTree, query);
        }
    }
}
