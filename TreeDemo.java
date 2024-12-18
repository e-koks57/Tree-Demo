import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class TreeNode {
    String name;
    List<TreeNode> children;
    TreeNode parent;

    TreeNode(String name, TreeNode parent) {
        this.name = name;
        this.parent = parent;
        this.children = new ArrayList<>();
    }

    void addChild(TreeNode child) {
        children.add(child);
    }

    TreeNode findChild(String name) {
        for (TreeNode child : children) {
            if (child.name.equals(name)) {
                return child;
            }
        }
        return null;
    }
}

public class TreeDemo {
    private static TreeNode root;
    private static TreeNode currentNode;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        root = new TreeNode("root", null);
        currentNode = root;

        System.out.println("Welcome to the TreeDemo Simulator!");
        printInstructions();

        while (true) {
            System.out.print("\n[" + currentNode.name + "]> ");
            String command = scanner.nextLine();

            if (command.equalsIgnoreCase("exit")) {
                System.out.println("Exiting TreeDemo Simulator. Goodbye!");
                break;
            }

            handleCommand(command);
        }

        scanner.close();
    }

    private static void printInstructions() {
        System.out.println("Available commands:");
        System.out.println("  mkdir <name>  - Create a subfolder");
        System.out.println("  cd <name>     - Change to a subfolder");
        System.out.println("  cd ..         - Move to the parent folder");
        System.out.println("  ls            - List subfolders");
        System.out.println("  exit          - Exit the simulator");
    }

    private static void handleCommand(String command) {
        String[] parts = command.split(" ", 2);
        switch (parts[0].toLowerCase()) {
            case "mkdir":
                if (parts.length < 2 || parts[1].isBlank()) {
                    System.out.println("Usage: mkdir <name>");
                } else {
                    createFolder(parts[1]);
                }
                break;
            case "cd":
                if (parts.length < 2 || parts[1].isBlank()) {
                    System.out.println("Usage: cd <name>");
                } else {
                    changeDirectory(parts[1]);
                }
                break;
            case "ls":
                listFolders();
                break;
            default:
                System.out.println("Unknown command. Type 'help' for instructions.");
        }
    }

    private static void createFolder(String name) {
        if (currentNode.findChild(name) != null) {
            System.out.println("Folder already exists.");
        } else {
            currentNode.addChild(new TreeNode(name, currentNode));
            System.out.println("Folder '" + name + "' created.");
        }
    }

    private static void changeDirectory(String name) {
        if (name.equals("..")) {
            if (currentNode.parent == null) {
                System.out.println("Already at the root directory.");
            } else {
                currentNode = currentNode.parent;
                System.out.println("Moved to folder: " + currentNode.name);
            }
        } else {
            TreeNode nextNode = currentNode.findChild(name);
            if (nextNode != null) {
                currentNode = nextNode;
                System.out.println("Moved to folder: " + name);
            } else {
                System.out.println("Folder not found.");
            }
        }
    }

    private static void listFolders() {
        if (currentNode.children.isEmpty()) {
            System.out.println("No subfolders.");
        } else {
            for (TreeNode child : currentNode.children) {
                System.out.println("- " + child.name);
            }
        }
    }
}
