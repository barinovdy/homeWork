package View.Commands;

import View.ConsoleUI;

public class SortByName extends Command{

    public SortByName(ConsoleUI consoleUI) {
        super(consoleUI);
        description = "Отсортировать семейное дерево по имени";
    }

    public void execute() {
        consoleUI.sortByName();
    }
}
