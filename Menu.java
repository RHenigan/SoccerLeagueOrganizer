import com.teamtreehouse.model.Player;
import com.teamtreehouse.model.Team;

import java.util.List;
import java.util.Scanner;

public class Menu{

    public String printMenu(){
        Scanner scan = new Scanner(System.in);
        System.out.println("MENU:");
        System.out.println("--------------------------------------------------------------------------");
        System.out.println("CREATE: Create a new team");
        System.out.println("ADD: Add a player to a team");
        System.out.println("REMOVE: Remove a player from a team");
        System.out.println("REPORT: View a report of a team by height");
        System.out.println("BALANCE: View a League Balance Report");
        System.out.println("ROSTER: View roster");
        System.out.println("QUIT: Exits the program");

        System.out.printf("%nSelect an option: ");

        String choice = scan.nextLine();
        return choice;
    }

    public void printTeams(List<Team> teams) {
        int i = 1;
        for(Team team : teams) {
            System.out.printf("%d.) %s coached by %s%n", i, team.getTeamName(), team.getCoachName());
            i++;
        }
    }

    public void printPlayers(List<Player> players) {
        int i = 1;
        String exp = "";
        for(Player player : players) {
            if (player.isPreviousExperience()) {
                exp = "experienced";
            } else {
                exp = "inexperienced";
            }
            System.out.printf("%d.) %s %s (%d inches - %s)%n", i, player.getFirstName(), player.getLastName(), player.getHeightInInches(), exp);
            i++;
        }
    }
}