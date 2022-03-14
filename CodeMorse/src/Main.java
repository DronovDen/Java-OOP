import parser.CommandParser;

public class Main {
    public static void main(String[] args) {
        CommandParser parser = new CommandParser();
        System.out.print("Please enter the command: ");
        parser.parseCommand();
    }
}



//TreeSet хранит внутри себя TreeMap - хорошо для сортировки
//TreeSet быстрее, если много коллизий по hashCode в случе HashSet

//HashSet хорош, при многократном добавлении
//HasMap считает по хешу ключа сдвиг в массиве, куда положить
//Добавление у HashMap быстрее
//HashSet в большинстве задач быстрее!!!

