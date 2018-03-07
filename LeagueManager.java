import com.teamtreehouse.model.Player;
import com.teamtreehouse.model.Players;
import com.teamtreehouse.model.Team;

import java.util.*;


public class LeagueManager {
  
  private static Menu menu;
  private static List<Team> teams = new ArrayList<>();
  private static List<Player> playerList = new ArrayList<>();
  private static TreeMap<Integer, Player> mapList = new TreeMap<>();

  public static void main(String[] args) {
    Player[] players = Players.load();
    System.out.printf("There are currently %d registered players.%n", players.length);
    // Your code here!
    
    int maxTeams = 0;
    String choice = "";
    menu = new Menu();
    for (Player player : players) {
        playerList.add(player);
    }
    maxTeams = playerList.size();
    while (true) {
        choice = menu.printMenu();
        Collections.sort(teams);
        Collections.sort(playerList);

        switch(choice.toUpperCase()) {
            case "CREATE":
               if (teams.size() < maxTeams) {
                    teams.add(createTeam());
                    } else {
                        System.out.println("There are not enough players to add another team");
                    }
                    break;
                case "ADD":
                    addToTeam(playerList);
                    break;
                case "REMOVE":
                    removeFromTeam();
                    break;
                case "REPORT":
                    printReport();
                    break;
                case "BALANCE":
                    printBalance();
                    break;
                case "ROSTER":
                    printRoster();
                    break;
                case "QUIT":
                    System.exit(0);
                    break;
                default:
                    System.out.println("-----------------------------");
                    System.out.println("Please select a valid choice!");
                    System.out.println("-----------------------------");
                    break;
            }
        }
    }

    private static void printBalance() {
        System.out.println("Team Name - Experienced:Inexperienced");
        for (Team team : teams) {
            int expCount = 0;
            int inExpCount = 0;
            List<Player> teamPlayers = team.getPlayersAsList();
            for (Player player : teamPlayers) {
                if(player.isPreviousExperience()) {
                    expCount++;
                } else {
                    inExpCount++;
                }
            }
            System.out.printf("%s - %d:%d%n", team.getTeamName(), expCount, inExpCount);
        }
    }

    private static void printReport() {
        Scanner scan = new Scanner(System.in);

        Collections.sort(teams);
        menu.printTeams(teams);
        System.out.println("Select a team (by number)");
        int team = scan.nextInt();

        List<Player> teamPlayers = teams.get(team-1).getPlayersAsList();

        ArrayList<String> shorter = new ArrayList<>();
        ArrayList<String> average = new ArrayList<>();
        ArrayList<String> taller = new ArrayList<>();

        for (Player player : teamPlayers) {
            if ((player.getHeightInInches() >= 35) && (player.getHeightInInches() <= 40)) {
                shorter.add(player.getInfo());
            }
            if ((player.getHeightInInches() >= 41) && (player.getHeightInInches() <= 46)) {
                average.add(player.getInfo());
            }
            if ((player.getHeightInInches() >= 47) && (player.getHeightInInches() <= 50)) {
                taller.add(player.getInfo());
            }
        }

        System.out.println("Short Team Members (35-40)");
        for (String playerInfo : shorter){
            System.out.println(playerInfo);
        }
        System.out.println("Average Team Members (41-46)");
        for (String playerInfo : average){
            System.out.println(playerInfo);
        }
        System.out.println("Tall Team Members (47-50)");
        for (String playerInfo : taller){
            System.out.println(playerInfo);
        }

        teams.get(team-1).setExpPerc();
        System.out.printf("This team consists of %d%% experience%n", teams.get(team-1).getExpPerc());

    }

    private static void removeFromTeam() {
        Scanner scan = new Scanner(System.in);

        Collections.sort(teams);
        menu.printTeams(teams);
        System.out.println("Select a team (by number)");
        int team = scan.nextInt();

        List<Player> teamPlayers = teams.get(team-1).getPlayersAsList();

        if(teamPlayers.size() <= 0) {
            System.out.println("No Players to remove");
        } else {
            Collections.sort(teamPlayers);
            menu.printPlayers(teamPlayers);
            System.out.println("Select a player (by number)");
            int player = scan.nextInt();

            playerList.add(teamPlayers.get(player - 1));
            teams.get(team-1).removePlayer(teamPlayers.get(player-1));
        }

    }

    private static void addToTeam(List<Player> players) {
        Scanner scan = new Scanner(System.in);

        Collections.sort(players);
        menu.printPlayers(players);
        System.out.println("Select a player (by number)");
        int player = scan.nextInt();

        Collections.sort(teams);
        menu.printTeams(teams);
        System.out.println("Select a team (by number)");
        int team = scan.nextInt();

        List<Player> teamPlayers = teams.get(team-1).getPlayersAsList();

        if (teamPlayers.size() >= 11) {
            System.out.println("This team is at capacity");
        } else {
            teams.get(team-1).addPlayer(players.get(player - 1));
            players.remove(player - 1);
        }
    }

    private static Team createTeam() {
        Scanner scan = new Scanner(System.in);

        System.out.println("Give the new team a name");
        String teamName = scan.nextLine();

        System.out.printf("Who is the coach of %s%n", teamName);
        String coachName = scan.nextLine();

        Team team = new Team(teamName, coachName);

        return team;
    }

    private static void printRoster() {
        for (Team team : teams) {
            List<Player> teamPlayers = team.getPlayersAsList();
            Collections.sort(teamPlayers);
            System.out.printf("%s coached by %s: %n", team.getTeamName(), team.getCoachName());
            for (Player player : teamPlayers) {
                System.out.println(player.getInfo());
            }
            System.out.println("");
        }
    }
}
