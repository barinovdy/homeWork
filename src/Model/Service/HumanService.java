package Model.Service;

import Model.Builder.HumanBuilder;
import Model.FamilyTree.FamilyTree;
import Model.Human.Human;
import Model.Human.Gender;
import Model.Writer.Writer;
import Model.Writer.FileHandler;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class HumanService {
    private FamilyTree<Human> familyTree;
    private HumanBuilder builder;
    private Writer writer;

    public HumanService() {
        familyTree = new FamilyTree<>();
        builder = new HumanBuilder();
        this.writer = new FileHandler(familyTree);
    }

    public String addHuman(String name, Gender gender, LocalDate birthDate, LocalDate deathDate) {
        Human human = builder.setName(name)
                .setGender(gender)
                .setBirthDate(birthDate)
                .setDeathDate(deathDate)
                .build();
        familyTree.addHuman(human);
        return name + " добавлен в семейное дерево.";
    }

    private String memberNotFoundError(){
        return "Человек с указанным ID отсутствует в дереве.";
    }

    public String addSpouse(int humanID, int spouseID) {
        for (Human human : familyTree) {
            if (human.getId() == humanID){
                for (Human spouse : familyTree) {
                    if (spouse.getId() == spouseID){
                        familyTree.setMarried(human, spouse);
                        return "У человека с ID" + humanID + " добавлен супруг с ID" + spouseID + ".";
                    }
                }
            }
        }
        return memberNotFoundError();
    }

    public String deleteSpouse(int humanID) {
        for (Human human : familyTree) {
            if (human.getId() == humanID){
                Human spouse = human.getSpouse();
                familyTree.setDivorsed(human, spouse);
                return "У человека с ID" + humanID + " удалена информация о супруге.";
            }
        }
        return memberNotFoundError();
    }

    public String addParent(int humanID, int parentID){
        for (Human human : familyTree) {
            if (human.getId() == humanID){
                for (Human parent : familyTree) {
                    if (parent.getId() == parentID){
                        familyTree.addParent(human, parent);
                        return "У человека с ID" + humanID + " добавлен родитель с ID" + parentID + ".";
                    }
                }
            }
        }
        return memberNotFoundError();
    }

    public String addChild(int humanID, int childID){
        for (Human human : familyTree) {
            if (human.getId() == humanID){
                for (Human child : familyTree) {
                    if (child.getId() == childID){
                        familyTree.addChild(human, child);
                        return "У человека с ID" + humanID + " добавлен ребенок с ID" + childID + ".";
                    }
                }
            }
        }
        return memberNotFoundError();
    }

    public String deleteHuman(int humanID) {
        familyTree.deleteHuman(humanID);
        return "Человек с ID" + humanID + " удален из семейного дерева.";
    }

    public String saveToFile(String filename) throws IOException {
        writer.saveToFile(filename, familyTree);
        return "Семейное дерево сохранено в файл \"" + filename + "\".";
    }

    public String loadFromFile(String filename) throws IOException, ClassNotFoundException {
        familyTree = writer.readFromFile(filename);
        return "Семейное дерево загружено из файла \"" + filename + "\".";
    }

    public String getHumanListInfo(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Семейное дерево:\n");
        for (Human human : familyTree) {
            stringBuilder.append(human);
            stringBuilder.append("\n");
        }
        stringBuilder.append("Всего " + familyTree.showFamilyTreeSize() + " элементов.\n");
        return stringBuilder.toString();
    }

    public String sortById(){
        familyTree.SortById();
        return "Семейное дерево отсортировано по Id.";
    }

    public String sortByName(){
        familyTree.SortByName();
        return "Семейное дерево отсортировано по имени.";
    }

    public String sortByAge(){
        familyTree.SortByAge();
        return "Семейное дерево отсортировано по возрасту.";
    }

    public String sortByChildrenNum(){
        familyTree.SortByChildrenNum();
        return "Семейное дерево отсортировано по количеству детей.";
    }

    public String sortByBirthdate(){
        familyTree.SortByBirthdate();
        return "Семейное дерево отсортировано по дате рождения.";
    }

    public FamilyTree<Human> getFamilyTree(){
        return familyTree;
    }

    @Override
    public String toString() {
        return getHumanListInfo();
    }
}
