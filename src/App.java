import java.util.Scanner;

import menu.DynamicManagementMenu;

public class App {
    public static void main(String[] args) throws Exception {
        try (Scanner sc = new Scanner(System.in)) {
            DynamicManagementMenu dynamicMenu = new DynamicManagementMenu();
            while (true) {
                System.out.println("STUDENT MANAGEMENT APPLICATION MENU");
                System.out.println("Use Dyanamic CRUD flow(Press 1)");
                System.out.println("Use static CRUD flow(Press 2)");
                System.out.println("Exit (Press 3)");

                String choice = sc.nextLine();

                switch (choice) {
                    case "1":
                        System.out.println("Using dynamic workflow");
                        dynamicMenu.runDynamicFlow();
                        break;
                    case "2":
                        System.out.println("Using static workflow");
                        break;
                    case "3":
                        System.out.println("Bye!");
                        return;
                    default:
                        System.out.println("Invalid choice!");
                }
            }
        }
    }

}
