import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class BlockPuzzle {

    public static class TreeNode {
        String val;
        List<TreeNode> children = new LinkedList<>();
        TreeNode parent;
        int depth = 0;

        TreeNode(String data, TreeNode par) {
            val = data;
            parent = par;
            if (parent != null)
                depth = parent.depth + 1;
        }

    }

    public static int speedInterval = 0;
    public final static String finalState = "1234 5678";
    public final static int maxLimit = 1000000;
    public static int limit = 1000000;
    public static TreeNode root;
    public static HashSet<String> previousMoves = new HashSet<>();

    public static JFrame frame;
    public static GUI gui;

    public static void createGUI() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        frame = new JFrame("8-Puzzle");
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        gui = new GUI();
    }
    public static void setGUI() {
        gui.setProperties();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setResizable(false);
        frame.setContentPane(gui.getRoot());
        frame.setVisible(true);
    }


    public static void printSolution(List<String> moves) {
        if (moves == null) {
            gui.move(root.val);
            gui.SolutionNotPossibleException();
            return;
        }
        for (String move : moves) {
            gui.move(move);
            try {
                Thread.sleep(speedInterval);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
        gui.totalMoves(moves.size()-1);
    }


    public static List<String> getValidMoves(String state) {
        List<String> states = new LinkedList<>();
        int[] offsets = {1, -1, 3, -3};
        int hole = state.indexOf(' ');
        for (int offset : offsets) {
            StringBuilder sb = new StringBuilder(state);
            if (((hole + 1) % 3 == 0 && offset == 1) || ((hole % 3) == 0 && offset == -1))
                continue;
            int j = hole + offset;
            if (j >= 0 && j < 9) {
                char l = sb.charAt(hole), r = sb.charAt(j);
                sb.setCharAt(hole, r);
                sb.setCharAt(j, l);
                states.add(sb.toString());
            }
        }
        return states;
    }

    public static void makeChildren(TreeNode node) {
        List<String> moves = getValidMoves(node.val);
        for (String move : moves) {
            if (!previousMoves.contains(move)) {
                TreeNode child = new TreeNode(move, node);
                node.children.add(child);
                previousMoves.add(move);
            }
        }
    }

    public static List<String> tracePath(TreeNode node) {
        List<String> path = new LinkedList<>();
        while (node != null) {
            path.add(0, node.val);
            node = node.parent;
        }
        return path;
    }

    public static List<String> BFS() {
        if (root == null)
            return null;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            TreeNode node = queue.remove();
            if (node.val.equals(finalState))
                return tracePath(node);
            makeChildren(node);
            queue.addAll(node.children);
        }
        return null;
    }

    public static List<String> DLS() {
        if (root == null) return null;
        Stack<TreeNode> stack = new Stack<>();
        stack.add(root);
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            if (node.val.equals(finalState)) return tracePath(node);
            if (node.depth + 1 < limit) {
                makeChildren(node);
                stack.addAll(node.children);
            }
        }
        return null;
    }

    public static List<String> IDS() {
        List<String> path = null;
        for (int i = 0; i < maxLimit; ++i) {
            limit = i;
            path = DLS();
            if (path != null)
                break;
        }
        return path;
    }

    public static void readFromFile(String filename) {
        File file = new File(filename);
        try {
            Scanner scanner = new Scanner(file);
            String string = scanner.nextLine();
            string += scanner.nextLine();
            string += scanner.nextLine();
            for (char c : finalState.toCharArray()) {
                if (string.indexOf(c) == -1 || string.length() > 9) {
                    gui.InvalidInitialStateException();
                    System.exit(1);
                }
            }
            root = new TreeNode(string, null);
            previousMoves.add(root.val);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            gui.FileNotFoundException();
            System.exit(1);
        }
        catch(NoSuchElementException e){
            e.printStackTrace();
            gui.InvalidInitialStateException();
            System.exit(1);
        }
    }

    public static String[] preference() {
        JPanel fields = new JPanel(new GridLayout(2, 1));
        JComboBox<String> comboBox1 = new JComboBox<>(new String[]{"BFS", "DFS", "IDS"});
        JComboBox<String> comboBox2 = new JComboBox<>(new String[]{"Fast", "Slow", "Instant"});

        fields.add(comboBox1);
        fields.add(comboBox2);

        int dialog = JOptionPane.showConfirmDialog(null, fields, "Preferences", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (dialog != 0)
            System.exit(0);
        String[] result = new String[2];
        result[0] = comboBox1.getSelectedItem().toString();
        result[1] = comboBox2.getSelectedItem().toString();
        return result;
    }

    public static void main(String[] args) {
        try {
            createGUI();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        readFromFile("File.txt");
        String[] selection = preference();
        setGUI();

        switch (selection[1]) {
            case "Fast" -> speedInterval = 250;
            case "Slow" -> speedInterval = 600;
            case "Instant" -> speedInterval = 0;
        }

        switch (selection[0]) {
            case "BFS" -> printSolution(BFS());
            case "DFS" -> printSolution(DLS());
            case "IDS" -> printSolution(IDS());
        }
    }
}