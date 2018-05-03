/**
 * Created by f1412 on 2018/5/2.
 */
import edu.princeton.cs.algs4.In;
import java.util.ArrayList;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FlowEdge;
import java.util.Iterator;

public class BaseballElimination {
    private int num_teams;
    private int[] w;//wins
    private int[] l;//loss
    private int[] r;//left
    private int[][] g;//game left
    private ArrayList<String> team_name;
    public BaseballElimination(String filename){
        // create a baseball division from given filename in format specified below
        if(filename == null) throw new IllegalArgumentException();
        In file = new In(filename);
        num_teams = file.readInt();
        team_name = new ArrayList<>();
        w = new int[num_teams];
        l = new int[num_teams];
        r = new int[num_teams];
        g = new int[num_teams][num_teams];
        for(int i = 0; i < num_teams; i++) {
            team_name.add(file.readString());
            w[i] = file.readInt();
            l[i] = file.readInt();
            r[i] = file.readInt();
            for (int j = 0; j < num_teams; j++) {
                g[i][j] = file.readInt();
            }
        }
        assert file.isEmpty();
    }

    public int numberOfTeams(){
        // number of teams
        return num_teams;
    }
    public Iterable<String> teams(){
        // all teams
        return team_name;
    }
    public int wins(String team){
        // number of wins for given team
        if(team == null) throw new IllegalArgumentException();
        if(!team_name.contains(team)) throw new IllegalArgumentException("not in team name");
        int ind = team_name.indexOf(team);
        return w[ind];
    }
    public int losses(String team){
        // number of losses for given team
        if(team == null) throw new IllegalArgumentException();
        if(!team_name.contains(team)) throw new IllegalArgumentException("not in team name");
        int ind = team_name.indexOf(team);
        return l[ind];
    }
    public int remaining(String team){
        // number of remaining games for given team
        if(team == null) throw new IllegalArgumentException();
        if(!team_name.contains(team)) throw new IllegalArgumentException("not in team name");
        int ind = team_name.indexOf(team);
        return r[ind];
    }
    public int against(String team1, String team2){
        // number of remaining games between team1 and team2
        if(team1 == null || team2 == null) throw new IllegalArgumentException();
        if(!team_name.contains(team1) || !team_name.contains(team2)) throw new IllegalArgumentException("not in team name");
        return g[team_name.indexOf(team1)][team_name.indexOf(team2)];
    }

    private FordFulkerson maxflow(String team){
        int ind = team_name.indexOf(team);
        FlowNetwork G = new FlowNetwork(num_teams + 1 + (num_teams-1)*(num_teams-2)/2);
        int count_game = 0;//跟踪下标
        for(int i = 0; i < num_teams; i++){
            if(i != ind) {
                for (int j = i + 1; j < num_teams; j++) {
                    if (j != ind) {
                        count_game += 1;
                        G.addEdge(new FlowEdge(0, count_game, g[i][j]));
                        if(i < ind) G.addEdge(new FlowEdge(count_game, (num_teams-1)*(num_teams-2)/2 + i + 1, Double.POSITIVE_INFINITY));
                        if(i > ind) G.addEdge(new FlowEdge(count_game, (num_teams-1)*(num_teams-2)/2 + i, Double.POSITIVE_INFINITY));
                        if(j < ind) G.addEdge(new FlowEdge(count_game, (num_teams-1)*(num_teams-2)/2 + j + 1, Double.POSITIVE_INFINITY));
                        if(j > ind) G.addEdge(new FlowEdge(count_game, (num_teams-1)*(num_teams-2)/2 + j, Double.POSITIVE_INFINITY));
                    }
                }
            }
        }
        for(int i = 0; i < num_teams; i++){
            if(i < ind) G.addEdge(new FlowEdge((num_teams-1)*(num_teams-2)/2 + i + 1, (num_teams-1)*(num_teams-2)/2 + num_teams , w[ind] + r[ind] - w[i]));
            if(i > ind) G.addEdge(new FlowEdge((num_teams-1)*(num_teams-2)/2 + i, (num_teams-1)*(num_teams-2)/2 + num_teams , w[ind] + r[ind] - w[i]));
        }
        return new FordFulkerson(G, 0, (num_teams-1)*(num_teams-2)/2 + num_teams);
    }

    public boolean isEliminated(String team){
        // is given team eliminated?
        //first,trivial elimination
        if(team == null) throw new IllegalArgumentException();
        if(!team_name.contains(team)) throw new IllegalArgumentException("not in team name");
        int ind = team_name.indexOf(team);
        int max_wins = w[ind] + r[ind];
        int max = -1;
        for(int i:w) {
            if (i > max) {
                max = i;
            }
        }
        if(max_wins < max){
            return true;
        }
        //second,Nontrivial elimination
        //s = 0 ,t = (n-1)(n-2)/2 + n
        FordFulkerson max_flow = maxflow(team);
        int track = 1;
        for(int i = 0; i < num_teams; i++){
            if(i != ind) {
                for (int j = i + 1; j < num_teams; j++) {
                    if (j != ind) {
                        if (max_flow.inCut(track)) {
                            return true;
                        }
                        track += 1;
                    }
                }
            }
        }
        return false;
    }
    public Iterable<String> certificateOfElimination(String team) {
        // subset R of teams that eliminates given team; null if not eliminated
        if(team == null) throw new IllegalArgumentException();
        if(!team_name.contains(team)) throw new IllegalArgumentException("not in team name");
        if(!isEliminated(team)) return null;
        ArrayList<String> beat_team = new ArrayList<>();
        int ind = team_name.indexOf(team);
        int max_wins = w[ind] + r[ind];
        int max = -1;
        int max_ind = -1;
        for(int i = 0;i < w.length;i++) {
            if (w[i] > max) {
                max = w[i];
                max_ind = i;
            }
        }
        if(max > max_wins){
            beat_team.add(team_name.get(max_ind));
            return beat_team;
        }
        FordFulkerson max_flow = maxflow(team);
        int track = 1;
        for(int i = 0; i < num_teams; i++) {
            if (i != ind) {
                for (int j = i + 1; j < num_teams; j++) {
                    if (j != ind) {
                        if (max_flow.inCut(track)) {
                            beat_team.add(team_name.get(i));
                            beat_team.add(team_name.get(j));
                        }
                        track += 1;
                    }
                }
            }
        }
        return beat_team;

    }

    }

