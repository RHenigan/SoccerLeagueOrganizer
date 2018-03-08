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
        Map<Boolean, Integer> expMap = new TreeMap<>();
        System.out.println("Team Name - Experienced:Inexperienced");
        for (Team team : teams) {
            expMap.clear();
            int c = 0;
            List<Player> teamPlayers = team.getPlayersAsList();
            for (Player player : teamPlayers) {
              if (expMap.get(player.isPreviousExperience()) == null) {
                expMap.put(player.isPreviousExperience(), 1);
              } else {
                c = expMap.get(player.isPreviousExperience());
                expMap.put(player.isPreviousExperience(), ++c);
              }
            }  
          System.out.printf("%s - %d:%d%n", team.getTeamName(), expMap.get(true), expMap.get(false));
        }
    }

    private static void printReport() {
        Scanner scan = new Scanner(System.in);

        Team selectedTeam = selectTeam();

        List<Player> teamPlayers = selectedTeam.getPlayersAsList();

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

        System.out.printf("%d Short Team Members (35-40)%n", shorter.size());
        for (String playerInfo : shorter){
            System.out.println(playerInfo);
        }
        System.out.printf("%d Average Team Members (41-46)%n", average.size());
        for (String playerInfo : average){
            System.out.println(playerInfo);
        }
        System.out.printf("%d Tall Team Members (47-50)%n", taller.size());
        for (String playerInfo : taller){
            System.out.println(playerInfo);
        }

        selectedTeam.setExpPerc();
        System.out.printf("This team consists of %d%% experience%n", selectedTeam.getExpPerc());

    }

    private static void removeFromTeam() {
        Scanner scan = new Scanner(System.in);

        Team selectedTeam = selectTeam();

        List<Player> teamPlayers = selectedTeam.getPlayersAsList();

        if(teamPlayers.size() <= 0) {
            System.out.println("No Players to remove");
        } else {
            Collections.sort(teamPlayers);
            menu.printPlayers(teamPlayers);
            System.out.println("Select a player (by number)");
            int player = scan.nextInt();

            playerList.add(teamPlayers.get(player - 1));
            selectedTeam.removePlayer(teamPlayers.get(player-1));
        }

    }

    private static void addToTeam(List<Player> players) {
        Scanner scan = new Scanner(System.in);

        Collections.sort(players);
        menu.printPlayers(players);
        System.out.println("Select a player (by number)");
        int player = scan.nextInt();

        Team selectedTeam = selectTeam();

        List<Player> teamPlayers = selectedTeam.getPlayersAsList();

        if (teamPlayers.size() >= 11) {
            System.out.println("This team is at capacity");
        } else {
            selectedTeam.addPlayer(players.get(player - 1));
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
  
  private static Team selectTeam() {
    Scanner scan = new Scanner(System.in);
    
    Collections.sort(teams);
    menu.printTeams(teams);
    System.out.println("Select a team (by number)");
    int team = scan.nextInt();
    
    return teams.get(team-1);
  }
}
